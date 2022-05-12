package com.test.whatsappstatusdowloader.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.adapter.SavedStatusAdapter
import com.test.whatsappstatusdowloader.adapter.WhatsAppStatusAdapter
import com.test.whatsappstatusdowloader.databinding.FragmentWhatsappStatusBinding
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.UtilsMethod
import java.io.File


class StatusFragment() : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    private var statusFileList: ArrayList<File>
    private lateinit var directoryAddress: String

    init {


//        if(arguments?.getString(Constants.DIRECTORY_KEY) !=null)
//            this.directoryAddress= arguments!!.getString(Constants.DIRECTORY_KEY).toString()


//        if (directoryAddress.isEmpty())
//            this.directoryAddress = directoryAddress



        statusFileList = ArrayList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        this.directoryAddress= arguments?.getString(Constants.DIRECTORY_KEY).toString()

        binding = FragmentWhatsappStatusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        setUpList()
    }

    private fun setUpList() {


        statusFileList.clear()
        val statusDirectory = File(directoryAddress)

        if (statusDirectory.exists())
            prepareStatusList(statusDirectory)
        else
            showDirectoryNotExist()
    }

    private fun prepareStatusList(statusDirectory: File) {

        val fileList: Array<out File>? = statusDirectory.listFiles()
        if (!fileList.isNullOrEmpty()) {

            binding.apply {
                rvStatus.visibility = View.VISIBLE
                tvWarning.visibility = View.GONE
            }

            for (file in fileList) {

                if (UtilsMethod.isImageFile(file.path) || UtilsMethod.isVideoFile(file.path)) {
                    statusFileList.add(file)
                }
            }


            setUpRecyclerView()

        } else {
            showNoStatusWasObserved()
        }
    }

    private fun showNoStatusWasObserved() {

        binding.apply {
            rvStatus.visibility = View.GONE
            if (directoryAddress == Constants.SAVED_DIRECTORY)
                tvWarning.text = getString(R.string.no_status_saved)
            else
                tvWarning.text = getString(R.string.no_status_was_observed)

            tvWarning.visibility = View.VISIBLE
            ivWarning.visibility = View.VISIBLE


        }

    }

    private fun showDirectoryNotExist() {

        binding.apply {
            rvStatus.visibility = View.GONE

            if (directoryAddress == Constants.SAVED_DIRECTORY)
                tvWarning.text = getString(R.string.no_status_saved)
            else
                tvWarning.text = getString(R.string.directory_not_exist)

            tvWarning.visibility = View.VISIBLE
            ivWarning.visibility = View.VISIBLE
        }
    }

    private fun setUpRecyclerView() {

            if (directoryAddress == Constants.SAVED_DIRECTORY){
                val statusAdapter =  SavedStatusAdapter( requireActivity())
                statusAdapter.differ.submitList(statusFileList)

                binding.rvStatus.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = statusAdapter
                }
            }
            else{
                val statusAdapter =WhatsAppStatusAdapter(requireActivity())
                statusAdapter.differ.submitList(statusFileList)
                binding.rvStatus.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = statusAdapter

                }
            }





    }


}
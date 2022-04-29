package com.test.whatsappstatusdowloader.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.adapter.WhatsAppStatusAdapter
import com.test.whatsappstatusdowloader.databinding.FragmentWhatsappStatusBinding
import com.test.whatsappstatusdowloader.utility.Constants
import com.test.whatsappstatusdowloader.utility.Utility
import java.io.File
import java.net.URLConnection


class StatusFragment(directoryAddress: String) : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    private var statusFileList: ArrayList<File>
    private var directoryAddress: String

    init {
        this.directoryAddress = directoryAddress
        statusFileList = ArrayList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
        Log.i("123321", statusDirectory.path.toString())

        if (statusDirectory.exists())
            prepareStatusList(statusDirectory)
        else
            showDirectoryNotExist()

    }

    private fun prepareStatusList(statusDirectory: File) {

        val statusListFile: Array<out File>? = statusDirectory.listFiles()
        if (!statusListFile.isNullOrEmpty()) {

            binding.apply {
                rvWhatsappStatus.visibility = View.VISIBLE
                tvWarning.visibility = View.GONE
            }

            for (statusFile in statusListFile) {

         //       Log.i("123321",statusFile.path.toString())
                if (Utility.isImageFile(statusFile.path) || Utility.isVideoFile(statusFile.path)) {
                    statusFileList.add(statusFile)

                }
            }


            setUpRecyclerView()

        } else {
            showNoStatusWasObserved()
        }
    }

    private fun showNoStatusWasObserved() {

        binding.apply {
            rvWhatsappStatus.visibility = View.GONE
            if (directoryAddress == Constants.SAVED_DIRECTORY)
                tvWarning.text = getString(R.string.no_status_saved)
            else
                tvWarning.text = getString(R.string.no_status_was_observed)
            tvWarning.visibility = View.VISIBLE

        }

    }

    private fun showDirectoryNotExist() {

        binding.apply {
            rvWhatsappStatus.visibility = View.GONE

            if (directoryAddress == Constants.SAVED_DIRECTORY)
                tvWarning.text = getString(R.string.no_status_saved)
            else
                tvWarning.text = getString(R.string.directory_not_exist)

            tvWarning.visibility = View.VISIBLE
        }
    }



    private fun setUpRecyclerView() {

        binding.rvWhatsappStatus.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = WhatsAppStatusAdapter(requireActivity(), statusFileList, directoryAddress)
        }
    }

}
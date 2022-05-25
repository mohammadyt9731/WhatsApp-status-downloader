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
import com.test.whatsappstatusdowloader.utils.MyIntent
import com.test.whatsappstatusdowloader.utils.UtilsMethod
import java.io.File
import kotlin.system.measureTimeMillis


class StatusFragment() : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    private var statusFileList: ArrayList<File> = ArrayList()
    private lateinit var directoryAddress: String


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

        setOnClick()

    }

    private fun setOnClick (){

        binding.btnOpenWhatsapp.setOnClickListener(){
            if(directoryAddress==Constants.WHATSAPP_BUSINESS_DIRECTORY
                ||directoryAddress==Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY){
                MyIntent.openWhatsAppBusiness(requireContext())

            }else {
                MyIntent.openWhatsApp(requireContext())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val time = measureTimeMillis {
                setUpList()
        }
        Log.i("taggg",time.toString())


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

        for (file in fileList!!){
            if(UtilsMethod.isImageFile(file.name) || UtilsMethod.isVideoFile(file.name))
                statusFileList.add(file)
        }



        if (!statusFileList.isNullOrEmpty()) {

            binding.apply {
                rvStatus.visibility = View.VISIBLE
                tvWarning.visibility = View.GONE
                btnOpenWhatsapp.visibility = View.GONE
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
            if (directoryAddress != Constants.SAVED_DIRECTORY)
                btnOpenWhatsapp.visibility=View.VISIBLE


        }

    }

    private fun showDirectoryNotExist() {

        binding.apply {
            rvStatus.visibility = View.GONE


            tvWarning.text = when(directoryAddress){

                Constants.WHATSAPP_DIRECTORY->getString(R.string.whatsapp_is_not_installed)
                Constants.NEW_WHATSAPP_DIRECTORY->getString(R.string.whatsapp_is_not_installed)
                Constants.WHATSAPP_BUSINESS_DIRECTORY->getString(R.string.whatsapp_business_is_not_installed)
                Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY->getString(R.string.whatsapp_business_is_not_installed)
                Constants.SAVED_DIRECTORY->getString(R.string.no_status_saved)
                else -> getString(R.string.directory_not_exist)
            }



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
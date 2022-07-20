package com.ddt.whatsappStatusDownloader.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.adapter.SavedStatusAdapter
import com.ddt.whatsappStatusDownloader.adapter.WhatsAppStatusAdapter
import com.ddt.whatsappStatusDownloader.databinding.FragmentWhatsappStatusBinding
import com.ddt.whatsappStatusDownloader.utils.*
import java.io.File
import java.text.FieldPosition


class StatusFragment : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    private var statusFileList: ArrayList<File> = ArrayList()
    private lateinit var directoryAddress: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init()

        binding = FragmentWhatsappStatusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun init() {

        if (arguments != null) {
            this.directoryAddress = arguments?.getString(Constants.DIRECTORY_KEY).toString()
        } else {

            directoryAddress = if (Build.VERSION.SDK_INT >= 30)
                Constants.NEW_WHATSAPP_DIRECTORY
            else
                Constants.WHATSAPP_DIRECTORY
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
        setUpList()
    }

    private fun setOnClick() {

        binding.btnOpenWhatsapp.setOnClickListener() {
            if (directoryAddress == Constants.WHATSAPP_BUSINESS_DIRECTORY
                || directoryAddress == Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY
            ) {
                MyIntent.openWhatsAppBusiness(requireContext())

            } else {
                MyIntent.openWhatsApp(requireContext())
            }
        }

    }

    private fun setUpList() {

       statusFileList.clear()
        val statusDirectory = File(directoryAddress)

        if (statusDirectory.exists())
            prepareStatusList(statusDirectory)
        else
            showDirectoryNotExist()

    }

    private fun showList() {
        binding.apply {
            rvStatus.visible()
            tvWarning.gone()
            ivWarning.gone()
            btnOpenWhatsapp.gone()

        }

    }

    private fun hiddenList() {
        binding.apply {
            rvStatus.gone()
            tvWarning.visible()
            ivWarning.visible()


        }
    }

    private fun prepareStatusList(statusDirectory: File) {


        val fileList: Array<out File>? = statusDirectory.listFiles()

        for (file in fileList!!) {
            if (UtilsMethod.isImageFile(file.name) || UtilsMethod.isVideoFile(file.name))
                statusFileList.add(file)
        }

        if (!statusFileList.isNullOrEmpty()) {
            showList()
            setUpRecyclerView()

        } else {
            hiddenList()
            showNoStatusWasObserved()
        }
    }

    private fun showNoStatusWasObserved() {

        binding.apply {
            if (directoryAddress == Constants.SAVED_DIRECTORY)
                tvWarning.text = getString(R.string.no_status_saved)
            else
                tvWarning.text = getString(R.string.no_status_was_observed)


            if (directoryAddress != Constants.SAVED_DIRECTORY)
                btnOpenWhatsapp.visible()


        }

    }

    private fun showDirectoryNotExist() {

        binding.apply {

            tvWarning.text = when (directoryAddress) {

                Constants.WHATSAPP_DIRECTORY, Constants.NEW_WHATSAPP_DIRECTORY -> getString(R.string.whatsapp_is_not_installed)

                Constants.WHATSAPP_BUSINESS_DIRECTORY, Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY -> getString(
                    R.string.whatsapp_business_is_not_installed
                )

                Constants.SAVED_DIRECTORY -> getString(R.string.no_status_saved)

                else -> getString(R.string.directory_not_exist)
            }

            hiddenList()
        }
    }

    private fun setUpRecyclerView() {

        if (directoryAddress == Constants.SAVED_DIRECTORY) {
            val statusAdapter:SavedStatusAdapter
            statusAdapter = SavedStatusAdapter(requireActivity(), statusFileList)

            binding.rvStatus.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = statusAdapter
            }
        } else {
            val statusAdapter = WhatsAppStatusAdapter(requireActivity())
            statusAdapter.differ.submitList(statusFileList)
            binding.rvStatus.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = statusAdapter

            }
        }


    }

}
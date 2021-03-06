package com.ddt.whatsappStatusDownloader.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.adapter.SavedStatusAdapter
import com.ddt.whatsappStatusDownloader.adapter.WhatsAppStatusAdapter
import com.ddt.whatsappStatusDownloader.databinding.FragmentWhatsappStatusBinding
import com.ddt.whatsappStatusDownloader.utils.*
import java.io.File


class StatusFragment : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    private var statusFileList: ArrayList<File> = ArrayList()
    private lateinit var directoryAddress: String
    private var recyclerViewState: Parcelable? = null


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
            directoryAddress = if (UtilsMethod.isAndroid11orHigher())
                Constants.NEW_WHATSAPP_DIRECTORY
            else
                Constants.WHATSAPP_DIRECTORY
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }


    override fun onPause() {
        super.onPause()

        //save position of recyclerView
        recyclerViewState = binding.rvStatus.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()


        setUpList()

        //load position of recyclerView
        if (recyclerViewState != null)
            binding.rvStatus.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }


    private fun setOnClick() {

        binding.btnOpenWhatsapp.setOnClickListener() {
            if (directoryAddress == Constants.WHATSAPP_BUSINESS_DIRECTORY
                || directoryAddress == Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY
            ) {
                //open business whatsapp
                MyIntent.openWhatsAppBusiness(requireContext())

            } else {
                //open whatsapp
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

            when(directoryAddress){
                Constants.WHATSAPP_DIRECTORY,Constants.NEW_WHATSAPP_DIRECTORY ->{
                    tvWarning.text = getString(R.string.no_w_status_was_observed)
                    btnOpenWhatsapp.text=getString(R.string.open_whatsapp)
                    btnOpenWhatsapp.visible()
                }

                Constants.WHATSAPP_BUSINESS_DIRECTORY,Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY->{
                    tvWarning.text = getString(R.string.no_wb_status_was_observed)
                    btnOpenWhatsapp.text=getString(R.string.open_business_whatsapp)
                    btnOpenWhatsapp.gone()
                }

                Constants.SAVED_DIRECTORY,Constants.SAVED_DIRECTORY->{
                    tvWarning.text = getString(R.string.no_status_saved)
                    btnOpenWhatsapp.gone()
                }
            }
        }

    }

    private fun showDirectoryNotExist() {

        binding.apply {

            when (directoryAddress) {

                Constants.WHATSAPP_DIRECTORY, Constants.NEW_WHATSAPP_DIRECTORY -> {
                    tvWarning.text = getString(R.string.whatsapp_is_not_installed)
                }

                Constants.WHATSAPP_BUSINESS_DIRECTORY, Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY -> {
                    tvWarning.text = getString( R.string.whatsapp_business_is_not_installed)
                }

                Constants.SAVED_DIRECTORY ->tvWarning.text = getString(R.string.no_status_saved)

                else -> tvWarning.text = getString(R.string.directory_not_exist)
            }
            btnOpenWhatsapp.gone()
            hiddenList()
        }
    }

    private fun setUpRecyclerView() {

        if (directoryAddress == Constants.SAVED_DIRECTORY) {

            val statusAdapter = SavedStatusAdapter(requireActivity(), statusFileList)
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
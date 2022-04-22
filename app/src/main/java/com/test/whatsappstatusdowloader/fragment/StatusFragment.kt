package com.test.whatsappstatusdowloader.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.test.whatsappstatusdowloader.adapter.WhatsAppStatusAdapter
import com.test.whatsappstatusdowloader.databinding.FragmentWhatsappStatusBinding
import java.io.File


class StatusFragment(directoryAddress:String) : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    private var statusFileList: ArrayList<File> = ArrayList()
    private var directoryAddress:String

    init {
        this.directoryAddress=directoryAddress
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


        // /Download/Wahtsapp
        val file = File(directoryAddress)

        Log.i("123321",file.path.toString())
        if (file.exists()) {

            val statusListFile = file.listFiles()
            if (statusListFile?.isNotEmpty() == true)
                for (statusFile in statusListFile){
                    if(statusFile.toString().endsWith("jpg")){
                        statusFileList.add(statusFile)
                      //  Log.i("123321",statusFile.path.toString())
                    }

                }

            setUpRecyclerView()
        }

    }

    private fun setUpRecyclerView() {

        binding.rvWhatsappStatus.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = WhatsAppStatusAdapter(requireActivity(), statusFileList,directoryAddress)
        }




    }
}
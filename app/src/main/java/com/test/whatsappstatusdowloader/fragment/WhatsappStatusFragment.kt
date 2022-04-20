package com.test.whatsappstatusdowloader.fragment


import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.test.whatsappstatusdowloader.adapter.WhatsAppStatusAdapter
import com.test.whatsappstatusdowloader.databinding.FragmentWhatsappStatusBinding
import java.io.File


class WhatsappStatusFragment : Fragment() {

    private lateinit var binding: FragmentWhatsappStatusBinding
    var statusFileList: ArrayList<File> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWhatsappStatusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // /Download/Wahtsapp
        val file =
            File(Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/.Statuses")
        Log.i("123321",file.toString())
        if (file.exists()) {

            val statusListFile = file.listFiles()

            if (statusListFile?.isNotEmpty() == true)
                for (f in statusListFile){
                    if(f.toString().endsWith("jpg")){

                        statusFileList.add(f)
                     //   Log.i("123321",f.path.toString())
                    }

                }

            setUpRecyclerView(view)
        }



    }

    private fun setUpRecyclerView(view: View) {

        binding.rvWhatsappStatus.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = WhatsAppStatusAdapter(requireActivity(), statusFileList)
        }




    }
}
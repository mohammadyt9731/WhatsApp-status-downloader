package com.test.whatsappstatusdowloader.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.adapter.WhatsAppStatusAdapter
import com.test.whatsappstatusdowloader.databinding.FragmentWhatsappStatusBinding
import com.test.whatsappstatusdowloader.utility.Utility


class WhatsappStatusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_whatsapp_status, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpRecyclerView(view)


    }

    private fun setUpRecyclerView(view: View){


        val rvWhatsappStatus = view.findViewById<RecyclerView>(R.id.rv_whatsapp_status)
        rvWhatsappStatus.layoutManager = GridLayoutManager(context,2)
        val adapter = WhatsAppStatusAdapter(requireActivity())
        rvWhatsappStatus.adapter = adapter
    }
}
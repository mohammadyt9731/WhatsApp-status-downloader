package com.ddt.whatsappStatusDownloader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ddt.whatsappStatusDownloader.databinding.FragmentGuideBinding
import com.ddt.whatsappStatusDownloader.utils.Constants

class GuideFragment : Fragment() {

    private lateinit var binding: FragmentGuideBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentGuideBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setUp lottie anim
        binding.lottieDownload2.frame=Constants.LOTTIE_END_FRAME

    }
}
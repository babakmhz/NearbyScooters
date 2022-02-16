package com.babakmhz.nearbyscooters.view.details

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.navArgs
import com.babakmhz.nearbyscooters.R
import com.babakmhz.nearbyscooters.databinding.FragmentDetailsBinding
import com.babakmhz.nearbyscooters.view.base.BaseBottomSheetFragment

class DetailsFragment : BaseBottomSheetFragment() {


    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding


    override fun getTheme(): Int {
        return R.style.BaseBottomSheetDialog
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.scooter = args.scooter
        binding.nearest = args.nearest
        binding.executePendingBindings()
        return binding.root
    }

    override fun getProgressBar(): ProgressBar? {
        return null
    }
}
package com.arifcit.tourmate.ui.tour

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arifcit.tourmate.R
import com.arifcit.tourmate.adapters.TourAdapter
import com.arifcit.tourmate.databinding.FragmentTourBinding
import com.arifcit.tourmate.repository.TourRepository.Companion.TAG
import com.arifcit.tourmate.ui.addTour.AddTourViewModel
import com.arifcit.tourmate.ui.login.LoginViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class TourFragment : Fragment() {


    private lateinit var binding : FragmentTourBinding
    private val addTourViewModel : AddTourViewModel by viewModels()
    private val loginViewModel : LoginViewModel by viewModels()


   // private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentTourBinding.inflate(inflater)

        val adapter = TourAdapter{tourId ->
              findNavController().navigate(R.id.action_nav_tours_to_tourDetailsFragment,
              Bundle().apply {
                  putString("id",tourId)
              }
                  )
        }
        binding.tourRecyclerView.adapter = adapter
        addTourViewModel.getTourFromDb(loginViewModel.user!!.uid).observe(viewLifecycleOwner) {
          //  Toast.makeText(requireActivity(),"${it.size}",Toast.LENGTH_SHORT).show()
            adapter.submitList(it)
        }

        binding.addTour.setOnClickListener {

            findNavController().navigate(R.id.action_nav_tours_to_addTourFragment)
        }
        binding.tourRecyclerView.setOnClickListener {

        }

      //  MobileAds.initialize(activity) {}

        /*var mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        InterstitialAd.load(activity,"ca-app-pub-5455800500942442/5041782965", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })*/


        return binding.root
    }


}
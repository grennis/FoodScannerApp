package com.innodroid.foodscannerapp.ui.home

import alert
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.innodroid.foodscannerapp.databinding.FragmentHomeBinding
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.model.Scan
import com.innodroid.foodscannerapp.services.Status
import getNavigationResult
import org.koin.android.ext.android.inject


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel by inject<HomeViewModel>()
    private val handler = Handler(Looper.getMainLooper())

    private val scansAdapter: ScansAdapter by lazy {
        ScansAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.scans.observe(this, {
            when (it.status) {
                Status.NOT_AUTHENTICATED -> showNotAuthenticated()
                Status.LOADING -> showLoading()
                Status.ERROR -> showError()
                Status.SUCCESS -> showData(it.data)
            }
        })
    }

    private fun showLoading() {
        binding?.help?.isGone = false
        binding?.help?.setText(R.string.loading)
    }

    private fun showError() {
        binding?.help?.isGone = false
        binding?.help?.setText(R.string.error_listing)
    }

    private fun showNotAuthenticated() {
        binding?.help?.isGone = false
        binding?.help?.setText(R.string.not_authenticated)
    }

    private fun showData(scans: List<Scan>?) {
        binding?.help?.isGone = !scans.isNullOrEmpty()
        binding?.help?.setText(R.string.no_scans)

        scansAdapter.load(scans)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            scan.setOnClickListener {
                scan()
            }

            scanImage.setOnClickListener {
                scan()
            }

            scans.adapter = scansAdapter
        }

        scansAdapter.itemClicked = {
            searchUpc(it.upc, false)
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (getNavigationResult("scanAgain")?.value == "true") {
            scan()
        }
    }

    private fun scan() {
        if (viewModel.scans.value?.status == Status.NOT_AUTHENTICATED) {
            alert(R.string.not_authenticated) {
                findNavController().navigate(R.id.navigation_account)
            }

            return
        }

        val intent = IntentIntegrator.forSupportFragment(this);
        intent.setOrientationLocked(true)
        intent.setBeepEnabled(false)
        intent.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                beep()
                searchUpc(result.contents, true)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun beep() {
        val tone = ToneGenerator(AudioManager.STREAM_ALARM, 100)

        tone.startTone(ToneGenerator.TONE_CDMA_PIP, 150)

        handler.postDelayed({
            tone.release()
        }, 200)
    }

    private fun searchUpc(upc: String, store: Boolean) {
        val args = Bundle().apply {
            putString("upc", upc)
            putBoolean("store", store)
        }

        findNavController().navigate(R.id.action_navigation_home_to_detailFragment, args)
    }
}

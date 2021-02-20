package com.innodroid.foodscannerapp.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.databinding.FragmentDetailBinding
import com.innodroid.foodscannerapp.model.Food
import com.innodroid.foodscannerapp.services.Status
import org.koin.android.ext.android.inject
import setNavigationResult

class DetailFragment : Fragment() {
    private var binding: FragmentDetailBinding? = null
    private val viewModel by inject<DetailViewModel>()

    private val ingredientsAdapter: IngredientsAdapter by lazy {
        IngredientsAdapter()
    }

    private val upc: String
        get() = requireArguments().getString("upc", "")

    private val store: Boolean
        get() = requireArguments().getBoolean("store", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getIngredients(upc, store).observe(this, {
            when (it.status) {
                Status.LOADING -> binding?.summary?.showLoading()
                Status.ERROR -> binding?.summary?.showError()
                Status.SUCCESS -> handleResult(it?.data)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            ingredients.adapter = ingredientsAdapter
        }

        setNavigationResult("false", "scanAgain")

        setHasOptionsMenu(true)

        return binding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.rescan) {
            setNavigationResult("true", "scanAgain")

            findNavController().popBackStack()
        } else if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleResult(product: Food?) {
        if (product == null) {
            binding?.summary?.showError()
            return
        }

        updateUI(product)
    }

    private fun updateUI(product: Food) {
        binding?.title?.text = product.brand
        binding?.subtitle?.text = product.label

        binding?.summary?.load(product.ingredients ?: listOf())

        ingredientsAdapter.load(product.ingredients)

        product.image?.let {
            binding?.image?.load(it) {
                placeholder(R.drawable.ic_baseline_image_24)
            }
        }
    }
}

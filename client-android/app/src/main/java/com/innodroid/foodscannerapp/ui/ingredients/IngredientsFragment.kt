package com.innodroid.foodscannerapp.ui.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.innodroid.foodscannerapp.databinding.FragmentIngredientsBinding
import com.innodroid.foodscannerapp.model.Ingredient
import org.koin.android.ext.android.inject

class IngredientsFragment : Fragment() {
    private var binding: FragmentIngredientsBinding? = null
    private val viewModel by inject<IngredientsViewModel>()

    private val ingredientsAdapter: IngredientsAdapter by lazy {
        IngredientsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false).apply {
            ingredients.adapter = ingredientsAdapter
        }

        ingredientsAdapter.load(Ingredient.all)

        return binding!!.root
    }
}

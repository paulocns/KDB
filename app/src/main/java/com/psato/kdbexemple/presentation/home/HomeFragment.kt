package com.psato.kdbexemple.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.psato.extensions.onClick
import com.psato.kdbexemple.R
import com.psato.kdbexemple.infrastructure.bindView
import com.psato.kdbexemple.presentation.base.BaseFragment
import com.psato.kdbexemple.presentation.search.QueryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    val homeFragmentViewModel: HomeFragmentViewModel by viewModel()
    val mvvmButton: Button by bindView(R.id.mvvm_button)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvvmButton.onClick(homeFragmentViewModel::onMVVMClicked)
        homeFragmentViewModel.startSearch.observe(this, Observer {
            Log.d("SATO", "metric start open search")
            startActivity(Intent(activity, QueryActivity::class.java))
        })
    }
}

package dev.kdblib.kdbexemple.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import dev.kdblib.kdbexemple.R
import dev.kdblib.kdbexemple.infrastructure.bindView
import dev.kdblib.kdbexemple.infrastructure.onClick
import dev.kdblib.kdbexemple.presentation.base.BaseFragment
import dev.kdblib.kdbexemple.presentation.search.QueryActivity
import dev.kdblib.onewaybind.Bindable
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), Bindable {

    private val homeFragmentViewModel: HomeFragmentViewModel by viewModel()
    private val mvvmButton: Button by bindView(R.id.mvvm_button)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvvmButton.onClick(homeFragmentViewModel::onMVVMClicked)
        homeFragmentViewModel.startSearch.bindNullable {
            startActivity(Intent(activity, QueryActivity::class.java))
        }
    }
}

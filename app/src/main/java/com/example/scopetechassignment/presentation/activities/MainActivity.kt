package com.example.scopetechassignment.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.scopetechassignment.R
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.presentation.viewmodels.GetUserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: GetUserListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        collectLatestLifecycleFlow(viewModel.userDetailState) {
            when (it.status) {
                Status.LOADING -> {
                    //Do nothing
                }
                Status.ERROR -> {
                    Toast.makeText(this@MainActivity, it.errorMessage, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        Toast.makeText(
                            this@MainActivity,
                            "size : ${data.userData.size}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}

fun <T> AppCompatActivity.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}
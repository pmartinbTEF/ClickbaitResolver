package com.pmartinb.clickbaitresolver.prompt.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pmartinb.clickbaitresolver.databinding.ActivityPromptBinding
import com.pmartinb.clickbaitresolver.web.ui.NewsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PromptActivity : AppCompatActivity() {

    private val promptViewModel: PromptViewModel by viewModels()

    private lateinit var binding: ActivityPromptBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromptBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initializeView()
    }


    private fun initializeView() {
        binding.checkUrl.setOnClickListener {
            promptViewModel.checkUrlClicked()
        }
        CoroutineScope(Dispatchers.Main).launch {
            promptViewModel.currentResponse.collect {
                binding.textViewHello.text = it
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            promptViewModel.readNewsButton.collect { _clicked ->
                if (_clicked) {
                    navigateToNewsActivity()
                }
            }
        }
    }

    fun navigateToNewsActivity() {
        val newsIntent = Intent(this, NewsActivity::class.java)
        startActivity(newsIntent)
    }
}

package com.capstone.getretore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.getretore.adapter.ListAdapter
import com.capstone.getretore.databinding.ActivityMainBinding
import com.capstone.getretore.ui.LoginActivity
import com.capstone.getretore.ui.RecomenActivity
import com.capstone.getretore.viewModel.MainViewModel
import com.capstone.getretore.viewModel.ViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelFactory
    private var addClicked = false
    private lateinit var listAdapter: ListAdapter
    private var token = ""
    private val mainViewModel: MainViewModel by viewModels { viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBudget.setOnClickListener {
            addClicked = true
            startActivity(Intent(this, RecomenActivity::class.java))
        }

        listAdapter = ListAdapter()
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_logout -> {
                startActivity(Intent(this, LoginActivity::class.java))
                Firebase.auth.signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData() {
        mainViewModel.getSession().observe(this@MainActivity) {
            token = it.token
            if (!it.isLogin) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            } else {
                mainViewModel.getListStories.observe(this@MainActivity) {pagingData ->
                    listAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (addClicked) {
            loadData()
            addClicked = false
            binding.rvList.scrollToPosition(0)
        }
    }
}
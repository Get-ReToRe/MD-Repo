package com.capstone.getretore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.capstone.getretore.adapter.PlaceAdapter
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.databinding.ActivityMainBinding
import com.capstone.getretore.ui.LoginActivity
import com.capstone.getretore.ui.RecomenActivity
import com.capstone.getretore.user.PlaceData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PlaceAdapter
    private var addClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PlaceAdapter(this@MainActivity, arrayListOf())

        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)

        remoteGetPlace()

        binding.btnBudget.setOnClickListener {
            addClicked = true
            startActivity(Intent(this, RecomenActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun remoteGetPlace(){
        ApiConfig.getApiService().getPlaces().enqueue(object : Callback<ArrayList<PlaceData>> {
            override fun onResponse(
                call: Call<ArrayList<PlaceData>>,
                response: Response<ArrayList<PlaceData>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    setDataToAdapter(data!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<PlaceData>>, t: Throwable) {
                Log.d("Error", ""+ t.stackTraceToString())
            }
        })
    }

    fun setDataToAdapter(data: ArrayList<PlaceData>){
        adapter.setData(data)
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
}
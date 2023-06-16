package com.capstone.getretore.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.capstone.getretore.R
import com.capstone.getretore.adapter.PlaceAdapter
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.databinding.FragmentHomeBinding
import com.capstone.getretore.ui.DetailActivity
import com.capstone.getretore.ui.LoginActivity
import com.capstone.getretore.user.PlaceData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var firebase: Firebase
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: PlaceAdapter

    private lateinit var rvMain: RecyclerView
    private val list = ArrayList<PlaceData>()

    private var addClicked = false

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = PlaceAdapter(requireContext(), arrayListOf())

        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)

        var auth = Firebase.auth
        binding.tvSelamatDatang.setText("Selamat Datang, " + auth.currentUser!!.displayName.toString() )

        remoteGetPlace()

        showPlaceList()

        return root
    }

    fun remoteGetPlace(){
        val placeAdapter = PlaceAdapter(requireContext(), placeList = ArrayList())
        ApiConfig.getApiService().getPlaces().enqueue(object : Callback<ArrayList<PlaceData>> {
            override fun onResponse(
                call: Call<ArrayList<PlaceData>>,
                response: Response<ArrayList<PlaceData>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    setDataToAdapter(data!!)

                    placeAdapter.setOnItemClickCallback(object : PlaceAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: PlaceData) {
                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra("NAME", data.Place_Name)
                            intent.putExtra("PRICE", data.Price)
                            intent.putExtra("DESCRIPTION", data.Description)
                            intent.putExtra("IMAGE", data.Image)
                            Log.d("DetailNAME", data.Place_Name.toString())
                            Log.d("DetailImage", data.Image.toString())
                            startActivity(intent)
                        }
                    })
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

    private fun showPlaceList() {
        val placeAdapter = PlaceAdapter(requireContext(), placeList = ArrayList())

        placeAdapter.setOnItemClickCallback(object : PlaceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlaceData) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("NAME", data.Place_Name)
                intent.putExtra("PRICE", data.Price)
                intent.putExtra("DESCRIPTION", data.Description)
                intent.putExtra("IMAGE", data.Image)
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
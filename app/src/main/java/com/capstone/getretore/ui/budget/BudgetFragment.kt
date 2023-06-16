package com.capstone.getretore.ui.budget

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.capstone.getretore.R
import com.capstone.getretore.adapter.BudgetPredictAdapter
import com.capstone.getretore.adapter.PlaceAdapter
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.databinding.FragmentBudgetBinding
import com.capstone.getretore.databinding.FragmentHomeBinding
import com.capstone.getretore.user.BudgetPredictData
import com.capstone.getretore.user.PlaceData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BudgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentBudgetBinding? = null
    private lateinit var adapter: BudgetPredictAdapter
    private var isLoading = false
    private var budget: Int = 0
    private var category: String = ""
    private var lat: Double = -7.7829
    private var long: Double = 110.3671
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_wisata,
            android.R.layout.simple_spinner_item
        ).also { adapters ->
            adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapters
        }

        adapter = BudgetPredictAdapter(requireContext(), arrayListOf())
        binding.rvPredict.adapter = adapter


        binding.btnBudgetRecommendation.setOnClickListener { getRecommendation() }
        val root: View = binding.root
        return root
    }

    fun setDataToAdapter(data: ArrayList<BudgetPredictData>){
        adapter.setData(data)
    }

    private fun getRecommendation(){
        isLoading = true
        loadingHandler(true)
        var inputBudget = binding.etBudget.text.toString();
        var inputCategory = binding.spinnerCategory.selectedItem.toString();
        if(inputBudget == ""){
            Toast.makeText(context, "Tambahkan Budget Dulu !", Toast.LENGTH_SHORT).show()
        }else if (inputCategory == "Category"){
            Toast.makeText(context, "Pilih Category Dulu !", Toast.LENGTH_SHORT).show()
        }else{
            budget = inputBudget.toInt()
            category = inputCategory;
            Log.d("BUDGET", "budget : $budget ,category : $category")
            val jsonObject = JSONObject()
            jsonObject.put("budget", budget);
            jsonObject.put("category", category);
            jsonObject.put("lat",lat);
            jsonObject.put("lon", long);
            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            ApiConfig.getApiService().getRecommendation(requestBody).enqueue(object :
                Callback<ArrayList<BudgetPredictData>>{
                override fun onResponse(
                    call: Call<ArrayList<BudgetPredictData>>,
                    response: Response<ArrayList<BudgetPredictData>>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        Log.d("response", "result ${data}" )
                        setDataToAdapter(data!!)
                    }
                    binding.loadingLayout.root.visibility = View.GONE
                }

                override fun onFailure(call: Call<ArrayList<BudgetPredictData>>, t: Throwable) {
                    Log.d("Error", ""+ t.stackTraceToString())
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }

            })
        }

    }

    private fun loadingHandler(isLoading: Boolean) {
        if (isLoading && this.isLoading) {
            binding.loadingLayout.root.visibility = View.VISIBLE
        } else {
            binding.loadingLayout.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingHandler(false)
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BudgetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BudgetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

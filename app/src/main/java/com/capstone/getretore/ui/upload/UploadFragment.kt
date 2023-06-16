package com.capstone.getretore.ui.upload

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.getretore.R
import com.capstone.getretore.adapter.PlaceAdapter
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.databinding.FragmentHomeBinding
import com.capstone.getretore.databinding.FragmentUploadBinding
import com.capstone.getretore.reduceFileImage
import com.capstone.getretore.ui.DetailActivity
import com.capstone.getretore.uriToFile
import com.capstone.getretore.user.PlaceData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class UploadFragment : Fragment() {

    private var getFile: File? = null
    private var _binding: FragmentUploadBinding? = null
    private lateinit var adapter: PlaceAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = PlaceAdapter(requireContext(), arrayListOf())
        binding.rvPredict.adapter = adapter

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnPredict.setOnClickListener { uploadImage() }



        return root
    }


    fun setDataToAdapter(data: ArrayList<PlaceData>){
        adapter.setData(data)
    }

    private fun uploadImage(){
        if(getFile!=null){
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )
            ApiConfig.getApiService().postImage(imageMultipart).enqueue(object :
                Callback<ArrayList<PlaceData>> {
                override fun onResponse(
                    call: Call<ArrayList<PlaceData>>,
                    response: Response<ArrayList<PlaceData>>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        Log.d("response", "response ${data}")
                        setDataToAdapter(data!!)
                    }
                }

                override fun onFailure(call: Call<ArrayList<PlaceData>>, t: Throwable) {
                    Log.d("Error", ""+ t.stackTraceToString())
                }

            })
        }else{
            Toast.makeText(context, "Tambahkan Gambar Dulu !", Toast.LENGTH_SHORT).show()
            Log.d("predict", "PPPPPPPPPPP")
        }
    }

    fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        val bitmapJpeg = Bitmap.CompressFormat.JPEG
        val outputStream = FileOutputStream(file)

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(bitmapJpeg, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(bitmapJpeg, compressQuality, outputStream)

        return file
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                getFile = uriToFile(uri, requireContext())
                binding.ivImage.setImageURI(uri)
            }
        }
    }


}
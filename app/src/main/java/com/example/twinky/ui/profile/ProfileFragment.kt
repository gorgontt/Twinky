package com.example.twinky.ui.profile

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.twinky.R
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.rey.material.app.BottomSheetDialog
import com.rey.material.widget.TextView
import java.text.DateFormat
import java.util.Calendar
import java.util.Objects


class ProfileFragment : Fragment() {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var changeAvatar : ImageView
    private lateinit var saveButton: MaterialButton

    private lateinit var Description: String
    private lateinit var Pname: String
    private lateinit var Price: String
    private lateinit var saveCurrentDate: String
    private lateinit var saveCurrentTime: String
    private lateinit var productRandomKey: String
    private lateinit var downloadImageUrl: String
    private lateinit var categoryName: String

    private lateinit var productDescription: EditText
    private lateinit var productName: EditText
    private lateinit var productPrice: EditText

    private lateinit var ImageUri: Uri
    private val GALLERYPICK = 1
    private lateinit var loadingBar: ProgressDialog


    private lateinit var ProductImageRef: StorageReference
    private lateinit var ProductsRef: DatabaseReference

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings = view.findViewById<ImageView>(R.id.profile_settings)

        settings.setOnClickListener {
            showBottomSheet()
        }

        val userPhone = "USER_PHONE_NUMBER"  // здесь должен быть телефон зарегистрированного пользователя
        val rootRef = FirebaseDatabase.getInstance().getReference()

        rootRef.child("Users").child(userPhone).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userName = snapshot.child("name").getValue(String::class.java)
                    view.findViewById<TextView>(R.id.profile_nickname).text = userName
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ProfileFragment", "Failed to read user data", error.toException())
            }
        })
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_settings, null)
        changeAvatar = dialogView.findViewById(R.id.settings_frag_imgV)
        saveButton = dialogView.findViewById(R.id.settings_frag_btn_save)
        productDescription = dialogView.findViewById(R.id.settings_frag_ET_phone)
        productName = dialogView.findViewById(R.id.settings_frag_ET_name)
        productPrice = dialogView.findViewById(R.id.settings_frag_ET_password)



        changeAvatar.setOnClickListener {
            openGallery()
        }

        saveButton.setOnClickListener {
            validateProductData()
        }


        dialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun openGallery() {
        val galleryIntent = Intent()
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT)
        galleryIntent.setType("image/*")
        startActivityForResult(galleryIntent, GALLERYPICK)
    }

    private fun validateProductData() {
        Description = productDescription.getText().toString()
        Pname = productName.getText().toString()
        Price = productPrice.getText().toString()
        if (ImageUri == null) {
            Toast.makeText(activity, "Добавьте фото", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(activity, "Добавьте описание!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(activity, "Добавьте цену!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(activity, "Добавьте название!", Toast.LENGTH_SHORT).show()
        } else {
            StoreProductInformation()
        }
    }

    private fun StoreProductInformation() {
        loadingBar?.setTitle("Загрузка")
        loadingBar?.setMessage("Пожалуйста, подождите")
        loadingBar?.setCanceledOnTouchOutside(false)
        loadingBar?.show()
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance()
        saveCurrentDate = currentDate.format(calendar.time)
        val currentTime = DateFormat.getTimeInstance()
        saveCurrentTime = currentTime.format(calendar.time)
        productRandomKey = saveCurrentDate + saveCurrentTime
        val filePath: StorageReference =
            ProductImageRef.child(ImageUri.lastPathSegment + productRandomKey + ".jpg")
        val uploadTask = filePath.putFile(ImageUri)
        uploadTask.addOnFailureListener { e: Exception ->
            loadingBar.dismiss()
            val message = e.toString()
            Toast.makeText(activity, "Ошибка $message", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
            Toast.makeText(activity, "Изображение загружено", Toast.LENGTH_SHORT)
                .show()
            val uriTask =
                uploadTask.continueWithTask<Uri?> { task: Task<UploadTask.TaskSnapshot?> ->
                    if (!task.isSuccessful) {
                        throw Objects.requireNonNull(task.exception)!!
                    }
                    downloadImageUrl = filePath.getDownloadUrl().toString()
                    filePath.getDownloadUrl()
                }
                    .addOnCompleteListener { task: Task<Uri?> ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Фото сохранено",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            SaveProductInfoToDatabase()
                        }
                    }

        }
    }

    private fun SaveProductInfoToDatabase() {
        val productMap = HashMap<String, Any>()
        productMap["pid"] = productRandomKey
        productMap["date"] = saveCurrentDate
        productMap["time"] = saveCurrentTime
        productMap["description"] = Description
        productMap["image"] = downloadImageUrl
        productMap["category"] = categoryName
        productMap["price"] = Price
        productMap["productName"] = Pname
        ProductsRef!!.child(productRandomKey).updateChildren(productMap)
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    loadingBar!!.dismiss()
//                    val homeIntent: Intent = Intent(
//                        this@ProfileFragment,
//                        AdminCategoryActivity::class.java
//                    )
//                    startActivity(homeIntent)
                    Toast.makeText(activity, "Товар добавлен", Toast.LENGTH_LONG)
                        .show()
                } else {
                    loadingBar!!.dismiss()
                    val message =
                        Objects.requireNonNull(task.exception)
                            .toString()
                    Toast.makeText(activity, "Ошибка: $message", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


}
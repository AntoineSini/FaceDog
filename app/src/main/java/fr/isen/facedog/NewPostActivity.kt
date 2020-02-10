package fr.isen.facedog

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import fr.isen.facedog.classes.Publication
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

import kotlinx.android.synthetic.main.activity_new_post.*


class NewPostActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage


    val GALLERY = 1
    val CAMERA = 2
    val PERMISSION_CODE = 1000
    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        //authentication part
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance().reference

        pictureButton.setOnClickListener{
            showPictureDialog()
        }

        addButton.setOnClickListener{
            addToDatabase(database)
            intent= Intent(this, GeneralFeedActivity::class.java)
            startActivity(intent)
        }


    }

    fun addToDatabase(firebaseData: DatabaseReference) {

        auth.currentUser?.uid
        var title : String? =  null
        var description : String? =  null
        title = titleInputLayout.text.toString()
        description = descriptionInputLayout.text.toString()
        val newPublication = Publication("1", title, description, auth.currentUser?.uid)
        val key = firebaseData.child("publication").push().key ?: ""
        newPublication.id = key
        var id_publi = newPublication.id.toString()
        firebaseData.child("publication").child(key).setValue(newPublication)

        val storage_ref = storage.reference
        var path : String? = null
        path = id_publi + ".jpg"


        val img_ref = storage_ref.child(path)
        image_uri?.let{
            img_ref.child(path).putFile(it)
        }

    }

    fun showPictureDialog() {

        val pictureDialogBuilder = AlertDialog.Builder(this)

        pictureDialogBuilder.setNegativeButton(
            "Fermer",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val pictureDialogItems = arrayOf(
            "Selectionner une photo depuis la gallerie",
            "Prendre une photo depuis la camera"
        )
        pictureDialogBuilder.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }

        }
        val alert = pictureDialogBuilder.create()
        alert.setTitle("Veuillez faire un choix")
        alert.show()
    }

    fun choosePhotoFromGallary(){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }
    fun takePhotoFromCamera(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                //permission already granted
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            openCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == GALLERY)
            {
                pictureButton.setImageURI(data?.data)
                image_uri =  data?.data
            }
            if(requestCode == CAMERA)
            {
                pictureButton.setImageURI(image_uri)

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, CAMERA)

    }

    fun requestPermission(permission: String, requestCode: Int, handler: () -> Unit)
    {

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(
                    this,
                    "Merci d'accepter les permissions dans vos parametres",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        } else {
            handler()
        }
    }
}

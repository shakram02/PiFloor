package pifloor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
//import com.google.android.gms.drive.Drive

class UploadActivity : AppCompatActivity() {

    private var fileLocation: Uri? = null
    private var mGoogleApiClient = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        val btnBrowse = findViewById<Button>(R.id.btnBrowse)
        btnBrowse.setOnClickListener{
            Log.d("testBrowse", "hello")
            val intent = Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
        val btnUpload = findViewById<Button>(R.id.btnUpload)
        btnUpload.setOnClickListener{
            Log.d("testUpload", "hello")
            if (mGoogleApiClient == null) {

                    /**
                     * Create the API client and bind it to an instance variable.
                     * We use this instance as the callback for connection and connection failures.
                     * Since no account name is passed, the user is prompted to choose.
                     */
//                    mGoogleApiClient = new GoogleApiClient.Builder(this)
//                            .addApi(Drive.API)
//                            .addScope(Drive.SCOPE_FILE)
//                            .addConnectionCallbacks(this)
//                            .addOnConnectionFailedListener(this)
//                            .build();
//                }
//                mGoogleApiClient.connect();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == Activity.RESULT_OK){
            fileLocation = data?.data
            Log.d("testURI", fileLocation.toString())
        }
    }

}

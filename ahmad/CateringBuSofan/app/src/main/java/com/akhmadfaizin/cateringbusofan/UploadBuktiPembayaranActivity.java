package com.akhmadfaizin.cateringbusofan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class UploadBuktiPembayaranActivity extends AppCompatActivity implements SingleUploadBroadcastReceiver.Delegate {
    private static final String TAG = "AndroidUploadService";
    private final SingleUploadBroadcastReceiver uploadReceiver = new SingleUploadBroadcastReceiver();
    private ProgressDialog pDialog;
    private String jsonString = "";
    private Boolean response = false;

    //Declaring views
    private Button buttonCancel;
    private Button buttonUpload;
    private TouchImageView imageView;
    private EditText etNominal;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    //Url to upload image
    private String UPLOAD_URL;

    private String idpemesanan;
    private String tanggal;
    private int nominal;

    private String kodeTransaksi;
    private int totalBayar;
    private int dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_bukti_pembayaran);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#792525"));
        }

        getSupportActionBar().setTitle("Upload Bukti Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idpemesanan = getIntent().getStringExtra("idpemesanan");
        tanggal = getCurrentDate();

        kodeTransaksi = getIntent().getStringExtra("kodeTransaksi");
        totalBayar = getIntent().getIntExtra("totalBayar", 0);
        dp = getIntent().getIntExtra("dp", 0);

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonCancel = findViewById(R.id.buttonCancel);
        imageView = findViewById(R.id.imageView);
        etNominal = findViewById(R.id.et_nominal);

        showFileChooser();

        etNominal.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (etNominal.getText().toString().matches("^0") )
                {
                    // Not allowed
                    Toast.makeText(UploadBuktiPembayaranActivity.this, "Tidak Boleh Awalan 0", Toast.LENGTH_SHORT).show();
                    etNominal.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etNominal.getText().toString())) {
                    Toast.makeText(UploadBuktiPembayaranActivity.this, "Nominal Harus Diisi", Toast.LENGTH_SHORT).show();
                } else {
                    nominal = Integer.valueOf(etNominal.getText().toString());
                    UPLOAD_URL = getString(R.string.base_url) + "uploadbukti/" + idpemesanan + "/" + nominal + "/" + tanggal;

                    // Build an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadBuktiPembayaranActivity.this);

                    // Set a title for Alert Dialog
                    builder.setTitle("Konfirmasi");

                    // Ask the final question
                    builder.setMessage("Anda Yakin Ingin Mengupload Bukti Pembayaran Sebesar Rp " + String.format("%,d", nominal) + " ?\n");

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("Submit Bukti", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uploadMultipart();
                        }
                    });

                    // Set the alert dialog no button click listener
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

                }





            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    @Override
    public void onProgress(int progress) {
        //your implementation
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        //your implementation
    }

    @Override
    public void onError(Exception exception) {
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

        //your implementation
        String message = "Upload Error";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {

        // Create The Json Untuk Notif
        try {
            JSONObject jsonNotif = new JSONObject();

            jsonNotif.put("to", "/topics/owners");
            jsonNotif.put("priority", "high");

            JSONObject jsonNotifObject = new JSONObject();
            String bodyNotif = "Id Pemesanan \n" + idpemesanan;
            jsonNotifObject.put("body", bodyNotif);
            jsonNotifObject.put("title", "Bukti Pembayaran Baru");
            jsonNotif.put("notification", jsonNotifObject);

            Log.e(TAG, "JSON Notif Untuk Dikirim: " + jsonNotif.toString());
            jsonString = jsonNotif.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        new GoNotif().execute();

        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

        //your implementation
        String message = "Upload Sukses";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onCancelled() {
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();

        //your implementation
        String message = "Upload Cancelled";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
        // Show Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Tunggu Sebentar...");
        pDialog.setCancelable(true);
        pDialog.show();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate(this);
            uploadReceiver.setUploadID(uploadId);

            //Creating a multi part request

            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "bukti") //Adding file
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setResult(RESULT_CANCELED);
            this.finish();
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class GoNotif extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://fcm.googleapis.com/fcm/send";

            response = sh.postNotif(url, jsonString);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            String message = "";
            if(response) {
                message = "Notif Berhasil";

            }else{
                message = "Notif Gagal";
            }

            Toast.makeText(UploadBuktiPembayaranActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    // Get Current Date and Format it the return the string
    public String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        String jsonFormat = "dd-MM-yyyy";
        SimpleDateFormat sdfJson = new SimpleDateFormat(jsonFormat, Locale.US);

        return sdfJson.format(currentTime);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }


}

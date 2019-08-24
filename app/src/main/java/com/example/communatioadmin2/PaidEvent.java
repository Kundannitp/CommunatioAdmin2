package com.example.communatioadmin2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class PaidEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 235;
    EditText eventtype,eventname,fee,lastdate,address,dateofevent,information,upiid,PIname;
    Spinner collegespin;
    ImageButton logo;
    String u1;
    StorageReference mstorage;
    Uri filePath;
    FirebaseAuth myAuth;
    String pushedid;
    String collegename="miscellaneous";
    Button release;
    DatabaseReference mreference;
    String[] Nits={"NIT Patna","NIT Rourkela","NIT Tiruchirapalli","NIT Agartalla","NIT Hamirpur","NIT Pudicherry","NIT Warangal"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_event);
        Intent i=getIntent();
        u1=i.getStringExtra("pay");
        eventtype=findViewById(R.id.eventtype);
        eventname=findViewById(R.id.eventname);
        upiid=findViewById(R.id.upiid);
        PIname=findViewById(R.id.nameofPI);
        information=findViewById(R.id.informationevent);
        fee=findViewById(R.id.fee);
        if(u1.equals("unpaid")){
            fee.setVisibility(View.INVISIBLE);
            upiid.setVisibility(View.INVISIBLE);
            PIname.setVisibility(View.INVISIBLE);
        }
        lastdate=findViewById(R.id.lastdate);
        address=findViewById(R.id.address);
        dateofevent=findViewById(R.id.dataofevent);
        logo=findViewById(R.id.logo);
        collegespin=findViewById(R.id.Nittype);
        release=findViewById(R.id.release);
        myAuth=FirebaseAuth.getInstance();
        logo.setOnClickListener(this);
        mstorage= FirebaseStorage.getInstance().getReference();
        mreference= FirebaseDatabase.getInstance().getReference();
        collegespin.setOnItemSelectedListener(this);
        release.setOnClickListener(this);
        ArrayAdapter adaptspin=new ArrayAdapter(this,android.R.layout.simple_spinner_item,Nits);
        adaptspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegespin.setAdapter(adaptspin);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());

        return output;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                logo.setImageBitmap(scaleBitmap(bitmap,300,300));
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            final String d6=myAuth.getCurrentUser().getUid();
            final StorageReference sRef = mstorage.child(d6 +"/" + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(PaidEvent.this, "Successfully Published", Toast.LENGTH_SHORT).show();

                            //creating the upload object to store uploaded image details
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mreference.child("EventCollegewise").child(myAuth.getCurrentUser().getUid()).child(pushedid).child("UriImage").setValue(uri.toString());
                                    mreference.child("Events").child(pushedid).child("UriImage").setValue(uri.toString());
                                }
                            });
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v==release){
            String s1=eventtype.getText().toString();
            String s2=eventname.getText().toString();
            String s3,s9,s10;
            if(u1.equals("unpaid")) {
                s3 = "free";
                s9 = "";
                s10 = "";
            }
            else{
                s3=fee.getText().toString();
                s9=upiid.getText().toString();
                s10=PIname.getText().toString();
            }
            String s4=lastdate.getText().toString();
            String s5=address.getText().toString();
            String s6=dateofevent.getText().toString();
            String s7=myAuth.getCurrentUser().getUid();
            String s8="Nothing";
            if(!information.getText().toString().equals(""))
                s8=information.getText().toString();
            pushedid=mreference.push().getKey();
            Events j=new Events(s1,s2,s3,s4,s5,s6,s7,collegename,s8,s9,s10);
            mreference.child("EventCollegewise").child(s7).child(pushedid).setValue(j);
            mreference.child("Events").child(pushedid).setValue(j);
            uploadFile();
        }
        if(v==logo){
            showFileChooser();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        collegename=Nits[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

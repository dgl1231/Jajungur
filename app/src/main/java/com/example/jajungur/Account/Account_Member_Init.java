package com.example.jajungur.Account;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jajungur.Kategorie.Kategorie_Home;
import com.example.jajungur.Kategorie.Kategorie_Transaction_Write;
import com.example.jajungur.Menu.Menu_MyPage;
import com.example.jajungur.R;
import com.example.jajungur.WriteInfo.MemberInfo;
import com.example.jajungur.WriteInfo.PostInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Account_Member_Init extends AppCompatActivity {

    private static final String TAG ="MemberInitActivity";

    FirebaseAuth  mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user= mAuth.getCurrentUser();
    Button checkMember,pro_img_btn;
    private ImageView ImgSave;
    private String ImgUri;
    private Uri filePath;


    private String Mname ;
    private String Mphonenumber ;
    private String Memail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_member_init);

        findViewById(R.id.checkMember).setOnClickListener(onClickListener);
        findViewById(R.id.pro_img_btn).setOnClickListener(onClickListener);

        if (ContextCompat.checkSelfPermission(Account_Member_Init.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Account_Member_Init.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            if(ActivityCompat.shouldShowRequestPermissionRationale(Account_Member_Init.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                StartToast("????????? ????????? ?????????");
            }
        }
//e-mail ????????? ?????????
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.checkMember:
                    profileUpdate();
                    if(filePath != null&&Mname.length() > 0&&Mphonenumber.length() > 0)
                    {

                        Log.e("??????","??????"+MemberInfo.grade);
                        MemberInfo memberinfo = new MemberInfo(Mname, Mphonenumber, user.getEmail(),ImgUri, Account_Login.Ugrade);
                        uploader(memberinfo);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), Menu_MyPage.class);
                        startActivity(intent);//????????? ??????//??? ??????  ??????
                    }else if(filePath == null){
                        Toast.makeText(getApplicationContext(), "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                    }else if(Mname.length() == 0){
                        Toast.makeText(getApplicationContext(), "????????? ?????? ?????????.", Toast.LENGTH_SHORT).show();
                    }else if(Mphonenumber.length() == 0){
                        Toast.makeText(getApplicationContext(), "????????? ????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    }else{

                    }
                    break;
                case R.id.pro_img_btn:
                    readImg();
                    break;
            }
        }
    };

    public void profileUpdate() {
        Mname = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        Mphonenumber = ((EditText) findViewById(R.id.phoneEditText)).getText().toString();
        if (Mname.length() > 0 && Mphonenumber.length() >= 9) {
            uploadFile();
        }
        else {
        }
    }

    public void onRequestPermissionsResult(int requestCode,@NonNull String permissionsp[],@NonNull int[] grantResults){
        switch(requestCode){
            case 1:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    finish();
                    StartToast("????????? ????????? ?????????");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request????????? 0?????? OK??? ???????????? data??? ????????? ?????? ?????????
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri ????????? Bitmap?????? ???????????? ImageView??? ?????? ?????????.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImgSave.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void readImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "???????????? ???????????????."), 0);
        ImgSave = findViewById(R.id.imageView);
    }
    private void uploadFile() {
        //???????????? ????????? ????????? ??????
        if (filePath != null) {
            //????????? ?????? Dialog ?????????
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("????????????...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique??? ???????????? ?????????.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            user = FirebaseAuth.getInstance().getCurrentUser();
            //storage ????????? ?????? ???????????? ????????? ??????.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://jajungur-e1076.appspot.com").child("/User_pro_IMG/"+user.getUid()+"/"+ filename);
            //???????????????...
            ImgUri = "User_pro_IMG/" +user.getUid()+"/"+ filename;
            if(Mname.length()>0 &&Mphonenumber.length()>0)
                storageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss(); //????????? ?????? Dialog ?????? ??????
                        Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                    }
                })
                        //?????????
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //?????????
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                @SuppressWarnings("VisibleForTests") //?????? ?????? ?????? ???????????? ????????? ????????????. ??? ?????????? ?????? ???????????
                                        double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                                //dialog??? ???????????? ???????????? ????????? ??????
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                            }
                        });
            else {
            }

        } else {
            Toast.makeText(getApplicationContext(), "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploader(MemberInfo memberInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users").document(user.getUid()).set(memberInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void StartToast(String msg){Toast.makeText(this,msg,Toast.LENGTH_SHORT).show(); }

}
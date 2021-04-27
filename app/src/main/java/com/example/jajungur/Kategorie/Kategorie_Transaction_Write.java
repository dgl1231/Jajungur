package com.example.jajungur.Kategorie;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jajungur.WriteInfo.PostInfo;
import com.example.jajungur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Kategorie_Transaction_Write extends AppCompatActivity {

    private ImageView ImgSave;
    private String ImgUri;
    private Uri filePath;
    private int b;
    private static final String TAG = "WritePostActivitiy";
    private FirebaseUser user;
    private String namecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorie_transaction_write);
        findViewById(R.id.RegistrationWriteBtn).setOnClickListener(onClickListner);
        findViewById(R.id.imagebtn).setOnClickListener(onClickListner);
        Intent intent = getIntent();
        b = intent.getIntExtra("data",0);
        //권한허용문
        if (ContextCompat.checkSelfPermission(Kategorie_Transaction_Write.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Kategorie_Transaction_Write.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            if(ActivityCompat.shouldShowRequestPermissionRationale(Kategorie_Transaction_Write.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                startToast("권한을 허용해 주세요");
            }
        }

    }

    View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.RegistrationWriteBtn:
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    uploadFile();
                    if(filePath!=null){
                        writeUpdate();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent backintent = new Intent(getApplicationContext(), Kategorie_Transaction.class);
                        backintent.putExtra("data",b);
                        startActivity(backintent);
                    }else {
                        Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.imagebtn:
                    readImg();
                    break;

            }
        }
    };

    private void readImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
        ImgSave = findViewById(R.id.imageView2);
    }

    public void onRequestPermissionsResult(int requestCode,@NonNull String permissionsp[],@NonNull int[] grantResults){
        switch(requestCode){
            case 1:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    finish();
                    startToast("권한을 허용해 주세요");
                }
            }
        }
    }

    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImgSave.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();
            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            user = FirebaseAuth.getInstance().getCurrentUser();
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://jajungur-e1076.appspot.com").child("/transimages/"+user.getUid()+"/"+ filename);
            //올라가거라...
            ImgUri = "transimages/" +user.getUid()+"/"+ filename;

            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐? ㄹㅇ 누구임??
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeUpdate() {
        final String Title = ((EditText) findViewById(R.id.writetitle)).getText().toString();
        final String Content = ((EditText) findViewById(R.id.writecontent)).getText().toString();
        final String Price = ((EditText) findViewById(R.id.writePrice)).getText().toString();

        if (Kategorie_Transaction.a != 4){
            if (Title.length() > 0 && Content.length() > 0 && Price.length() > 0) {

                PostInfo writeinfo = new PostInfo(Title, Content, Price, user.getUid(), ImgUri, new Date());
                uploader(writeinfo);
            } else {
                if (Content.length() > 0 && Price.length() > 0) {
                    startToast("제목을 입력해주세요");
                }
                if (Title.length() > 0 && Price.length() > 0) {
                    startToast("내용을 입력해주세요");
                }
                if (Title.length() > 0 && Content.length() > 0 ) {
                    startToast("가격정보를 입력해주세요");
                }
            }}
        else{
            if (Title.length() > 0 && Content.length() > 0) {
                PostInfo writeinfo = new PostInfo(Title, Content, null, user.getUid(), ImgUri, new Date());
                uploader(writeinfo);
            } else {
                if (Content.length() > 0 && Price.length() > 0) {
                    startToast("제목을 입력해주세요");
                }
                if (Title.length() > 0 && Price.length() > 0) {
                    startToast("내용을 입력해주세요");
                }
            }}
    }


    private void uploader(PostInfo writeinfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(b == 0){
            namecode = "posts";
        }else if(b == 1){
            namecode = "PC";
        }else if(b == 2){
            namecode = "Mobile";
        }else if(b == 3){
            namecode = "Etc";
        }else if(b == 4){
            namecode = "Community";
        }else{

        }
        db.collection(namecode).add(writeinfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }
}

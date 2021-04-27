package com.example.jajungur.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jajungur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Customer_Notice_Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_notice_add);
        setTitle("공지사항 등록");
    }

    public void AddBtn(View view) {
        writeUpdate();
    }

    private void writeUpdate() {
        final String Title = ((EditText) findViewById(R.id.TitleField)).getText().toString();
        final String Content = ((EditText) findViewById(R.id.ContentField)).getText().toString();

        if (Title.length() > 0 && Content.length() > 0 && Content.length() > 0) {

            Customer_Notice_Data noticeinfo = new Customer_Notice_Data(Title, Content, new Date());
            uploader(noticeinfo);
            Intent backintent = new Intent(getApplicationContext(), Customer_Notice.class);
            startActivity(backintent);
        } else {
            if (Title.length() == 0 && Content.length() > 0) {
                StartToast("제목을 입력해주세요");
            }
            if (Content.length() == 0 && Title.length() > 0) {
                StartToast("내용을 입력해주세요");
            }
        }
    }

    private void uploader(Customer_Notice_Data noticeinfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notice").add(noticeinfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void StartToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
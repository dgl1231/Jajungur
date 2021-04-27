package com.example.jajungur.Kategorie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jajungur.Customer.Customer_Notice;
import com.example.jajungur.R;
import com.example.jajungur.Adapter.TransAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;

public class Kategorie_Transaction_InPost extends AppCompatActivity {


    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorie_transaction_inpost);
        setTitle("제목: " + TransAdapter.mDataset.get(TransAdapter.pos).getTitle());

        Log.e("로그","\n"+TransAdapter.title1+"\n");
        Log.e("로그","\n"+TransAdapter.contents1+"\n");
        Log.e("로그","\n"+TransAdapter.price1+"\n");
        Log.e("로그","\n"+TransAdapter.publisher1+"\n");
        Log.e("로그","\n"+TransAdapter.imguri1+"\n");
        Log.e("로그","\n"+TransAdapter.createdat1+"\n");

        TextView transpostDate = findViewById(R.id.PostDate);
        TextView transpostPrice = findViewById(R.id.PostPrice);
        TextView transpostWrite = findViewById(R.id.PostWrite);
        transpostWrite.setMovementMethod(new ScrollingMovementMethod());

        final ImageView transpostImgView = findViewById(R.id.PostImgView);

        transpostDate.setText(new SimpleDateFormat("yyyy-MM-dd" + " hh:mm").format(TransAdapter.mDataset.get(TransAdapter.pos).getcreatedAt()));
        transpostPrice.setText("가격: "+TransAdapter.mDataset.get(TransAdapter.pos).getPrice()+"원");
        transpostWrite.setText(TransAdapter.mDataset.get(TransAdapter.pos).getContents());


        mStorageRef = FirebaseStorage.getInstance().getReference().
                child(TransAdapter.mDataset.get(TransAdapter.pos).getImgUri());
        mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(transpostImgView)
                            .load(task.getResult())
                            .override(1400, 1400)
                            .into(transpostImgView);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                }
            }
        });


    }

    public void TransBtn (View view) {
        Intent PaymentIntent = new Intent(getApplicationContext(), Kategorie_Payment.class); //결제 화면이동
        startActivity(PaymentIntent);//결제 화면이동
    }
    @Override
    public void onBackPressed() {
        Intent TransIntent = new Intent(getApplicationContext(), Kategorie_Transaction.class); //결제 화면이동
        startActivity(TransIntent);//결제 화면이동
    }
}
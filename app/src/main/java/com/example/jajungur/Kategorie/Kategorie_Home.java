package com.example.jajungur.Kategorie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jajungur.Account.Account_Login;
import com.example.jajungur.Account.Account_Member_Init;
import com.example.jajungur.Menu.Menu_Community;
import com.example.jajungur.Menu.Menu_Customer;
import com.example.jajungur.Menu.Menu_MyPage;
import com.example.jajungur.R;
import com.example.jajungur.WriteInfo.MemberInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Kategorie_Home extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorie_home);
        setTitle("자전거");

        ImageView Gageonbtn = (ImageView)findViewById(R.id.GageonBtn);
        ImageView PCbtn = (ImageView)findViewById(R.id.PcBtn);
        ImageView Mobilebtn = (ImageView)findViewById(R.id.MobileBtn);
        ImageView ETCbtn = (ImageView)findViewById(R.id.ETCBtn);
        Button Communitybtn= (Button)findViewById(R.id.CommunityBtn);




        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document != null) {
                        if (document.exists()) {
                            ArrayList<MemberInfo> MemberList = new ArrayList<>();
                            MemberList.add(new MemberInfo(
                                    Account_Login.Uname = document.getData().get("name").toString(),
                                    Account_Login.Uphone = document.getData().get("phonenumber").toString(),
                                    Account_Login.Uemail = document.getData().get("email").toString(),
                                    Account_Login.Uimguri = document.getData().get("imgUri").toString(),
                                    Account_Login.Ugrade = document.getData().get("grade").toString()));
                        }
                    }
                }
            }
        });



        Gageonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction.class); //글쓰기
                intent.putExtra("data",0);
                startActivity(intent);
            }
        });

        PCbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction.class); //글쓰기
                intent.putExtra("data",1);
                startActivity(intent);
            }
        });
        Mobilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction.class); //글쓰기
                intent.putExtra("data",2);
                startActivity(intent);
            }
        });
        ETCbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction.class); //글쓰기
                intent.putExtra("data",3);
                startActivity(intent);
            }
        });
        Communitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction.class); //글쓰기
                intent.putExtra("data",4);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.homemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.MyPage:
                Intent MyIntent = new Intent(getApplicationContext(), Menu_MyPage.class); //마이페이지 화면 이동
                startActivity(MyIntent);//마이페이지 화면 이동
                return true;

            case R.id.Community:
                Intent CommuIntent = new Intent(getApplicationContext(), Menu_Community.class); //커뮤니티 화면 이동
                startActivity(CommuIntent);//커뮤니티 화면 이동
                return true;

            case R.id.Customer:
                Intent CusIntent = new Intent(getApplicationContext(), Menu_Customer.class); //고객지원 화면 이동
                startActivity(CusIntent);//고객지원 화면 이동
                return true;

            case R.id.Logout:
                Intent MainIntent = new Intent(getApplicationContext(), Account_Login.class); //고객지원 화면 이동
                startActivity(MainIntent);//고객지원 화면 이동
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }
}

package com.example.jajungur.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jajungur.Account.Account_Member_Init;
import com.example.jajungur.Account.Account_Member_Profile;
import com.example.jajungur.Community.Community_Rule;
import com.example.jajungur.Customer.Customer_FAQ;
import com.example.jajungur.Customer.Customer_Notice_Add;
import com.example.jajungur.Kategorie.Kategorie_Home;
import com.example.jajungur.R;
import com.example.jajungur.WriteInfo.MemberInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Menu_MyPage extends AppCompatActivity {

    Button BackBtn;
    TextView NMemberInfo,Customer,Inquiry,CommunityRule,NoticeAddBtn,mProfile;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_mypage);
        setTitle("마이페이지");

        mProfile = (TextView)findViewById(R.id.Profile);
        BackBtn = (Button) findViewById(R.id.BackBtn);
        NMemberInfo = (TextView) findViewById(R.id.MemberInfo);
        Customer = (TextView) findViewById(R.id.Customer);
        Inquiry = (TextView) findViewById(R.id.Inquiry);
        CommunityRule = (TextView) findViewById(R.id.CommunityRule);
        NoticeAddBtn = (TextView) findViewById(R.id.NoticeAddBtn);

        String Fieldname = MemberInfo.name;
        String FieldPhone = MemberInfo.phonenumber;
        String FieldEmail = MemberInfo.email;
        String FieldProfile = MemberInfo.ImgUri;


        TextView textName = findViewById(R.id.NameField);
        TextView textPhone = findViewById(R.id.PhoneNum);
        TextView textEmail = findViewById(R.id.EmailField);
        final ImageView imageProfile = findViewById(R.id.UserProfile);



        textName.setText(Fieldname);
        textPhone.setText(FieldPhone);
        textEmail.setText(FieldEmail);

        Log.e("로그","uri"+FieldProfile);
        mStorageRef = FirebaseStorage.getInstance().getReference().
                child(FieldProfile);
        mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(imageProfile)
                            .load(task.getResult())
                            .override(1400, 1400)
                            .into(imageProfile);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                }
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIntent = new Intent(getApplicationContext(), Kategorie_Home.class); //홈 화면 이동
                startActivity(HomeIntent);//홈화면 이동
            }
        });

        NMemberInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent InitIntent = new Intent(getApplicationContext(), Account_Member_Init.class); //개인정보 입력화면 이동
                startActivity(InitIntent);//개인정보 입력화면 이동
            }
        });

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CusIntent = new Intent(getApplicationContext(), Menu_Customer.class); //고객센터 화면 이동
                startActivity(CusIntent);//고객센터 화면 이동
            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mProfileIntent = new Intent(getApplicationContext(), Account_Member_Profile.class); //절로가짐
                startActivity(mProfileIntent);//절로가짐 ㅇㅇ
            }
        });
        Inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent InquiryIntent = new Intent(getApplicationContext(), Customer_FAQ.class); //FAQ 화면 이동
                startActivity(InquiryIntent);//FAQ 화면 이동
            }
        });

        CommunityRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RuleIntent = new Intent(getApplicationContext(), Community_Rule.class); //커뮤니티 규칙 화면 이동
                startActivity(RuleIntent);//커뮤니티 규칙 화면 이동
            }
        });

        NoticeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NAIntent = new Intent(getApplicationContext(), Customer_Notice_Add.class); //공지사항 등록 화면 이동
                startActivity(NAIntent);//공지사항 등록 화면 이동
            }
        });
    }

}

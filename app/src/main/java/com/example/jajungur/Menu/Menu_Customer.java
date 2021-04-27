package com.example.jajungur.Menu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jajungur.Account.Account_Login;
import com.example.jajungur.Account.Account_SignUp;
import com.example.jajungur.Customer.Customer_Clause;
import com.example.jajungur.Customer.Customer_FAQ;
import com.example.jajungur.Customer.Customer_Notice;
import com.example.jajungur.Customer.Customer_Policy;
import com.example.jajungur.Kategorie.Kategorie_Home;
import com.example.jajungur.R;

public class Menu_Customer extends AppCompatActivity {

    Button BackBtn,LogoutBtn,NoticeBtn,FAQBtn,ClauseBtn,PolicyBtn,DeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_customer);
        setTitle("고객지원");

        BackBtn = (Button) findViewById(R.id.BackBtn);
        LogoutBtn = (Button) findViewById(R.id.LogoutBtn);
        NoticeBtn = (Button) findViewById(R.id.NoticeBtn);
        FAQBtn = (Button) findViewById(R.id.FAQBtn);
        ClauseBtn = (Button) findViewById(R.id.ClauseBtn);
        PolicyBtn = (Button) findViewById(R.id.PolicyBtn);
        DeleteBtn = (Button) findViewById(R.id.DeleteBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIntent = new Intent(getApplicationContext(), Kategorie_Home.class); //홈 화면 이동
                startActivity(HomeIntent);//홈 화면 이동
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(getApplicationContext(), Account_Login.class); //메인화면 이동
                startActivity(MainIntent);//메인화면 이동
            }
        });

        NoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NoticeIntent = new Intent(getApplicationContext(), Customer_Notice.class); //공지사항 화면 이동
                startActivity(NoticeIntent);//공지사항 화면 이동
            }
        });

        FAQBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FAQIntent = new Intent(getApplicationContext(), Customer_FAQ.class); //FAQ 화면 이동
                startActivity(FAQIntent);//FAQ 화면 이동
            }
        });

        ClauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ClauseIntent = new Intent(getApplicationContext(), Customer_Clause.class); //이용약관 화면 이동
                startActivity(ClauseIntent);//이용약관 화면 이동
            }
        });

        PolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PolicyIntent = new Intent(getApplicationContext(), Customer_Policy.class); //개인정보 처리방침 화면 이동
                startActivity(PolicyIntent);//개인정보 처리방침 화면 이동
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(Menu_Customer.this);
                dlg.setTitle("회원 탈퇴");
                dlg.setMessage("정말 회원탈퇴를 하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Menu_Customer.this,"계정이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Account_Login.class); //로그인 화면 이동
                        startActivity(intent);//로그인 화면 이동
                    }
                });

                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent HomeIntent = new Intent(getApplicationContext(), Kategorie_Home.class); //홈 화면 이동
        startActivity(HomeIntent);//홈 화면 이동
    }
}

package com.example.jajungur.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jajungur.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account_SignUp extends AppCompatActivity {

    private static final String TAG = "SIGN";
    private FirebaseAuth mAuth;
    FirebaseUser user;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup);
        setTitle("회원가입");

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.SignUpbtn).setOnClickListener(OnClickListener);
    }

    View.OnClickListener OnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())//다음 버튼을 클릭헀을때 sign()메소드 실행
            {
                case R.id.SignUpbtn:
                    Log.e("클릭", "클릭");
                    sign();
                    break;
            }
        }
    };

    private void signOut() //사용자를 초기화 하는 메소드
    {
        FirebaseAuth.getInstance().signOut();
    }

    private void EmailCertification()//인증 메일을 보내는 파이어베이스 오픈소스
    {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Email sent.");
                    Toast.makeText(getApplicationContext(), "인증메일을 전송했습니다.", Toast.LENGTH_SHORT).show(); //이메일 보내졌을 경우 팝업
                } else {
                    Toast.makeText(getApplicationContext(), "인증메일 전송실패.", Toast.LENGTH_SHORT).show(); //이메일 보내기 실패했을경우의 팝업
                }
            }
        });
    }
    private void sign() {
        try {
            email = ((EditText) findViewById(R.id.Email)).getText().toString();//이메일을 String으로 가져옴
            password = ((EditText) findViewById(R.id.Password)).getText().toString();//패스워드를 String 으로 가져옴

            //회원가입을 하는 파이어베이스 오픈소스
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        user = mAuth.getCurrentUser();//사용자를 가져옴
                        Toast.makeText(getApplicationContext(), "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show();//성공시 팝업
                        EmailCertification();//성공시 이메일 인증을 보내고 로그인 화면으로 이동
                        signOut();
                        Intent intent = new Intent(getApplicationContext(), Account_Login.class); // 로그인 화면 이동
                        startActivity(intent);

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());//실패시 로그
                        if (password.length() < 6) {
                            Toast.makeText(getApplicationContext(), "비밀번호 6자 이상 적으십시오.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "중복된 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

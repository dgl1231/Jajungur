package com.example.jajungur.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jajungur.Kategorie.Kategorie_Home;
import com.example.jajungur.R;
import com.example.jajungur.WriteInfo.MemberInfo;
import com.example.jajungur.WriteInfo.PostInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Account_Login extends AppCompatActivity {

    public static final String TAG = "SIGN";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    public static String Uname;
    public static String Uphone;
    public static String Uemail;
    public static String Ugrade;
    public static String Uimguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login);
        setTitle("자전거 로그인");

    }

    public void LoginBtn(View view){
        Login();
    }

    public void SigninBtn(View view){
        Intent intent = new Intent(getApplicationContext(),Account_SignUp.class); // 회원가입 화면 이동
        startActivity(intent);
    }

    public void Login(){
        try {
            String email = ((EditText) findViewById(R.id.IdText)).getText().toString();//email값을 String으로 가져옴
            String password = ((EditText) findViewById(R.id.PasswordText)).getText().toString();//Password를 String으로 가져옴
            if(email.length()==0||password.length()==0)//이메일과 패스워드에 아무것도 입력이 안되어있을때
            {
                Toast.makeText(getApplicationContext(), "이메일 혹은 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();

            }
            else {
                //파이어베이스 오픈소스를 이용한 로그인과정
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");//로그인 성공시 성공로그 띄움
                            user = mAuth.getCurrentUser();//유저 내용 가져옴


                            if (user.isEmailVerified()) //이메일 인증을 했는지 확인
                            {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference docRef = db.collection("users").document(user.getUid());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if(document != null) {
                                                if (document.exists()) {
                                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                    ArrayList<MemberInfo> MemberList = new ArrayList<>();
                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                    MemberList.add(new MemberInfo(
                                                            Uname= document.getData().get("name").toString(),
                                                            Uphone= document.getData().get("phonenumber").toString(),
                                                            Uemail= document.getData().get("email").toString(),
                                                            Uimguri = document.getData().get("imgUri").toString(),
                                                            Ugrade= document.getData().get("grade").toString()));

                                                    Intent intent = new Intent(getApplicationContext(), Kategorie_Home.class);
                                                    startActivity(intent);//홈화면 이동
                                                    Log.e("로그","회원정보"+Uname+Uphone+Uemail+Ugrade);
                                                    Toast.makeText(getApplicationContext(), "환영합니다. 자전거입니다.", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Ugrade="user";
                                                    Intent intent = new Intent(getApplicationContext(), Account_Member_Init.class); //정보 기입 페이지로 이동
                                                    startActivity(intent);
                                                    Toast.makeText(getApplicationContext(), "처음 오셨군요! 회원 정보를 입력해주세요!", Toast.LENGTH_SHORT).show();
                                                    Log.d(TAG, "No such document");
                                                }
                                            }

                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "이메일을 인증해주시기 바랍니다.", Toast.LENGTH_SHORT).show();//이메일 인증이 안되었을 경우 이메일 인증해달라 요청
                            }
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());//실패시 실패 로그
                            Toast.makeText(getApplicationContext(), "이메일 혹은 패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed () {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }
}


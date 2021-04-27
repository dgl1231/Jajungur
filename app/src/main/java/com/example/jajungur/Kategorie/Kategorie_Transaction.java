package com.example.jajungur.Kategorie;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jajungur.WriteInfo.MemberInfo;
import com.example.jajungur.WriteInfo.PostInfo;
import com.example.jajungur.R;
import com.example.jajungur.Adapter.TransAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import com.example.jajungur.WriteInfo.MemberInfo;
public class Kategorie_Transaction extends AppCompatActivity {

    protected static com.example.jajungur.WriteInfo.PostInfo PostInfo;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button WriteBtn;
    private FirebaseFirestore firebaseFirestore;
    private static final String TAG = "transaction";

    private String UserGrade;
    private String ADMIN = "admin";
    private String USER = "user";


    public static String docID;
    public static String namecode;
    public static int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorie_transaction);
        Intent intent = getIntent();
        a = intent.getIntExtra("data",0);
        WriteBtn = (Button) findViewById(R.id.writebtn);
        UserGrade = MemberInfo.grade;
        Log.e("로그","grade"+MemberInfo.grade);
        Log.e("로그","grade"+UserGrade);
        if(a == 0){
            setTitle("가전제품");
            visiblebtn(a);
        }else if(a == 1){
            setTitle("PC및 주변부품");
            visiblebtn(a);
        }else if(a == 2){
            setTitle("모바일");
            visiblebtn(a);
        }else if(a == 3){
            setTitle("기타(일렉아님)");
            visiblebtn(a);
        }else if(a == 4){
            setTitle("커뮤니티");
            visiblebtn(a);
        }else{

        }

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView = (RecyclerView) findViewById(R.id.recyclertransView);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(Kategorie_Transaction.this);
                recyclerView.setLayoutManager(layoutManager);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclertransView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Kategorie_Transaction.this);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void visiblebtn(int b){
        if(b != 4) {
            if (UserGrade.equals(ADMIN)) {
                Log.e("로그", "grade" + MemberInfo.grade);
                WriteBtn.setVisibility(View.VISIBLE);
                WriteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction_Write.class);
                        startActivity(intent); //글쓰기
                    }
                });
            } else if (UserGrade.equals(USER)) {
                Log.e("로그", "grade" + MemberInfo.grade);
                WriteBtn.setVisibility(View.GONE);
            }
        }else if(b == 4)
        {
            Log.e("로그", "grade" + MemberInfo.grade);
            WriteBtn.setVisibility(View.VISIBLE);
            WriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), com.example.jajungur.Kategorie.Kategorie_Transaction_Write.class);
                    startActivity(intent); //글쓰기
                }
            });
        }
    }

    public void cardBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), Kategorie_Transaction_InPost.class); //글쓰기
        startActivity(intent); //글쓰기
    }

    protected void onResume(){
        super.onResume();
        if(a == 0){
            namecode = "posts";
        }else if(a == 1){
            namecode = "PC";
        }else if(a == 2){
            namecode = "Mobile";
        }else if(a == 3){
            namecode = "Etc";
        }else if(a == 4){
            namecode = "Community";
        }else{

        }

        if (a != 4) {
            firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = firebaseFirestore.collection(namecode);
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).limit(20).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<PostInfo> postList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new PostInfo(
                                            document.getData().get("title").toString(),
                                            document.getData().get("contents").toString(),
                                            document.getData().get("price").toString(),
                                            document.getData().get("publisher").toString(),
                                            document.getData().get("imgUri").toString(),
                                            new Date(document.getDate("createdAt").getTime())));
                                }

                                RecyclerView.Adapter mAdapter = new TransAdapter(Kategorie_Transaction.this, postList);
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        else{
            firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = firebaseFirestore.collection(namecode);
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).limit(20).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<PostInfo> postList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new PostInfo(
                                            document.getData().get("title").toString(),
                                            document.getData().get("contents").toString(),
                                            null,
                                            document.getData().get("publisher").toString(),
                                            document.getData().get("imgUri").toString(),
                                            new Date(document.getDate("createdAt").getTime())));

                                }
                                RecyclerView.Adapter mAdapter = new TransAdapter(Kategorie_Transaction.this, postList);
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }

                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
        Intent TransIntent = new Intent(getApplicationContext(), Kategorie_Home.class); //결제 화면이동
        startActivity(TransIntent);//결제 화면이동
    }
}

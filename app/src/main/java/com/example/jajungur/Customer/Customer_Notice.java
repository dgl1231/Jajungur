package com.example.jajungur.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.jajungur.Adapter.Notice_List_Adapter;
import com.example.jajungur.Menu.Menu_Customer;
import com.example.jajungur.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Customer_Notice extends AppCompatActivity {

    private static final String TAG = "notice";
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_notice);
        setTitle("자전거 공지사항");

        recyclerView = (RecyclerView) findViewById(R.id.NoticeRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Customer_Notice.this);
        recyclerView.setLayoutManager(layoutManager);

    }
    protected void onResume() {
        super.onResume();

        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("notice");
        collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).limit(20).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Customer_Notice_Data> noticedata = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                noticedata.add(new Customer_Notice_Data(
                                        document.getData().get("notice").toString(),
                                        document.getData().get("content").toString(),
                                        new Date(document.getDate("createdAt").getTime())));
                            }

                            RecyclerView.Adapter mAdapter = new Notice_List_Adapter(Customer_Notice.this,noticedata);
                            recyclerView.setAdapter(mAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.noticemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Refresh:
                Intent NoticeIntent = new Intent(getApplicationContext(), Customer_Notice.class); //화면 새로고침
                startActivity(NoticeIntent);//화면 새로고침
                return true;
        }
        return false;
    }

    public void onBackPressed() {
        Intent CustomerIntent = new Intent(getApplicationContext(), Menu_Customer.class); //홈 화면 이동
        startActivity(CustomerIntent);//홈 화면 이동
    }
}

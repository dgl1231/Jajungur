package com.example.jajungur.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jajungur.Adapter.Notice_List_Adapter;
import com.example.jajungur.Adapter.TransAdapter;
import com.example.jajungur.Menu.Menu_Customer;
import com.example.jajungur.R;

import java.text.SimpleDateFormat;

public class Customer_Notice_Content extends AppCompatActivity {

    Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_notice_content);
        setTitle("자전거 공지사항");

        BackBtn = (Button) findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), Customer_Notice.class);
                startActivity(intent1);
            }
        });

        Log.e("로그","\n"+Notice_List_Adapter.title+"\n");
        Log.e("로그","\n"+Notice_List_Adapter.content+"\n");
        Log.e("로그","\n"+Notice_List_Adapter.createdat+"\n");

        TextView TitleText = findViewById(R.id.TitleText);
        TextView DateText = findViewById(R.id.DateText);
        TextView ContentText = findViewById(R.id.ContentText);

        TitleText.setText("[공지] "+Notice_List_Adapter.NoticeDataset.get(Notice_List_Adapter.pos).getNotice());
        DateText.setText(new SimpleDateFormat("yyyy-MM-dd_hh:mm").format(Notice_List_Adapter.NoticeDataset.get(Notice_List_Adapter.pos).getCreatedAt()));
        ContentText.setText(Notice_List_Adapter.NoticeDataset.get(Notice_List_Adapter.pos).getContent());
    }
}
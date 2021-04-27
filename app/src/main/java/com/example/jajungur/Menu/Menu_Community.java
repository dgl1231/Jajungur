package com.example.jajungur.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jajungur.Kategorie.Kategorie_Home;
import com.example.jajungur.R;

public class Menu_Community extends AppCompatActivity {

    Button BackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_community);
        setTitle("커뮤니티");

        BackBtn = (Button) findViewById(R.id.BackBtn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Kategorie_Home.class); //홈 화면 이동
                startActivity(intent);//홈화면 이동
            }
        });
    }
}

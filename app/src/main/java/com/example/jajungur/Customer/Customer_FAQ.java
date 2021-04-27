package com.example.jajungur.Customer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jajungur.Adapter.FAQ_Recycler_Adapter;
import com.example.jajungur.R;

import java.util.ArrayList;
import java.util.List;

public class Customer_FAQ extends AppCompatActivity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_faq);
        setTitle("자주 묻는 질문");

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<FAQ_Recycler_Adapter.Item> data = new ArrayList<>();

        FAQ_Recycler_Adapter.Item no1 = new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.HEADER, "Q. 개인정보를 변경하고 싶어요.");
        no1.invisibleChildren = new ArrayList<>();
        no1.invisibleChildren.add(new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.CHILD, "[마이페이지 ▶ 회원정보 수정] 을 클릭하여 [회원정보 수정]" +
                                                                                                  "\n페이지로 이동하여 개인정보를 수정하실 수 있습니다."));

        FAQ_Recycler_Adapter.Item no2 = new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.HEADER, "Q. 인증메일이 안 와요.");
        no2.invisibleChildren = new ArrayList<>();
        no2.invisibleChildren.add(new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.CHILD, "1. [이메일의 스팸 차단 기능을 이용하는 경우]" +
                                                                                                  "\n 이메일의 스팸 차단 기능 때문에 인증메일이 스팸메일함에" +
                                                                                                  "\n 보관되어 있을 수도 있습니다. 스팸메일함을 확인해주세요."));
        no2.invisibleChildren.add(new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.CHILD, "2. [이메일을 잘못 입력했을 경우]" +
                                                                                                  "\n 회원가입 시 잘못된 이메일을 입력하셨을 경우 인증메일을" +
                                                                                                  "\n 전송받지 못할 수 있습니다. 이메일을 다시 입력해주세요."));
        no2.invisibleChildren.add(new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.CHILD, "3. [서버 과부하로 인해 전송이 누락된 경우]" +
                                                                                                  "\n 서버 과부하로 인해 전송이 누락됐을 수 있습니다." +
                                                                                                  "\n 이메일 재전송 버튼을 이용하여 재전송을 시도해주세요."));

        FAQ_Recycler_Adapter.Item no3 = new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.HEADER, "Q. 사기의 위험이 있지는 않을까요?");
        no3.invisibleChildren = new ArrayList<>();
        no3.invisibleChildren.add(new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.CHILD, "저희 자전거에서는 판매품을 받아 검수과정을 거친 후" +
                                                                                                  "\n판매글을 등록하여 구매의향이 있는 구매자가 있으면" +
                                                                                                  "\n입금을 받고 물품을 배송해드리고 있습니다."));
        no3.invisibleChildren.add(new FAQ_Recycler_Adapter.Item(FAQ_Recycler_Adapter.CHILD, "위 과정에서 발생할 수 있는 사기행위는 없을 것임을" +
                                                                                                  "\n자전거에서 보장해드립니다."));
        data.add(no1);
        data.add(no2);
        data.add(no3);

        recyclerview.setAdapter(new FAQ_Recycler_Adapter(data));
    }
}


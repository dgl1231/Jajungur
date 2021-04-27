package com.example.jajungur.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jajungur.Customer.Customer_Notice_Content;
import com.example.jajungur.Customer.Customer_Notice_Data;
import com.example.jajungur.Kategorie.Kategorie_Transaction_InPost;
import com.example.jajungur.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notice_List_Adapter extends RecyclerView.Adapter<Notice_List_Adapter.NoticeViewHolder> {

    public static ArrayList<Customer_Notice_Data> NoticeDataset;
    public static int pos;
    public static String title;
    public static String content;
    public static Date createdat;
    private static Activity activity1;

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        public CardView NocardView;

        public NoticeViewHolder(CardView v) {
            super(v);
            NocardView = v;

            NocardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Log.e("로그","데이터"+pos);

                        title = NoticeDataset.get(pos).getNotice();
                        content = NoticeDataset.get(pos).getContent();
                        createdat = NoticeDataset.get(pos).getCreatedAt();
                        onclick2();
                    }
                }
            });
        }
    }

    public Notice_List_Adapter(Activity activity, ArrayList<Customer_Notice_Data> myDataset) {
        NoticeDataset = myDataset;
        this.activity1 = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Notice_List_Adapter.NoticeViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        // create a new view
        CardView NocardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_notice_item, parent, false);
        final NoticeViewHolder noticeViewHolder = new NoticeViewHolder(NocardView);
        return new NoticeViewHolder(NocardView);
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        CardView cardView = holder.NocardView;
        TextView textTitle = cardView.findViewById(R.id.TitleText);
        TextView contentText = cardView.findViewById(R.id.ContentField);
        TextView createdAtTextView = cardView.findViewById(R.id.DateText);

        textTitle.setText("[공지] "+NoticeDataset.get(position).getNotice());
        contentText.setText(NoticeDataset.get(position).getContent());
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd").format(NoticeDataset.get(position).getCreatedAt()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return NoticeDataset.size();
    }

    public static void onclick2() {
        Intent intetn123 = new Intent(activity1.getApplicationContext(), Customer_Notice_Content.class); // 공지사항 열람
        activity1.startActivity(intetn123);
    }
}

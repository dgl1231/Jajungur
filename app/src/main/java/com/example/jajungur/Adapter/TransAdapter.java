package com.example.jajungur.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jajungur.Kategorie.Kategorie_Transaction;
import com.example.jajungur.Kategorie.Kategorie_Transaction_InPost;
import com.example.jajungur.WriteInfo.PostInfo;
import com.example.jajungur.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransViewHolder> {


    private Activity activity;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    private TextView createdAtTextView;
    private int c = 0;
    private String namecode;
    private static Intent intent11;

    private static Activity activity1;
    public static ArrayList<PostInfo> mDataset;
    public static int pos;
    public static String title1;
    public static String contents1;
    public static String price1;
    public static String publisher1;
    public static String imguri1;
    public static Date createdat1;


    public static class TransViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public TransViewHolder(CardView v) {
            super(v);
            cardView = v;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View a) {
                    pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Log.e("로그","데이터"+ pos);

                        title1 = mDataset.get(pos).getTitle();
                        contents1 = mDataset.get(pos).getContents();
                        price1 = mDataset.get(pos).getPrice();
                        publisher1 = mDataset.get(pos).getpublisher();
                        imguri1 = mDataset.get(pos).getImgUri();
                        createdat1 = mDataset.get(pos).getcreatedAt();
                        onclick2();
                    }
                }
            });
        }

    }

    public TransAdapter(Activity activity, ArrayList<PostInfo> myDataset) {
        mDataset = myDataset;
        this.activity1 = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransAdapter.TransViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // create a new view

        c = Kategorie_Transaction.a;
        final CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.kategorie_transaction_item, parent, false);
        final TransViewHolder transViewHolder = new TransViewHolder(cardView);
        return new TransViewHolder(cardView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TransViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textTitle = cardView.findViewById(R.id.transPost);
        TextView textPrice = cardView.findViewById(R.id.transPrice);
        createdAtTextView = cardView.findViewById(R.id.transDate);
        final ImageView imageView = cardView.findViewById(R.id.imagePost);

        mStorageRef = FirebaseStorage.getInstance().getReference().child(mDataset.get(position).getImgUri());
        mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(activity1)
                            .load(task.getResult())
                            .override(400, 400)
                            .into(imageView);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지

                }
            }
        });
        textTitle.setText("제목   : "+mDataset.get(position).getTitle());
        if(c != 4){ textPrice.setText("가격   : "+mDataset.get(position).getPrice()+"원");}
        else{
        }
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd_hh:mm").format(mDataset.get(position).getcreatedAt()));
    }

    public static void onclick2() {
        Intent intetn123 = new Intent(activity1.getApplicationContext(),Kategorie_Transaction_InPost.class); //글쓰기
        activity1.startActivity(intetn123);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


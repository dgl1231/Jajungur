package com.example.jajungur.Kategorie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jajungur.Adapter.TransAdapter;
import com.example.jajungur.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Kategorie_Payment extends AppCompatActivity {

    private StorageReference mStorageRef;
    CheckBox VirtualAccount,CreditCard,AccountTransfer,Precaution;



    private String docID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorie_payment);
        setTitle("결제하기");

        Log.e("로그","\n"+TransAdapter.price1+"\n");
        Log.e("로그","\n"+ TransAdapter.imguri1+"\n");

        final ImageView ProductPic = (ImageView) findViewById(R.id.ProductPic);
        TextView ProductPrice = (TextView) findViewById(R.id.ProductPrice);
        VirtualAccount = (CheckBox) findViewById(R.id.VirtualAccount);
        CreditCard = (CheckBox) findViewById(R.id.CreditCard);
        AccountTransfer = (CheckBox) findViewById(R.id.AccountTransfer);
        Precaution = (CheckBox) findViewById(R.id.precaution);

        VirtualAccount.setChecked(true);

        mStorageRef = FirebaseStorage.getInstance().getReference().
                child(TransAdapter.mDataset.get(TransAdapter.pos).getImgUri());
        mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(ProductPic)
                            .load(task.getResult())
                            .override(1400, 1400)
                            .into(ProductPic);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                }
            }
        });

        ProductPrice.setText("가격: "+TransAdapter.mDataset.get(TransAdapter.pos).getPrice()+"원");
    }

    public void VitualBox(View view) {
        if(VirtualAccount.isChecked() == true){
            CreditCard.setChecked(false);
            AccountTransfer.setChecked(false);
        } else {

        }
    }

    public void CardBox(View view) {
        if(CreditCard.isChecked() == true){
            VirtualAccount.setChecked(false);
            AccountTransfer.setChecked(false);
        } else {

        }
    }

    public void AccountBox(View view) {
        if(AccountTransfer.isChecked() == true){
            VirtualAccount.setChecked(false);
            CreditCard.setChecked(false);
        } else {

        }
    }

    public void CancelBtn(View view) {
        Intent InPostIntent = new Intent(getApplicationContext(), Kategorie_Transaction_InPost.class); //결제 화면이동
        startActivity(InPostIntent);//결제 화면이동
    }

    public void PaymentBtn(View view) {
        if(Precaution.isChecked() == false) {
            StartToast("약관에 동의해주세요.");
        } else {


            Delet();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StartToast("구매가 완료되었습니다.");
            Intent HomeIntent = new Intent(getApplicationContext(), Kategorie_Home.class); //결제 화면이동
            startActivity(HomeIntent);//결제 화면이동
        }
    }
    public void Delet(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference desertRef = storageRef.child(TransAdapter.mDataset.get(TransAdapter.pos).getImgUri());
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.e("로그","들어와지긴하노 ㅋㅋ\n"+Kategorie_Transaction.namecode);
        String urikk = TransAdapter.mDataset.get(TransAdapter.pos).getImgUri();
        db.collection(Kategorie_Transaction.namecode)
                .whereEqualTo("imgUri",urikk)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                docID = document.getId();
                                Log.e("로그","왜오류야씨@발"+docID);
                                deletdoc(document.getId());
                            }
                        } else {
                        }
                    }
                });
    }
    private void deletdoc(String docid){
        Log.e("로그","왜오류야씨@발"+docid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Kategorie_Transaction.namecode).document(docid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Log.e("로그","너무깊게들어와버림 ㄹㅇㅋㅋ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    private void StartToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
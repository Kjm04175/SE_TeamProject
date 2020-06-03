package com.example.choppingmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    User user;
    Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendBtn = findViewById(R.id.messageBtn);
        childUpdates=new HashMap<>();
        info = new Info("김철수","M","19970101","01012341234","성남시 가천대학교");
        user = new User("KCS1234","1q2w3e4r!",info);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //databaseReference.child("message").push().setValue("2");
                userValue=user.toMap();
                childUpdates.put("/User/"+"KCS1234",userValue);
                databaseReference.updateChildren(childUpdates);
            }
        });
    }
}

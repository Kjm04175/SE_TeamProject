package com.example.choppingmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainActivity;
    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public DatabaseReference databaseReference = firebaseDatabase.getReference();

    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    User user;
    Info info;
    Button getMessage;
    LoginFragment loginScreen;
    SignupFragment signupScreen;

    enum Screen{
        Login, SignUp,OpenMarket,Community
    }
    private void init()
    {
        mainActivity =this;
        loginScreen=new LoginFragment();
        signupScreen=new SignupFragment();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentLayout, loginScreen).commit();

        Button sendBtn = findViewById(R.id.messageBtn);
        getMessage = findViewById(R.id.searchBtn);
        childUpdates=new HashMap<>();
        info = new Info("김철수","M","19970101","01012341234","성남시 가천대학교");
        user = new User("KCS1234","1q2w3e4r!",info);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("User").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("db","DocumentSnapShot added with ID: "+documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Error adding document",e);
                    }
                });
            }
        });
        getMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetData.class);
                startActivity(intent);
            }
        });
    }
    public static Map<String, Object> JSONtoMap(DataSnapshot dataSnapshot)
    {
        Map<String, Object> map = new HashMap<>();
        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            String key = ds.getKey();
            Object val = ds.getValue();
            map.put(key,val);
        }
        return map;
    }
    public void setScreen(Screen screen)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(screen==Screen.Login) {
            transaction.replace(R.id.fragmentLayout, loginScreen).commit();
        }
        else if(screen==Screen.SignUp) {
            signupScreen=new SignupFragment();
            transaction.replace(R.id.fragmentLayout, signupScreen).commit();
        }
        if(screen!=Screen.Login)
            transaction.addToBackStack(null);
    }
}

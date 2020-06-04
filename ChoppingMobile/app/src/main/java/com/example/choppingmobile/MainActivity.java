package com.example.choppingmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.login.Login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    User user;
    Info info;
    Button getMessage;
    LoginFragment loginScreen;
    SignupFragment signupScreen;
    FirebaseFirestore db;
    public static MainActivity mainActivity;
    enum Screen{
        Login, SignUp,OpenMarket,Community
    }
    private void init()
    {
        mainActivity =this;
        db = FirebaseFirestore.getInstance();
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
                //databaseReference.child("message").push().setValue("2");
                userValue=user.toMap();
                childUpdates.put("/User/"+"KCS1234",userValue);
                databaseReference.updateChildren(childUpdates);
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
    public void setScreen(Screen screen)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(screen==Screen.Login) {
            transaction.replace(R.id.fragmentLayout, loginScreen).commit();
        }
        else if(screen==Screen.SignUp) {
            transaction.replace(R.id.fragmentLayout, signupScreen).commit();
        }
        transaction.addToBackStack(null);
    }
}

package com.example.choppingmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GetData extends AppCompatActivity {

    FirebaseDatabase mDBinstance=null;
    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdate = null;
    Map<String, Object> userValue=null;
    UserInfo userInfo = null;
    Button getBtn;
    TextView printTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        mDBinstance=FirebaseDatabase.getInstance();
        mDBReference = mDBinstance.getReference("User/KCS1234");
        getBtn=findViewById(R.id.getDataBtn);
        printTxt = findViewById(R.id.printTView);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> user = new HashMap<>();
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            String key = ds.getKey();
                            Object val = ds.getValue();
                            user.put(key,val);
                        }
                        User Value = new User();
                        Value.setUser(user);
                        Log.w("Data",Value.userInfo.address);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Database2","Failed to read Value.",databaseError.toException());
                    }
                });
            }
        });
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                Log.d("Database", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });
    }
}

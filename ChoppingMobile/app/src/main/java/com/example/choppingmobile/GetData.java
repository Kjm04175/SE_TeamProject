package com.example.choppingmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class GetData extends AppCompatActivity {

    FirebaseDatabase mDBinstance=null;
    DatabaseReference mDBReference = null;
    FirebaseFirestore db=null;
    HashMap<String, Object> childUpdate = null;
    Map<String, Object> userValue=null;
    UserInfo userInfo = null;
    Button getBtn;
    TextView printTxt;
    Query getDataTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        mDBinstance=FirebaseDatabase.getInstance();
        db = MainActivity.mainActivity.db;
        mDBReference = mDBinstance.getReference("User/KCS1234");
        getDataTest=db.collection("User").whereGreaterThan("ID","KCS");//mDBinstance.getReference("User").orderByChild("ID");
        getBtn=findViewById(R.id.getDataBtn);
        printTxt = findViewById(R.id.printTView);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                getDataTest.startAt("KCS").endAt("KCS\uf8ff").addValueEventListener(new ValueEventListener() {
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
                        if(dataSnapshot==null)
                            Log.e("Search","null");
                        else
                            Log.e("Result",dataSnapshot.toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Database2","Failed to read Value.",databaseError.toException());
                    }
                });*/
                getDataTest.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        Log.d("getdb2",document.getId()+"==>"+document.getData());
                                    }
                                } else{
                                    Log.w("getdb2",task.getException());
                                }
                            }
                        });
                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        Log.d("getdb",document.getId()+"==>"+document.getData());
                                    }
                                } else{
                                    Log.w("getdb",task.getException());
                                }
                            }
                        });
            }
        });
        /*
        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Query query = mDBReference;
                if(query.orderByChild("ID").equalTo("KCS*")!=null)
                    Log.e("search",query.orderByChild("ID").equalTo("KCS*").toString());
                else
                    Log.e("search","null");
                String value = dataSnapshot.getValue().toString();
                Log.d("Database", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });*/
    }
}

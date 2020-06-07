package com.example.choppingmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;

public class ServiceActivity extends AppCompatActivity {

    public static ServiceActivity serviceActivity=null;
    public FirebaseFirestore db=null;
    private Query dataQuery=null;
    private HashMap<State, Fragment> pageList=null;


    private Button marketBtn;
    private Button communityBtn;
    private Button userPageBtn;
    private Community communityFragment=null;
    private ServiceMain serviceMainFragment=null;
    private OpenMarket marketFragment=null;
    private UserPage userPageFragment=null;

    enum State{
        Community, OpenMarket,User, mainPage
    };
    private void initPages()
    {
        communityFragment=new Community();
        serviceMainFragment = new ServiceMain();
        marketFragment=new OpenMarket();
        userPageFragment=new UserPage();

        pageList.put(State.Community,communityFragment);
        pageList.put(State.mainPage,serviceMainFragment);
        pageList.put(State.OpenMarket,marketFragment);
        pageList.put(State.User,userPageFragment);
    }
    private void initBtn()
    {
        marketBtn=findViewById(R.id.onenMarketBtn);
        communityBtn=findViewById(R.id.communityBtn);
        userPageBtn=findViewById(R.id.userTabBtn);
    }
    private void init()
    {
        serviceActivity=this;
        db=FirebaseFirestore.getInstance();
        pageList= new HashMap<>();
        initPages();
        initBtn();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        init();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.serviceMainLayout,serviceMainFragment).commit();
        userPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScreen(State.User);
            }
        });

        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScreen(State.Community);
            }
        });

        marketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScreen(State.OpenMarket);
            }
        });
    }

    public void setScreen(State state)
    {
        getSupportFragmentManager().popBackStack(state.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment fragment = pageList.get(state);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment!=null)
            transaction.replace(R.id.serviceMainLayout,fragment).commit();
        transaction.addToBackStack(state.toString());
    }
}

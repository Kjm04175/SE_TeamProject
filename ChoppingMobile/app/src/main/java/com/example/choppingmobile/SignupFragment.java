package com.example.choppingmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private EditText idEdit;
    private EditText pwEdit;
    private EditText confirmPwEdit;
    private EditText nameEdit;
    private EditText genderEdit;
    private EditText phoneEdit;
    private EditText birthEdit;
    private EditText addrEdit;
    private ArrayList<EditText> editLsit;
    private Button duplicateBtn;
    private Button submitBtn;

    private User user;

    public void init(ViewGroup vg)
    {
        editLsit=new ArrayList<>();

        idEdit=vg.findViewById(R.id.id_signUpEdit);
        editLsit.add(idEdit);
        pwEdit=vg.findViewById(R.id.password_signUpEdit);
        editLsit.add(pwEdit);
        confirmPwEdit=vg.findViewById(R.id.confirmPassword_signUpEdit);
        editLsit.add(confirmPwEdit);
        nameEdit=vg.findViewById(R.id.nameEdit);
        editLsit.add(nameEdit);
        genderEdit=vg.findViewById(R.id.genderEdit);
        editLsit.add(genderEdit);
        phoneEdit = vg.findViewById(R.id.phoneNumberEdit);
        editLsit.add(phoneEdit);
        birthEdit=vg.findViewById(R.id.birthDayEdit);
        editLsit.add(birthEdit);
        addrEdit=vg.findViewById(R.id.addressEdit);
        editLsit.add(addrEdit);

        duplicateBtn = vg.findViewById(R.id.idDuplicateconfirmBtn);
        submitBtn = vg.findViewById(R.id.signUpSubmitBtn);

        user = new User();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_signup, container, false);
        init(viewGroup);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()) {
                    signUp(composeUserData());
                    Toast.makeText(getContext(),"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show();
                    MainActivity.mainActivity.setScreen(MainActivity.Screen.Login);
                }
                else
                    Toast.makeText(getContext(), "필수 입력 항목이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return viewGroup;
    }
    private boolean validation()
    {
        //여기에는 조건 넣기. ex, pw는 8글자 이하 등등
        boolean result = true;
        for(EditText editText:editLsit)
        {
            Log.e("getText", Integer.toString(editLsit.size()));

            if(editText.getText().toString().equals(""))
                result=false;
        }
        return result;
    }
    private User composeUserData()
    {
        String name=nameEdit.getText().toString();
        String gender = genderEdit.getText().toString();
        String birthday=birthEdit.getText().toString();
        String phoneNum=phoneEdit.getText().toString();
        String address=addrEdit.getText().toString();

        String id = idEdit.getText().toString();
        String pw = pwEdit.getText().toString();
        user.userInfo.setInfo(name,gender,birthday,phoneNum,address);
        user.setUser(id, pw);

        return user;
    }
    private void signUp(User user)
    {
        HashMap<String,Object> childUpdate = new HashMap<>();
        childUpdate.put("/User/"+user.id,user.toMap());
        DatabaseReference reference= MainActivity.mainActivity.databaseReference;
        reference.updateChildren(childUpdate);
    }
}

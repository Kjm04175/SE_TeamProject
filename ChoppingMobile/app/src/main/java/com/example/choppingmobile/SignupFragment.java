package com.example.choppingmobile;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


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
    private boolean usableID =false;

    private EditText idEdit;
    private EditText pwEdit;
    private EditText confirmPwEdit;
    private EditText nameEdit;
    private EditText genderEdit;
    private EditText phoneEdit;
    private EditText birthEdit;
    private EditText addrEdit;

    private TextView duplicateText;

    private ArrayList<EditText> editLsit;
    private Button duplicateBtn;
    private Button submitBtn;

    private User user;

    private FirebaseFirestore db;
    private Query userQuery;

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

        duplicateText=vg.findViewById(R.id.duplicateText);

        db=MainActivity.mainActivity.db;
        userQuery = db.collection("User");
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
                    MainActivity.mainActivity.setScreen(MainActivity.Screen.Login);
                }
            }
        });
        duplicateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputID=idEdit.getText().toString();
                userQuery.whereEqualTo("id",inputID)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult().isEmpty())
                                {
                                    usableID =true;
                                    duplicateText.setText("ID를 사용할 수 있습니다.");
                                    duplicateText.setTextColor(Color.BLUE);
                                }
                                else
                                {
                                    usableID=false;
                                    duplicateText.setText("이미 사용중인 ID입니다.");
                                    duplicateText.setTextColor(Color.RED);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Error: Connection Failure",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return viewGroup;
    }
    private boolean validation()//id, 비밀번호 복잡성, 생일 -> DateType으로, phone number형식 맞춰야함.
    {
        //여기에는 조건 넣기. ex, pw는 8글자 이하 등등
        for(EditText editText:editLsit)
        {
            Log.e("getText", Integer.toString(editLsit.size()));

            if(editText.getText().toString().equals(""))
            {
                Toast.makeText(getContext(), "필수 입력 항목이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!usableID)
        {
            Toast.makeText(getContext(),"ID 중복확인이 필요합니다.",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pwEdit.getText().toString().equals(confirmPwEdit.getText().toString())) {
            Toast.makeText(getContext(),"비밀번호 확인이 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
        db.collection("User").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getContext(),"회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error: 회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

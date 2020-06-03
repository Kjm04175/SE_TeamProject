package com.example.choppingmobile;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String id;
    public String password;
    public Info userInfo;
    public User()
    {

    }
    public User(String _id, String _password, Info _info)
    {
        id=_id;
        password=_password;
        userInfo = _info;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID",id);
        result.put("Password",password);
        result.put("Info",userInfo.toMap());
        return result;

    }

}

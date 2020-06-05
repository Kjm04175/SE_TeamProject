package com.example.choppingmobile;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String id;
    public String password;
    public Info userInfo;
    public User()
    {
        id="null";
        password="null";
        userInfo=new Info();
    }
    public User(String _id, String _password, Info _info)
    {
        id=_id;
        password=_password;
        userInfo = _info;
    }
    public void setUser(String _id, String _password, Info _info)
    {
        id=_id;
        password=_password;
        userInfo = _info;
    }
    public void setUser(String _id, String _password)
    {
        id=_id;
        password=_password;
    }
    public void setUser(Map<String, Object> data)
    {
        id=(String)data.get("ID");
        password=(String) data.get("Password");
        userInfo.setInfo((HashMap<String,Object>)data.get("Info"));
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

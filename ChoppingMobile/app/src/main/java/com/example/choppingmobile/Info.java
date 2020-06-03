package com.example.choppingmobile;

import java.util.HashMap;
import java.util.Map;

public class Info {
    public String name;
    public String gender;
    public String birth;
    public String pNumber;
    public String address;

    public Info()
    {

    }
    public Info(String _name, String _gender, String _birth, String _pNumber, String _address)
    {
        name=_name;
        gender=_gender;
        birth=_birth;
        pNumber=_pNumber;
        address=_address;
    }
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name",name);
        result.put("Gender",gender);
        result.put("Birth",birth);
        result.put("PhoneNumber",pNumber);
        result.put("Address",address);

        return result;
    }
}

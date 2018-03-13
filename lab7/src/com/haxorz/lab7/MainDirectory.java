package com.haxorz.lab7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class MainDirectory implements Directory {

    //set because there cannot be identical people
    //and even if there were, itd be rather useless
    private List<Employee> _dir = new ArrayList<>();

    public boolean add(String e){
        Gson gson = new Gson();
        Collection<Employee> employees = gson.fromJson(e, new TypeToken<Collection<Employee>>(){}.getType());
        return _dir.addAll(employees);
    }
    public boolean print(){
        if (_dir.isEmpty()) System.out.println("<empty directory>");

        Collections.sort(_dir);

        for(Employee e : _dir){
            System.out.println(e.toString());
        }
        return true;
    }
    public void clear(){
        _dir.clear();
    }
}

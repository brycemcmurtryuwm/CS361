package com.haxorz.lab7;

import com.google.gson.Gson;

import java.util.Collection;

/**
 * Transports employee objects from the DirectoryProxy
 *  to the Main Directory
 */
public class Transport {

    //JSON
    public static String EmployeesAsJson(Collection<Employee> e){
        Gson gson = new Gson();

        return gson.toJson(e);
    }



}

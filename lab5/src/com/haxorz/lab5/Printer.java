package com.haxorz.lab5;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Printer {

    public String print(String toPrint, boolean includeTimeStamp){
        //<time> <transaction> <amount>
    	final SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	
    	if(includeTimeStamp == false){
    		return toPrint;
    	}
    	return sdf.format(timestamp) + " " + toPrint;
    }

}

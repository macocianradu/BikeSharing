package com.crud.bikeshare.bikeshare;

import java.util.ArrayList;

public class ArrayToString {

    public static ArrayList<Long> toArray(String string){
        String[] aux = string.split(", ");
        ArrayList<Long> result = new ArrayList<>();
        if(string.length() == 0){
            return result;
        }
        for(String s : aux){
            result.add(Long.valueOf(s));
        }
        return result;
    }

    public static String toString(ArrayList<Long> ids){
        StringBuilder string = new StringBuilder();
        for(Long id : ids){
            string.append(id);
            string.append(", ");
        }
        return string.toString();
    }
}

package org.mule.extension.multi.authenticator.internal.utils;

import java.util.ArrayList;

public class ArrayUtils {

    public static boolean ArrayListHasHash(String string, ArrayList<String> array){
        // For Loop for iterating ArrayList
        for (int i = 0; i < array.size(); i++){
            if (array.get(i).toLowerCase().equals(string.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}

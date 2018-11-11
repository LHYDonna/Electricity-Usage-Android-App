package com.example.linhongyu.smarter;

/**
 * Created by linhongyu on 30/4/18.
 */

public class Validation {

    public boolean checkStringLength(String input, int limit)
    {
        if (input.length() >= limit)
            return true;
        else
            return false;
    }

    public boolean checkStringLegal(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < input.length(); i++)
        {
            if (!Character.isLetterOrDigit(c[i]))
                return false;
        }
        return true;
    }

    public boolean validPassword(String password)
    {
        int numberLimit = 6;
        if (checkStringLength(password,numberLimit) && checkStringLegal(password))
            return true;
        return false;
    }

    public boolean validMobile(String mobile){
        char[] c = mobile.toCharArray();
        for (int i = 0; i < mobile.length(); i++)
        {
            if (!Character.isDigit(c[i]))
                return false;
        }
        return true;
    }
}

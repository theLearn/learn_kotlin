package com.example.hongcheng.common.util;

import android.content.Context;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by hongcheng on 16/3/26.
 */
public final class StringUtils {
    private StringUtils(){}

    public static boolean isEmpty(String str){
        if(str == null || str.isEmpty()){
            return true;
        }
        return false;
    }

    public static String byte2String(byte[] bytes)
    {
        if (bytes == null)
        {
            return "";
        }
        else
        {
            return new String(bytes, Charset.forName("UTF-8"));
        }
    }

    public static String bytesToHexString(byte[] bArray)
    {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;

        for (int i = 0; i < bArray.length; i++)
        {
            sTemp = Integer.toHexString(0xFF & bArray[i]);

            if (sTemp.length() < 2)
            {
                sb.append(0);
            }

            sb.append(sTemp.toLowerCase(Locale.getDefault()));
        }

        return sb.toString();
    }

    public static String replaceStr(Context context, int resId, String... replaces){
        String result = context.getResources().getString(resId);
        if(isEmpty(result)){
            return "";
        }

        if(replaces == null || 0 == replaces.length){
            return result;
        }

        for(int i = 0 ; i < replaces.length ; i++){
            result = result.replaceFirst("#a#", replaces[i]);
        }

        return result;
    }

    public static boolean isEmail(String email){
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean checkPassword(String password){
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}

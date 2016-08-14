package com.atguigu.demoim.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class SpUtil {

    public static final String IS_NEW_INVITE = "is_new_invite";
    private static SharedPreferences sp;

    private SpUtil() {
    }

    private static SpUtil instance = new SpUtil();

    public static SpUtil getInstance(Context context) {
        if(sp==null) {

            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return instance;
    }

    //保存
    public void save(String key,Object value) {
        if(value instanceof String) {
            sp.edit().putString(key, (String) value).commit();
        }else if(value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).commit();
        }else if(value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }
    }

    //获取
    public String getString(String key,String defValue) {
        return sp.getString(key,defValue);
    }

    public int getInt(String key,int defValue) {
        return sp.getInt(key, defValue);
    }

    public boolean getBoolean(String key,boolean deValue) {
        return sp.getBoolean(key,deValue);
    }
}

package com.example.waitou.rxjava.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * SharedPreference工具
 * Created by waitou on 16/7/27.
 */
public class PreUtil {

    private SharedPreferences mPreferences;
    private static PreUtil sInstance;
    private Context mContext;

    private PreUtil(Context context) {
        this.mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreUtil getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        sInstance = new PreUtil(context);
    }

    public void set(String key, Object value) {
        SharedPreferences.Editor edit = mPreferences.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Set) {
            edit.putStringSet(key, (Set<String>) value);
        } else if (value instanceof List) {
            StringBuilder sb = new StringBuilder();
            for (String str : (List<String>) value) {
                sb.append(str);
                sb.append("#$#");
            }
            edit.putString(key, sb.toString());
        } else {
            throw new RuntimeException("Not support type");
        }
        edit.apply();
        edit.clear();
    }

    /**
     * 保存对象
     */
    public void set(String key, Serializable serializable) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(mContext.openFileOutput(
                    key, Activity.MODE_PRIVATE));
            out.writeObject(serializable);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 取出对象
     */
    public Serializable getSerializable(String key) {
        Serializable obj = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(
                    mContext.openFileInput(key));
            obj = (Serializable) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    public String getString(String key) {
        return mPreferences.getString(key, null);
    }

    public Boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    public Integer getInteger(String key) {
        return mPreferences.getInt(key, 0);
    }

    public Float getFloat(String key) {
        return mPreferences.getFloat(key, 0.0f);
    }

    public Float getFloat(String key, float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }

    public Long getLong(String key) {
        return mPreferences.getLong(key, 0);
    }

    public Set<String> getStringSet(String key) {
        return mPreferences.getStringSet(key, null);
    }

    public List<String> getStringList(String key) {
        String value = getString(key);
        if (!TextUtils.isEmpty(value)) {
            String[] splitStr = value.split("#\\$#");
            return Arrays.asList(splitStr);
        }
        return null;
    }


    /**
     * 检查指定的key是否有值 ,存入的对象不能查询
     */
    public boolean hasKey(String key) {
        return mPreferences.contains(key);
    }
}

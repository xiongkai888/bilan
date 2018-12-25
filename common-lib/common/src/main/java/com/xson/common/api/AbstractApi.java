package com.xson.common.api;

import android.content.Context;

import com.xson.common.utils.L;
import com.xson.common.utils.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Milk <249828165@qq.com>
 */
public abstract class AbstractApi {

    private int p;
//    public String API_URL = "http://47.75.105.214/api/";
    public static String API_URL;

    public static enum Method {
        GET,
        POST,
    }

    public static enum Enctype {
        TEXT_PLAIN,
        MULTIPART,
    }

    protected abstract String getPath();

    public abstract Method requestMethod();

    public Enctype requestEnctype() {
        return Enctype.TEXT_PLAIN;
    }

    public String getUrl() {
        return API_URL + getPath();
    }

//    public void setAPI_URL(String API_URL){
//        this.API_URL = API_URL;
//    }

    public void setPage(int page) {
        this.p = page;
    }

    public Map<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<String, Object>();

        Field[] field;
        Class clazz = getClass();
        try {
            for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
                field = c.getDeclaredFields();
                for (Field f : field) {
                    f.setAccessible(true);
                    Object value = f.get(this);
                    if (value != null && !f.getName().contains("$") && !"API_URL".equals(f.getName()) && !"serialVersionUID".equals(f.getName())) {
                        if (StringUtils.isSame("mDefault", f.getName())) {
                            params.put("default", value);//关键字处理
                        } else {
                            params.put(f.getName(), value);
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            L.e(e);
        }
        if (p > 0) {
            params.put("p", p);
        } else {
            params.remove("p");
        }
        return params;
    }


    public void handleParams(Context context, Map<String, Object> params) {

        //move file field
        HashMap<String, Object> fileMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof File)
                fileMap.put(entry.getKey(), entry.getValue());
            else if (value instanceof Iterable) { // List<File>, Collection<File>, etc...
                Iterator iter = ((Iterable) value).iterator();
                if (iter.hasNext() && iter.next() instanceof File) {
                    fileMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (!fileMap.isEmpty())
            for (String key : fileMap.keySet()) {
                params.remove(key);
            }
        params.putAll(fileMap);
    }

}

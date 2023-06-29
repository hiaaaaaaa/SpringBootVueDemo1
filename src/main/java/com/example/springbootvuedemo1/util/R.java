package com.example.springbootvuedemo1.util;

import java.util.HashMap;

public class R extends HashMap<String, Object> {

    public R(){
        put("msg","success");
        put("code",0);
    }

    public R put(String key,Object value){
        super.put(key, value);
        return this;
    }

    public R setData(Object data){
        put("data",data);
        return this;
    }

    public R setDataAndCount(Object data,Integer count){
        put("data",data);
        put("count",count);
        return this;
    }

    public R setRole(String role){
        put("role",role);
        return this;
    }

    public static R ok(String msg){
        R r = new R();
        r.put("msg",msg);
        return r;
    }

    public static R ok(HashMap<String,Object> map){
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok(){
        return new R();
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("msg",msg);
        r.put("code",code);
        return r;
    }

}

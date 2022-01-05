package com.cloudapp.web.main.call;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ModelBean {

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public <T> Object getData(Class<T> clazz) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(this.data.toString(), clazz);
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

}

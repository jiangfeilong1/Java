package com.example.demo.error;

/**
 * @author Administrator
 */
public class ErrorInfo<T> {
    private final static Integer SUCCESS =200;
    private final static Integer ERROR =100;
    private String url;
    private String message;
    private Integer code;
    private T data;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Integer getSUCCESS() {
        return SUCCESS;
    }

    public static Integer getERROR() {
        return ERROR;
    }
}

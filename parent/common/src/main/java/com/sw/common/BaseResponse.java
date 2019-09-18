package com.sw.common;

public class BaseResponse<T> {

    private int code;
    private String message;
    private T data;


    public static BaseResponse build(Object data){
        return new BaseResponse<>(data);
    }

    public BaseResponse(T data) {
        this.code = BaseResult.SUCCESS.code;
        this.message = BaseResult.SUCCESS.message;
        this.data = data;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

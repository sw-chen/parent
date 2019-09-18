package com.sw.common;


import org.springframework.util.StringUtils;

public class BaseException extends RuntimeException {

    private int errCode;

    public BaseException(BaseResult result){
        this(result.code, result.message);
    }

    public BaseException(BaseResult result, String errorMessage){
        this(result.code, StringUtils.isEmpty(errorMessage)  ? result.message : errorMessage);
    }

    public BaseException(Integer result , String errorMessage) {
        this(result, errorMessage, null);
    }

    public BaseException(Integer errorNum , String errorMessage, Throwable cause) {
        super(errorMessage, cause, false, false);
    }

    public int getErrCode() {
        return errCode;
    }
}

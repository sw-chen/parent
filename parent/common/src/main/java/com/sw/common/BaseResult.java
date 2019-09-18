package com.sw.common;

public enum BaseResult {

    SUCCESS(0, "请求成功"),
    FAIL(1, "请求失败"),
    PARAM_ERROR(2, "参数错误"),

    FILE_EMPTY(1001, "上传文件为空"),
    FILE_FAIL(1002, "上传文件失败")
    ;

    public int code;
    public String message;

    BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }


}

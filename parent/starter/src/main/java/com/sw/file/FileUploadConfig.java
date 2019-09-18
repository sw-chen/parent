package com.sw.file;

import lombok.Data;

@Data
public class FileUploadConfig {

    //上传服务器接口
    private String fileUploadUrl;

    //保存路径前缀
    private String savePathPrefix;

    //读取路径前缀
    private String readPathPrefix;
}

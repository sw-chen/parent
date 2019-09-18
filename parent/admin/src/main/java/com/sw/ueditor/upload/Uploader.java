package com.sw.ueditor.upload;


import com.sw.file.FileUploadConfig;
import com.sw.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public class Uploader {
    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;
    private FileUploadConfig fileUploadConfig;


    public Uploader(HttpServletRequest request, Map<String, Object> conf, FileUploadConfig fileUploadConfig) {
        this.request = request;
        this.conf = conf;
        this.fileUploadConfig = fileUploadConfig;
    }

    public final State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        } else {
            state = BinaryUploader.save(this.request, this.conf, this.fileUploadConfig);
        }

        return state;
    }
}

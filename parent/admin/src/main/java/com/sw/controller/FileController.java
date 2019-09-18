package com.sw.controller;

import com.sw.common.BaseResponse;
import com.sw.service.impl.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    FileService fileService;

    @PostMapping(value = "upload")
    @ResponseBody
    public BaseResponse<Boolean> upload(@RequestParam("file") MultipartFile file) {
        return new BaseResponse<>(fileService.upload(file));
    }

    @GetMapping(value = "/read")
    @ResponseBody
    public void getUrlFile(String url, HttpServletResponse response) {
        fileService.read(url,response);
    }
}
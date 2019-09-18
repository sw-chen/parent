package com.sw.controller;

import com.sw.file.FileUploadConfig;
import com.sw.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * 用于处理关于ueditor插件相关的请求
 *
 * @author Guoqing
 * @date 2017-11-29
 */
@RestController
@CrossOrigin
@RequestMapping("/ueditor")
public class UeditorController {

    @Autowired
    FileUploadConfig fileUploadConfig;

    @RequestMapping(value = "/exec")
    @ResponseBody
    @ApiIgnore
    public String exec(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String path = request.getSession().getServletContext().getRealPath("/");
        System.out.println(path);
        return new ActionEnter(request, path, fileUploadConfig).exec();
    }

}

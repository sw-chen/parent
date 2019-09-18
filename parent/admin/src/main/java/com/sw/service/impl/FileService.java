package com.sw.service.impl;

import com.sw.common.BaseException;
import com.sw.common.BaseResult;
import com.sw.controller.FileController;
import com.sw.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${dfs.file.upload-path}")
    private String uploadPath;


    public boolean upload(MultipartFile file) {

        if (file.isEmpty()) {
            throw new BaseException(BaseResult.FILE_EMPTY);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);

        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);

        // 文件上传路径
        String filePath = uploadPath + DateUtils.getReqDate();

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;

        File dest = new File(filePath +"/"+ fileName);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResult.FILE_FAIL);
        }
    }

    public void read(String url,HttpServletResponse response) {
        // 这里的url，我为了测试，直接就写静态的。
//        url = "E:\\ue\\upload\\image\\20190911\\1568171904349024879.png";
        File file = new File(url);
        // 后缀名
        String suffixName = url.substring(url.lastIndexOf("."));
        //判断文件是否存在如果不存在就返回默认图片或者进行异常处理
        if (!(file.exists() && file.canRead())) {
//            file = new File("xxx/xxx.jpg");
            System.out.println("文件不存在");
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.close();
            response.setContentType("image/png;charset=utf-8");
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

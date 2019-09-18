package com.sw.ueditor.upload;

import com.sw.file.FileUploadConfig;
import com.sw.ueditor.PathFormat;
import com.sw.ueditor.define.AppInfo;
import com.sw.ueditor.define.BaseState;
import com.sw.ueditor.define.FileType;
import com.sw.ueditor.define.State;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class BinaryUploader {
    static Logger logger = LoggerFactory.getLogger(BinaryUploader.class);

    public static final State save(HttpServletRequest request, Map<String, Object> conf, FileUploadConfig fileUploadConfig) {

        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if (isAjaxUpload) {
            upload.setHeaderEncoding("UTF-8");
        }

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("upfile");

            String savePath = (String) conf.get("savePath");                         ///upload/image/{yyyy}{mm}{dd}/{time}{rand:6}
            String localSavePathPrefix = (String) conf.get("savePathPrefix");     //E:/ue
            String originFileName = file.getOriginalFilename();                         //文件名
            String suffix = FileType.getSuffixByFilename(originFileName);         //文件后缀

            originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }
            savePath = PathFormat.parse(savePath, originFileName);   ///upload/image/20190917/1568691095958018901.png
            localSavePathPrefix = localSavePathPrefix + savePath;
            String physicalPath = localSavePathPrefix;  //E:/ue/upload/image/20190917/1568691338929046192.png
            logger.info("BinaryUploader physicalPath:{},savePath:{}", localSavePathPrefix, savePath);
            InputStream is = file.getInputStream();

            //在此处调用ftp的上传图片的方法将图片上传到文件服务器    TODO  是否要换成上传文件接口?
            String path = physicalPath.substring(0, physicalPath.lastIndexOf("/"));  //E:/ue/upload/image/20190917
            String picName = physicalPath.substring(physicalPath.lastIndexOf("/") + 1, physicalPath.length());   //1568691095958018901.png
            State storageState = StorageManager.saveFileByInputStream(request, is, path, picName, maxSize);

            is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
                storageState.putInfo("url", fileUploadConfig.getReadPathPrefix() + savePath);
            }

            return storageState;
        } catch (Exception e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        }
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}

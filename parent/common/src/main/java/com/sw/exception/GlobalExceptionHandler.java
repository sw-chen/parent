package com.sw.exception;

import com.sw.common.BaseException;
import com.sw.common.BaseResponse;
import com.sw.common.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller异常统一处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 统一处理业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public @ResponseBody
    BaseResponse<String> handle(BaseException ex) {
        BaseResponse<String> rep = new BaseResponse<>(ex.getErrCode(), ex.getMessage());
        LOG.debug(ex.toString());
        return rep;
    }

//
//
//    /**
//     * 自定义异常类
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler
//    public @ResponseBody
//    BaseResponse<String> handle(AccessTokenException ex) {
//        BaseResponse<String> rep = new BaseResponse<String>();
//        LOG.debug(ex.toString());
//        rep.setError(ex.getErrorNum(), ex.getMessage());
//        return rep;
//    }
//
//    @ExceptionHandler
//    public @ResponseBody
//    BaseResponse<String> handle(HttpServletRequest req, TypeMismatchException ex) {
//        BaseResponse<String> rep = new BaseResponse<String>();
//        LOG.error("请求[{}]传送参数有误：{}", req.getRequestURI(), ex.getMessage());
//        rep.setError(ErrorNum.PRA_ERR.index, "传送的参数有误，请检查！");
//        return rep;
//    }
//
//    @ExceptionHandler
//    public @ResponseBody
//    BaseResponse<String> handle(JsonMappingException ex) {
//        BaseResponse<String> rep = new BaseResponse<String>();
//        LOG.error("传送的JSON格式有误，请检查！", ex);
//        rep.setError(ErrorNum.PRA_ERR.index, "传送的JSON格式有误，请检查！");
//        return rep;
//    }
//
//    @ExceptionHandler
//    public @ResponseBody
//    BaseResponse<String> handle(HttpRequestMethodNotSupportedException ex) {
//        BaseResponse<String> rep = new BaseResponse<String>();
//        LOG.debug(ex.toString());
//        rep.setError(ErrorNum.PRA_ERR.index, ex.getMessage());
//
//        return rep;
//    }
//
//    @ExceptionHandler
//    public @ResponseBody
//    BaseResponse<String> handle(MissingServletRequestParameterException ex) {
//        BaseResponse<String> rep = new BaseResponse<String>();
//        LOG.debug(ex.toString());
//        rep.setError(ErrorNum.PRA_ERR.index, ex.getMessage());
//        return rep;
//    }

    @ExceptionHandler
    public @ResponseBody
    BaseResponse<String> handleAllException(Exception ex) {
        ex.printStackTrace();
        BaseResponse<String> rep = new BaseResponse<>(BaseResult.FAIL.code, BaseResult.FAIL.message);
        LOG.error("系统错误:", ex);
        rep.setData(ex.getMessage());
        return rep;
    }
}

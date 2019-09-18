package com.sw.controller;


import com.sw.common.BaseException;
import com.sw.common.BaseResponse;
import com.sw.common.BaseResult;
import com.sw.entity.User;
import com.sw.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenshiwan
 * @since 2019-07-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping(value = "/getList")
    @ResponseBody
    public BaseResponse<User> getList(){
        List<User> list = userService.list();
//        throw new BaseException(BaseResult.FAIL);
        return BaseResponse.build(list);
    }
}

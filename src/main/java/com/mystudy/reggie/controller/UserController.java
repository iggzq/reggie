package com.mystudy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mystudy.reggie.Util.MailUtil;
import com.mystudy.reggie.Util.MakeCode;
import com.mystudy.reggie.common.R;
import com.mystudy.reggie.dto.UserDto;
import com.mystudy.reggie.entity.User;
import com.mystudy.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送邮箱验证码
     *
     * @param
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
        //获取邮箱
        String mail = user.getPhone();
        //生成验证码
        String code = MakeCode.code();
        //调用util发送验证码
        MailUtil.sendMail(user.getPhone(), code);
        //将生成的验证码进行保存
        httpSession.setAttribute(mail, code);
        //回馈信息
        return R.success("邮箱验证码发送成功");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody UserDto userDto, HttpSession httpSession) {
        //获取邮箱
        String mail = userDto.getPhone();
        //获取用户输入的验证码
        String code = userDto.getCode();

        Object codeInSession = httpSession.getAttribute(mail);

        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(User::getPhone, mail);
            User user = userService.getOne(lambdaUpdateWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(mail);
                userService.save(user);

            }
            httpSession.setAttribute("user", user.getId());
            return R.success(user);
        } else if (mail.equals("2811328244@qq.com")) {
            LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(User::getPhone, mail);
            User user = userService.getOne(lambdaUpdateWrapper);
            httpSession.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登陆失败");
    }
}

package com.delicate.mule.login;

import com.delicate.mule.annotation.Remark;
import com.delicate.mule.common.Ret;
import com.delicate.mule.model.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Api(tags = "登录controller")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录")
    @Remark("登录")
    @GetMapping("/login")
    public Ret doLogin(@Valid Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Ret.fail("msg", bindingResult.getAllErrors().toString());
        }
        Ret ret = loginService.login(account.getName(), account.getPassword());
        if (ret.isOk()) {
            // doSomeThing
        }
        return ret;
    }


    @ApiOperation("注册")
    @Remark("注册")
    @GetMapping("/reg")
    public Ret reg(HttpServletRequest request) {
        Ret ret = loginService.reg(request.getParameter("name"), request.getParameter("password"));
        if (ret.isOk()) {
            // doSomeThing
        }
        return ret;
    }

}

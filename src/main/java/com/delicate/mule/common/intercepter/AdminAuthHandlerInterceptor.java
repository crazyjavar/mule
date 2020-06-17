package com.delicate.mule.common.intercepter;

import com.delicate.mule.accountrole.AccountRoleService;
import com.delicate.mule.auth.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminAuthService authService;

    @Autowired
    private AccountRoleService accountRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("i am AdminAuthHandlerInterceptor preHandle");
        Object id = request.getAttribute("account_id");
        if (id != null) {
            String requestURI = request.getRequestURI();
            System.out.println("请求路径是 " + requestURI);
            Long account_id = (Long) id;
            // 如果是超级管理员或者拥有对当前 action 的访问权限则放行
            if (accountRoleService.isSuperAdmin(account_id) ||
                    authService.hasPermission(account_id, request.getRequestURI())) {
                return true;
            }
        }
//        throw new HasNoPermissionRuntimeException(403,"权限不足");
        // TODO: response返回错误信息
        response.sendError(403, "权限不足");
        return false;
    }

}

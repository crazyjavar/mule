package com.delicate.mule.permission;

import cn.hutool.core.util.StrUtil;
import com.delicate.mule.annotation.Remark;
import com.delicate.mule.common.Ret;
import com.delicate.mule.dao.PermissionRepository;
import com.delicate.mule.dao.RolePermissionRepository;
import com.delicate.mule.model.Permission;
import com.github.xiaoymin.knife4j.spring.web.Knife4jController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.swagger.web.ApiResourceController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    private Set<String> excludedMethodNames = buildExcludedMethodName();

    /**
     * 排除 BasicErrorController,ApiResourceController,Knife4jController
     * 暂时先写死，有时间修改
     *
     * @return
     */
    private Set<String> buildExcludedMethodName() {
        Set<String> excludedMethodName = new HashSet<String>();
        excludedMethodName.add(BasicErrorController.class.getName());
        excludedMethodName.add(ApiResourceController.class.getName());
        excludedMethodName.add(Knife4jController.class.getName());
        return excludedMethodName;
    }

    /**
     * 按照controller的顺序查询,返回的权限列表
     *
     * @return
     */
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll(Sort.by("controller"));
    }

    /**
     * 同步 permission
     * 获取后台管理所有 mapping 以及 controller，将数据自动写入 permission 表
     * 随着开发过程的前行，可以动态进行同步添加新的 permission 数据
     * <p>
     * 如果更改了方法映射路径，会直接添加进去，而旧的映射通过markRemovedMapping()方法进行标记，
     * 会给过期的permission设置一个删除标识，便于前段处理
     */
    public Ret sync() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        int counter = 0;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : handlerMethods.entrySet()) {
            Method method = m.getValue().getMethod();
            // 获取Controller#name
            String controllerName = method.getDeclaringClass().getName();

            if (excludedMethodNames.contains(controllerName)) {
                continue;
            }
            RequestMappingInfo info = m.getKey();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String mapping : p.getPatterns()) {
                //1. 查数据看是否有该条记录
                Permission permission = permissionRepository.findByControllerAndAndMapping(controllerName, mapping);
                if (permission == null) {
                    permission = new Permission();
                    setRemarkValue(permission, method);
                    permission.setController(controllerName);
                    permission.setMapping(mapping);
                    permissionRepository.save(permission);
                    counter++;
                } else {
                    // 2.如果有对应数据，则查看Remark字段，为空就设置@Remark里面的值
                    if (StrUtil.isBlank(permission.getRemark())) {
                        setRemarkValue(permission, method);
                        if (permissionRepository.save(permission) != null) {
                            counter++;
                        }
                    }
                }
            }
        }
        if (counter == 0) {
            return Ret.ok("msg", "权限已经是最新状态，无需更新");
        } else {
            return Ret.ok("msg", "权限更新成功，共更新权限数 : " + counter);
        }
    }

    /**
     * 在已被移除的 permission 中 put 进去一个 removed 值为 true 的标记，便于在界面显示不同的样式
     *
     * @return 存在被删除的 mapping 时返回 true，否则返回 false
     */
    public boolean markRemovedMapping(Page<Permission> permissionPage, HttpServletRequest request) {
        boolean ret = false;

        /**
         * 获取MVC中所有的url，如果有疑惑，请参考我的博客介绍RequestMapping章节
         * https://juejin.im/post/5edbb27cf265da76b67bfb35
         */
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = handlerMethods.keySet();
        Set<String> mappings = requestMappingInfos.stream()
                .map(RequestMappingInfo::getPatternsCondition)
                .map(PatternsRequestCondition::getPatterns)
                .flatMap(x -> x.stream())
                .collect(Collectors.toSet());

//        Set<String> mappings = new HashSet<>();
//        for (RequestMappingInfo info : requestMappingInfos) {
//            PatternsRequestCondition p = info.getPatternsCondition();
//            for (String mapping : p.getPatterns()) {
//                mappings.add(mapping);
//            }
//        }
        for (Permission p : permissionPage.getContent()) {
            String mapping = p.getMapping();
            if (!mappings.contains(mapping)) {
                p.setRemove(true);
                ret = true;
            }
        }

        return ret;
    }

    private void setRemarkValue(Permission permission, Method method) {
        Remark remark = method.getAnnotation(Remark.class);
        if (remark != null && StrUtil.isNotBlank((remark.value()))) {
            permission.setRemark(remark.value());
        }
    }

    public Ret save(Permission permission) {
        permissionRepository.save(permission);
        return Ret.ok("msg", "更新成功");
    }

    public Permission findById(Long id) {
        return permissionRepository.getOne(id);
    }

    /**
     * 删除权限表中数据，因为角色权限表通过permissionId与权限表关联，所以需要一起删掉
     *
     * @param permissionId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Ret delete(Long permissionId) {
        permissionRepository.deleteById(permissionId);
        rolePermissionRepository.deleteByPermissionId(permissionId);
        return Ret.ok("msg", "权限删除成功");
    }

    public Page<Permission> paginate(Integer page, Integer size) {
        Page<Permission> permissions = permissionRepository.findAll(PageRequest.of(page, size, Sort.by("mapping")));
        return permissions;
    }
}

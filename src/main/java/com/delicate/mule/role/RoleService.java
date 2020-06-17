package com.delicate.mule.role;

import com.delicate.mule.common.Ret;
import com.delicate.mule.dao.AccountRoleRepository;
import com.delicate.mule.dao.RolePermissionRepository;
import com.delicate.mule.dao.RoleRepository;
import com.delicate.mule.model.Permission;
import com.delicate.mule.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


@Service
public class RoleService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    public Page<Role> paginate(Integer page, Integer size) {
        Page<Role> roles = roleRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
        return roles;
    }

    /**
     * 角色创建的时候是没有传id的，只有更新的时候会传
     * 保存与更新方法可以合并在一起
     *
     * @param role
     * @return
     */
    public Ret saveOrUpdate(Role role) {
        if (exists(role.getName())) {
            return Ret.fail("msg", "角色名称已经存在，请输入别的名称");
        }
        if (role.getId() == null) {
            role.setCreateAt(new Date());
            roleRepository.save(role);
            return Ret.ok("msg", "创建成功");
        } else {
            roleRepository.save(role);
            return Ret.ok("msg", "修改成功");
        }
    }

    /**
     * 判断角色名字是否存在
     *
     * @param name
     * @return
     */
    public boolean exists(String name) {
        name = name.toLowerCase().trim();
        Role role = roleRepository.findByName(name);
        return role != null;
    }

    /**
     * 根据传进来的id删除表role，role_permission,account_role对应的内容
     * 跟roleId关联的数据都需要删除
     *
     * @param roleId 角色id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Ret deleteById(Long roleId) {
        roleRepository.deleteById(roleId);
        rolePermissionRepository.deleteById(roleId);
        accountRoleRepository.deleteByRoleId(roleId);
        return Ret.ok("msg", "删除成功");
    }

    public Role findById(Long id) {
        return roleRepository.getOne(id);
    }

    /**
     * 根据 controller 将 permission列表进行分组
     */
    public LinkedHashMap<String, List<Permission>> groupByController(List<Permission> permissionList) {
        LinkedHashMap<String, List<Permission>> ret =
                permissionList.stream().
                        collect(groupingBy(Permission::getController, LinkedHashMap::new, toList()));
        return ret;
    }


    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles;
    }
}

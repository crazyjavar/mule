package com.delicate.mule.dao;

import com.delicate.mule.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByControllerAndAndMapping(String controller, String mapping);
}

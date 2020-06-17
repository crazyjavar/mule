package com.delicate.mule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户角色表
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AccountRole {

    @Id
    private Long accountId;

    private Long roleId;
}

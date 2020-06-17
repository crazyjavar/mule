package com.delicate.mule.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限表
 */
@NoArgsConstructor
@Data
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mapping;
    private String controller;

    private String remark;

    /**
     * 标识这个Permission是否过期，是否应该删除
     */
    @Transient
    private Boolean remove;

    /**
     * Attributes of this model
     */
    @Transient
    private Map<String, Object> attrs = new HashMap<>(4);

    public Permission put(String key, Object value) {
        attrs.put(key, value);
        return this;
    }
}

package com.kazma233.blog.entity.permission.vo;

import com.kazma233.blog.entity.common.vo.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionQuery extends Query {

    private String permissionName;

    private String permissionDescription;

}
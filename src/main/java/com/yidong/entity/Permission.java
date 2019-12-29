package com.yidong.entity;

import lombok.Data;

@Data
public class Permission {
    private int permissionId;
    private String permissionName;
    //父菜单id
    private  Integer parentId;
    //父菜单名称
    private String parentName;
    private String url;
    private int level;
}

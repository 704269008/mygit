package com.jns.entity;

public class Permission {
    private long resourceId;
    //资源名称
    private String resourceName;
    //资源类型
    private String resourceType;
    //访问url路径
    private String resourceUrl;
    //权限代码字符串
    private String resourcePercode;
    //父节点id
    private long resourceParentid;

    private char available;

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourcePercode() {
        return resourcePercode;
    }

    public void setResourcePercode(String resourcePercode) {
        this.resourcePercode = resourcePercode;
    }

    public long getResourceParentid() {
        return resourceParentid;
    }

    public void setResourceParentid(long resourceParentid) {
        this.resourceParentid = resourceParentid;
    }


    @Override
    public String toString() {
        return "permission["+resourcePercode+","+resourceUrl+"]";
    }
}

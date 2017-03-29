package com.jaya.hackthaonproject;


public class NotRamuRes {



    private String Resource_Type;



    private String resource_id;



    public NotRamuRes(String Resource_Type, String resource_id)
    {
        this.Resource_Type=Resource_Type;
        this.resource_id=resource_id;

    }
    public String getResource_Type() {
        return Resource_Type;
    }

    public void setResource_Type(String resource_Type) {
        Resource_Type = resource_Type;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }



}

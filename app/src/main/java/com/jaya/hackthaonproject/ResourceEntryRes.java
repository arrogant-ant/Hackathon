package com.jaya.hackthaonproject;

/**
 * Created by shubham on 3/25/2017.
 */
public class ResourceEntryRes {
    private String resource_id;

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    private String resource_type;


    public ResourceEntryRes(String resource_type, String resource_id ) {

        this.resource_type=resource_type;
        this.resource_id=resource_id;

    }


}

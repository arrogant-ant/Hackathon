package com.jaya.hackthaonproject;


public class Contacts_allocated{



    private String resource_type;
    private String no_of_resources;



    public Contacts_allocated(String resource_type, String no_of_resources)
    {
        this.resource_type=resource_type;
        this.no_of_resources=no_of_resources;

    }
    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getNo_of_resources() {
        return no_of_resources;
    }

    public void setNo_of_resources(String no_of_resources) {
        this.no_of_resources = no_of_resources;
    }

}

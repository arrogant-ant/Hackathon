package com.jaya.hackthaonproject;


public class AllocatedRes {



    private String Resource_Type;



    private String No_Of_Resources;



    public AllocatedRes(String Resource_Type, String No_Of_Resources)
    {
        this.Resource_Type=Resource_Type;
        this.No_Of_Resources=No_Of_Resources;

    }
    public String getNo_Of_Resources() {
        return No_Of_Resources;
    }

    public void setNo_Of_Resources(String no_Of_Resources) {
        No_Of_Resources = no_Of_Resources;
    }

    public String getResource_Type() {
        return Resource_Type;
    }

    public void setResource_Type(String resource_Type) {
        Resource_Type = resource_Type;
    }


}

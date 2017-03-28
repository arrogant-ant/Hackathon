package com.jaya.hackthaonproject;


public class TrackRes {



    private String Resource_id;



    private String Resource_type;
    private String location_id;
    private String Status;

    public TrackRes(String Resource_id, String Resource_type, String location_id, String Status)
    {
        this.Resource_id=Resource_id;
        this.Resource_type=Resource_type;
        this.location_id=location_id;
        this.Status=Status;

    }
    public String getResource_type() {
        return Resource_type;
    }

    public void setResource_type(String resource_type) {
        Resource_type = resource_type;
    }

    public String getResource_id() {
        return Resource_id;
    }

    public void setResource_id(String resource_id) {
        Resource_id = resource_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



}

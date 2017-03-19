package com.jaya.hackthaonproject;

/**
 * Created by shubham on 3/19/2017.
 */

public class Contacts_timeline{



        private String Demand_id;

    public String getDemand_id() {
        return Demand_id;
    }

    public void setDemand_id(String demand_id) {
        Demand_id = demand_id;
    }

    public String getResource_type() {
        return Resource_type;
    }

    public void setResource_type(String resource_type) {
        Resource_type = resource_type;
    }

    public String getNo_of_resources() {
        return No_of_resources;
    }

    public void setNo_of_resources(String no_of_resources) {
        No_of_resources = no_of_resources;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getModified_by() {
        return Modified_by;
    }

    public void setModified_by(String modified_by) {
        Modified_by = modified_by;
    }

    public String getModified_on() {
        return Modified_on;
    }

    public void setModified_on(String modified_on) {
        Modified_on = modified_on;
    }

    private String Resource_type;
        private String No_of_resources;
        private String Status;
    private String Modified_by;
    private String Modified_on;
        public Contacts_timeline(String Demand_id,String Resource_type,String No_of_resources,String Status,String Modified_by,String Modified_on)
        {
            this.Demand_id=Demand_id;
            this.Resource_type=Resource_type;
            this.No_of_resources=No_of_resources;
            this.Status=Status;
            this.Modified_by=Modified_by;
            this.Modified_on=Modified_on;

        }

}

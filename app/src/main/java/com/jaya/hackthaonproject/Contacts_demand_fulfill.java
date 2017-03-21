package com.jaya.hackthaonproject;


public class Contacts_demand_fulfill {


   private String Resource_Type;
    private String No_Of_Resources;
    private String Demand_Id;
     private String Date_Of_Demand;

    public Contacts_demand_fulfill(String Resource_Type, String No_Of_Resources, String Demand_Id, String Date_Of_Demand) {
        this.Resource_Type = Resource_Type;
        this.No_Of_Resources = No_Of_Resources;
        this.Demand_Id = Demand_Id;
        this.Date_Of_Demand = Date_Of_Demand;

    }
    public String getDemand_Id() {
        return Demand_Id;
    }

    public void setDemand_Id(String demand_Id) {
        Demand_Id = demand_Id;
    }

    public String getDate_Of_Demand() {
        return Date_Of_Demand;
    }

    public void setDate_Of_Demand(String date_Of_Demand) {
        Date_Of_Demand = date_Of_Demand;
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

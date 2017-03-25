package com.jaya.hackthaonproject;

/**
 * Created by Sabita_Sant on 25/03/17.
 */

public class GrantedRes {
    String demand_id, res_type, no, date;

    public GrantedRes(String demand_id, String res_type, String no, String date) {
        this.demand_id = demand_id;
        this.res_type = res_type;
        this.no = no;
        this.date = date;
    }

    public void setDemand_id(String demand_id) {
        this.demand_id = demand_id;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDemand_id() {
        return demand_id;
    }

    public String getRes_type() {
        return res_type;
    }

    public String getNo() {
        return no;
    }

    public String getDate() {
        return date;
    }
}

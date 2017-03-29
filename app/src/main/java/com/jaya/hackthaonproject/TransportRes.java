package com.jaya.hackthaonproject;

/**
 * Created by Sabita_Sant on 29/03/17.
 */

public class TransportRes {
    String demand_id, res_type, no, des_id;

    public TransportRes(String demand_id, String res_type, String no, String des_id) {
        this.demand_id = demand_id;
        this.res_type = res_type;
        this.no = no;
        this.des_id = des_id;
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

    public String getDes_id() {
        return des_id;
    }
}

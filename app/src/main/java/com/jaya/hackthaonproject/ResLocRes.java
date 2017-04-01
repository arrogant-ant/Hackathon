package com.jaya.hackthaonproject;

/**
 * Created by Sabita_Sant on 30/03/17.
 */

public class ResLocRes {
    String res_no,src_id,res_type;

    public ResLocRes(String res_no, String src_id, String res_type) {
        this.res_no = res_no;
        this.src_id = src_id;
        this.res_type = res_type;
    }

    public String getRes_no() {
        return res_no;
    }

    public String getSrc_id() {
        return src_id;
    }

    public String getRes_type() {
        return res_type;
    }
}

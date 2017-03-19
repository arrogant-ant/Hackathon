package com.jaya.hackthaonproject;


public class Resource {
    private String type;
    private String no;
    private String time;

    public Resource(String type, String no, String time) {

        this.type = type;
        this.no = no;
        this.time = time;
    }
    public String getType() {
        return type;
    }

    public String getNo() {
        return no;
    }

    public String getTime() {
        return time;
    }


}



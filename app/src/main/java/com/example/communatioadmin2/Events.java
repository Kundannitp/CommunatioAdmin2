package com.example.communatioadmin2;

public class Events {
    String eventtype,eventname,fee,lastdate,address,dateofevent,uid,collegename,information,UPIid,PIname;

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public Events(String eventtype, String eventname, String fee, String lastdate, String address, String dateofevent, String uid, String collegename, String information, String UPIid, String PIname) {
        this.eventtype = eventtype;
        this.eventname = eventname;
        this.fee = fee;
        this.lastdate = lastdate;
        this.address = address;
        this.dateofevent = dateofevent;
        this.uid = uid;
        this.collegename = collegename;
        this.information = information;
        this.UPIid = UPIid;
        this.PIname = PIname;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateofevent() {
        return dateofevent;
    }

    public void setDateofevent(String dateofevent) {
        this.dateofevent = dateofevent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

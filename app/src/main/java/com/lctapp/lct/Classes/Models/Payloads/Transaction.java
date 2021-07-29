package com.lctapp.lct.Classes.Models.Payloads;

public class Transaction {
    private String memberNo;
    private String fromDate;
    private String toDate;

    public Transaction(String memberNo, String fromDate, String toDate) {
        this.memberNo = memberNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}

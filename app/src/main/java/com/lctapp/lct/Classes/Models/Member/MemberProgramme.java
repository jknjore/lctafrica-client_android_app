package com.lctapp.lct.Classes.Models.Member;

import java.util.List;

public class MemberProgramme {
    public String programmeCode;
    public int programmeId;
    public String programmeName;
    public List<Voucher> vouchers;
    public int schemeId;
    public boolean isActive;
    public Object insuranceCode;
    public Object schemeCode;

    public String getProgrammeCode() {
        return programmeCode;
    }

    public int getProgrammeId() {
        return programmeId;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public boolean isActive() {
        return isActive;
    }

    public Object getInsuranceCode() {
        return insuranceCode;
    }

    public Object getSchemeCode() {
        return schemeCode;
    }
}

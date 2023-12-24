package com.example.bookmart.project.Request;

import java.util.Date;

public class AdminUpdateCouponRequest {
    private String code;

    private Date startdate;

    private Date exprDate;

    private Integer discountRate;
    public AdminUpdateCouponRequest()
    {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getExprDate() {
        return exprDate;
    }

    public void setExprDate(Date exprDate) {
        this.exprDate = exprDate;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }
}

package com.example.bookmart.project.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Cupons {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;


    @Column(name = "cupon_code")
    private String code;

    @Column(name="valid_from")

    private Date valid_fromDate;

    @Column(name = "valid_upto")

    private Date valid_uptoDate;

    @Column(name = "discount")
    private Integer discount;
    @Column(name = "Is_active")

    private Boolean is_activeState;

    public Cupons()
    {

    }

    public Cupons(Long id, String code, Date valid_fromDate, Date valid_uptoDate, Integer discount, Boolean is_activeState) {
        this.id = id;
        this.code = code;
        this.valid_fromDate = valid_fromDate;
        this.valid_uptoDate = valid_uptoDate;
        this.discount = discount;
        this.is_activeState = is_activeState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getValid_fromDate() {
        return valid_fromDate;
    }

    public void setValid_fromDate(Date valid_fromDate) {
        this.valid_fromDate = valid_fromDate;
    }

    public Date getValid_uptoDate() {
        return valid_uptoDate;
    }

    public void setValid_uptoDate(Date valid_uptoDate) {
        this.valid_uptoDate = valid_uptoDate;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getIs_activeState() {
        return is_activeState;
    }

    public void setIs_activeState(Boolean is_activeState) {
        this.is_activeState = is_activeState;
    }
}

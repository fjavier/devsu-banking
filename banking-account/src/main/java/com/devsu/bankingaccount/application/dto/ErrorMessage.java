package com.devsu.bankingaccount.application.dto;

import java.util.Date;
import java.util.List;

public class ErrorMessage {
    private Date date;
    private List<ErrorDetail> errorDetail;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public void setErrorDetail(List<ErrorDetail> errorDetail) {
        this.errorDetail = errorDetail;
    }

    public List<ErrorDetail> getErrorDetail() {
        return errorDetail;
    }
}

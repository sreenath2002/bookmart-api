package com.example.bookmart.project.Response;

public class CommonResponse<T> {

    private String statuscode;

    private T result;

    private String message;

    public CommonResponse(String statuscode, T result, String message) {
        this.statuscode = statuscode;
        this.result = result;
        this.message = message;
    }

    public CommonResponse(){

    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

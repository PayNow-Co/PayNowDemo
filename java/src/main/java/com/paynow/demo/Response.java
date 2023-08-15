package com.paynow.demo;

public class Response {
    private long status;
    private String type;
    private String message;
    private Result result;
    private String requestID;

    public long getStatus() { return status; }
    public void setStatus(long value) { this.status = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public Result getResult() { return result; }
    public void setResult(Result value) { this.result = value; }

    public String getRequestID() { return requestID; }
    public void setRequestID(String value) { this.requestID = value; }
}

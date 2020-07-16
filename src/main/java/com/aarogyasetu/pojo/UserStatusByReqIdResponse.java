package com.aarogyasetu.pojo;

public class UserStatusByReqIdResponse {

    private String requestId;
    private String trace_id;
    private String request_status;
    private String as_status;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getAs_status() {
        return as_status;
    }

    public void setAs_status(String as_status) {
        this.as_status = as_status;
    }
}

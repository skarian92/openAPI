package com.aarogyasetu.pojo;

import java.util.UUID;

public class UserStatusRequest {
    private String phone_number;
    private String trace_id;
    private String reason;

    public UserStatusRequest(String phone_number){
        this.phone_number = phone_number;
        this.trace_id = UUID.randomUUID().toString();
        this.reason = phone_number + "  " + trace_id;
    }
    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

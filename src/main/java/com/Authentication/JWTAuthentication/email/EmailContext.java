package com.Authentication.JWTAuthentication.email;

import java.util.HashMap;
import java.util.Map;

public class EmailContext
{
    private String to;
    private String from;
    private String subject;
    private final Map<String, String> context = new HashMap<>();

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void addContextVariable(String key, String value) {
        this.context.put(key, value);
    }
}

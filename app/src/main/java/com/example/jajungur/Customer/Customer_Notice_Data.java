package com.example.jajungur.Customer;

import java.util.Date;

public class Customer_Notice_Data {

    String notice;
    String content;
    Date createdAt;

    public Customer_Notice_Data(String notice, String content, Date createdAt) {
        this.notice = notice;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String name) {
        this.content = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}

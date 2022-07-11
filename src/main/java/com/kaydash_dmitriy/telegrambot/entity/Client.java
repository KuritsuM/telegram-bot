package com.kaydash_dmitriy.telegrambot.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255, nullable = false)
    private String fullName;

    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Column(length = 400, nullable = false)
    private String address;

    @Column(nullable = false)
    private Long externalId;

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

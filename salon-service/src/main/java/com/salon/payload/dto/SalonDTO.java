package com.salon.payload.dto;

import lombok.Data;

import java.util.List;
@Data
public class SalonDTO {

    private Long id;

    private  String name;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private Long ownerId;

    private String openTime;

    private String closeTime;
}

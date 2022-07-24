package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TUserDto implements Serializable {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean gender;
}

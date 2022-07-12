package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;

@Data

public class UserDto implements Serializable {
    private final String userName;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Boolean gender;
}

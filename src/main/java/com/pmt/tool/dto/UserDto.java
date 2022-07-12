package com.pmt.tool.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean gender;
}

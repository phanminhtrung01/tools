package com.pmt.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean gender;
}

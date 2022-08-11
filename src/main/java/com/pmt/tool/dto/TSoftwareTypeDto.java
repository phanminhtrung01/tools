package com.pmt.tool.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TSoftwareTypeDto implements Serializable {
    private String extensionType;
    private String description;
}

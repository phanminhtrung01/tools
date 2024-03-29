package com.pmt.tool.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseObject {
    private final Integer status;
    private final String message;
    private final Object data;
}

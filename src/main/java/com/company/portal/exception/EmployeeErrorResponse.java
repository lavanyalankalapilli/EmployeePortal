package com.company.portal.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeErrorResponse {

    private int status;
    private String message;

    public EmployeeErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }


}

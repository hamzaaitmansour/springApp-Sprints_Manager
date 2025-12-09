package com.genieLogiciele.sprint.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SharedError {
    private String message;
    private Date timestamp;
    private int code;
}
package com.example.pointofsalebyDulan.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardResponse {

    private int code;
    private String massege;
    private Object data;
}
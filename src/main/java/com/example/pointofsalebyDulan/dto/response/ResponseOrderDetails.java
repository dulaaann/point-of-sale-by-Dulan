package com.example.pointofsalebyDulan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseOrderDetails {

    //customer
    private String customerName;
    private String customerAddress;
    private ArrayList contactNumbers;

    //order
    private Date date;
    private double total;
}
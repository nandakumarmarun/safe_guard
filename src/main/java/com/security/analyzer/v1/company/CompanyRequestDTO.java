package com.security.analyzer.v1.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDTO implements Serializable {

    private String companyName;

    private String address;

    private String contact;

}

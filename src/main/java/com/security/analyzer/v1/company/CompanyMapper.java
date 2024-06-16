package com.security.analyzer.v1.company;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyMapper {

    public Company CompanyRequestDTOtoCompany(CompanyRequestDTO companyRequestDTO){
        Company company = new Company();
        company.setCompanyName(companyRequestDTO.getCompanyName());
        company.setAddress(companyRequestDTO.getAddress());
        company.setContact(companyRequestDTO.getContact());
        return company;
    }

    public Company CompanyUpdateDTOtoCompany(CompanyUpdateDTO companyUpdateDTO){
        Company company = new Company();
        company.setId(companyUpdateDTO.getId());
        company.setCompanyName(companyUpdateDTO.getCompanyName());
        company.setAddress(companyUpdateDTO.getAddress());
        company.setContact(companyUpdateDTO.getContact());
        return company;
    }

    public CompanyReponseDTO CompanytoCompanyReponseDTO(Company company){
        CompanyReponseDTO companyReponseDTO = new CompanyReponseDTO();
        companyReponseDTO.setId(company.getId());
        companyReponseDTO.setCompanyName(company.getCompanyName());
        companyReponseDTO.setAddress(company.getAddress());
        companyReponseDTO.setContact(company.getContact());
        return companyReponseDTO;
    }


    public List<CompanyReponseDTO> CompanyListoCompanyReponseDTOList( List<Company> companyList){
        List<CompanyReponseDTO> companyReponseDTOs = new ArrayList<>();
        companyList.forEach(data-> {
            companyReponseDTOs.add(CompanytoCompanyReponseDTO(data)) ;
        });
        return companyReponseDTOs;
    }


}

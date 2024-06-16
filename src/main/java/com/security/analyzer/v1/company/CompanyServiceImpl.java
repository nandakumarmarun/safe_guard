package com.security.analyzer.v1.company;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.company.Company}
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;


    @Override
    public CompanyReponseDTO save(CompanyRequestDTO companyRequestDTO) {
        log.debug("Request to save Company : {}", companyRequestDTO);
        Company company = companyMapper.CompanyRequestDTOtoCompany(companyRequestDTO);
        Company reponse = companyRepository.save(company);
        CompanyReponseDTO companyReponseDTO = companyMapper.CompanytoCompanyReponseDTO(reponse);
        return companyReponseDTO;
    }

    @Override
    public CompanyReponseDTO update(CompanyUpdateDTO companyUpdateDTO) {
        log.debug("Request to update Company : {}", companyUpdateDTO);
        Company company = companyMapper.CompanyUpdateDTOtoCompany(companyUpdateDTO);
        Company reponse = companyRepository.save(company);
        CompanyReponseDTO companyReponseDTO = companyMapper.CompanytoCompanyReponseDTO(reponse);
        return companyReponseDTO;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CompanyReponseDTO> findAll() {
        log.debug("Request to get all Companies");
        List<Company> companyList = companyRepository.findAll();
        List<CompanyReponseDTO> companyReponseDTOList =
            companyMapper.CompanyListoCompanyReponseDTOList(companyList);
        return companyReponseDTOList;
    }

    public Page<Company> findAllWithEagerRelationships(Pageable pageable) {
        return companyRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}

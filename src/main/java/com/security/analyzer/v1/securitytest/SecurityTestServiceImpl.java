package com.security.analyzer.v1.securitytest;

import com.security.analyzer.v1.Enum.PriorityLevel;
import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.checklist.CheckList;
import com.security.analyzer.v1.checklist.CheckListRepository;
import com.security.analyzer.v1.checklistItem.CheckListItem;
import com.security.analyzer.v1.checklistItem.CheckListItemRepository;
import com.security.analyzer.v1.company.Company;
import com.security.analyzer.v1.company.CompanyRepository;
import com.security.analyzer.v1.config.utils.SecurityUtils;
import com.security.analyzer.v1.testchecklist.TestCheckList;
import com.security.analyzer.v1.testchecklist.TestCheckListDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListUpdateDTO;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItem;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemDTO;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemUpdateDTO;
import com.security.analyzer.v1.user.User;
import com.security.analyzer.v1.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Interface for managing {@link com.security.analyzer.v1.securitytest.SecurityTest}.
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class SecurityTestServiceImpl implements SecurityTestService {

    private final Logger log = LoggerFactory.getLogger(SecurityTestServiceImpl.class);

    private final SecurityTestRepository securityTestRepository;

    private final CheckListRepository checkListRepository;

    private final CheckListItemRepository checkListItemRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;


    @Override
    public SecurityTestResponseDTO  save(SecurityTestDTO securityTestDTO) {
        log.debug("Request to save SecurityTest : {}", securityTestDTO);
        List<CheckListItem> checkListItems = checkListItemRepository.findAll();
        List<CheckList> checkListLists = checkListRepository.findAll();
        Optional<Company> optionalCompany = companyRepository.findById(securityTestDTO.getCompanyId());

        Optional<User> optionalUser = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin().get());

        double testScore = 0;
        int UnmarkedHighPriorityCount = 0;
        StringBuilder sb = new StringBuilder("");

        SecurityTest securityTest = new  SecurityTest();
        Set<TestCheckList> testCheckLists = new HashSet<>();
        securityTest.setApplicationName(securityTestDTO.getApplicationName());
        securityTest.setSystemNo(securityTestDTO.getSystemNo());
        securityTest.company(optionalCompany.isPresent() ? optionalCompany.get():null);
        securityTest.setApplicationUser(optionalUser.isPresent() ? optionalUser.get() : null);
        securityTest.setTestStatus("PROCESSING");


        for(TestCheckListDTO testCheckListDTO : securityTestDTO.getTestCheckLists()){
            List<TestCheckListItem>  testCheckListItemDTOS  = new ArrayList<>();
            TestCheckList  testCheckList = new TestCheckList();

            Optional<CheckList> optionalCheckList = checkListLists
                .stream()
                .filter(data -> data.getId().equals(testCheckListDTO.getCheckListId()))
                .findAny();

             if(optionalCheckList.isPresent()){
                 testCheckList.setCheckList(optionalCheckList.get());


             for (TestCheckListItemDTO testCheckListItemDTO : testCheckListDTO.getTestCheckListItems()){
                 TestCheckListItem testCheckListItem = new TestCheckListItem();
                 Optional<CheckListItem> optionalCheckListItem = optionalCheckList.get().getCheckListItems()
                     .stream()
                     .filter(data -> data.getId().equals(testCheckListItemDTO.getChecklistitemId()))
                     .findAny();

                 if(optionalCheckListItem.isPresent()){
                     testCheckListItem.setChecklistitem(optionalCheckListItem.get());
                     testCheckListItem.setMarked(testCheckListItemDTO.getMarked());
                     testCheckListItemDTOS.add(testCheckListItem);
                     if(testCheckListItem.getMarked()){
                         testScore = testScore + optionalCheckListItem.get().getValue();
                     }
                     if(testCheckListItem.getMarked() == false
                         && optionalCheckListItem.get().getPriorityLevel().equals(PriorityLevel.HIGH)){
                         sb.append(optionalCheckListItem.get().getName()+",");
                         UnmarkedHighPriorityCount++;
                     }
                 }
             }
             testCheckList.setTestCheckListItems(testCheckListItemDTOS.stream().collect(Collectors.toSet()));
             testCheckLists.add(testCheckList);
             }
        }

        Double total = checkListItemRepository.findsumofValue();
        double percentage = (testScore/total)*100;

        securityTest.testScore(percentage);

        if(UnmarkedHighPriorityCount > 0){
            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
            securityTest.setTestDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
        } else if(percentage <= 20){
            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
            securityTest.setTestDescription("Your security is too low!" );
        } else if (percentage <80) {
            securityTest.setSecurityLevel(SecurityLevel.MODERATE);
            securityTest.setTestDescription("Your security settings are moderate." );
        } else{
            securityTest.setSecurityLevel(SecurityLevel.EXCELLENT);
            securityTest.setTestDescription("Your security settings are excellent!" );
        }
        securityTest.setTestCheckLists(testCheckLists);
        SecurityTest response = securityTestRepository.save(securityTest);
        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO(response);
        return securityTestResponseDTO;
    }

    @Override
    public SecurityTestResponseDTO update(SecurityTestUpdateDTO securityTestUpdateDTO) {
        log.debug("Request to save SecurityTest : {}", securityTestUpdateDTO);
        List<CheckListItem> checkListItems = checkListItemRepository.findAll();
        List<CheckList> checkListLists = checkListRepository.findAll();
        Optional<Company> optionalCompany = companyRepository.findById(securityTestUpdateDTO.getCompanyId());

        Optional<User> optionalUser = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin().get());

        double testScore = 0;
        int UnmarkedHighPriorityCount = 0;
        StringBuilder sb = new StringBuilder("");

        Optional<SecurityTest> securityTestById = securityTestRepository.findById(securityTestUpdateDTO.getId());

        SecurityTest securityTest = securityTestById.get();

        Set<TestCheckList> testCheckLists = new HashSet<>();
        securityTest.setId(securityTestUpdateDTO.getId());
        securityTest.setApplicationName(securityTestUpdateDTO.getApplicationName());
        securityTest.setSystemNo(securityTestUpdateDTO.getSystemNo());
        securityTest.company(optionalCompany.isPresent() ? optionalCompany.get():null);
        securityTest.setApplicationUser(optionalUser.isPresent() ? optionalUser.get() : null);
        securityTest.setTestStatus("PROCESSING");


        for(TestCheckListUpdateDTO testCheckListUpdateDTO : securityTestUpdateDTO.getTestCheckLists()){
            List<TestCheckListItem>  testCheckListItemDTOS  = new ArrayList<>();
            TestCheckList  testCheckList = new TestCheckList();

            Optional<CheckList> optionalCheckList = checkListLists
                .stream()
                .filter(data -> data.getId().equals(testCheckListUpdateDTO.getCheckListId()))
                .findAny();

            if(optionalCheckList.isPresent()){
                testCheckList.setId(testCheckListUpdateDTO.getId());
                testCheckList.setCheckList(optionalCheckList.get());


                for (TestCheckListItemUpdateDTO testCheckListItemUpdateDTO : testCheckListUpdateDTO.getTestCheckListItems()){
                    TestCheckListItem testCheckListItem = new TestCheckListItem();
                    Optional<CheckListItem> optionalCheckListItem = optionalCheckList.get().getCheckListItems()
                        .stream()
                        .filter(data -> data.getId().equals(testCheckListItemUpdateDTO.getChecklistitemId()))
                        .findAny();

                    if(optionalCheckListItem.isPresent()){
                        testCheckListItem.setId(testCheckListItemUpdateDTO.getId());
                        testCheckListItem.setChecklistitem(optionalCheckListItem.get());
                        testCheckListItem.setMarked(testCheckListItemUpdateDTO.getMarked());
                        testCheckListItemDTOS.add(testCheckListItem);
                        if(testCheckListItem.getMarked()){
                            testScore = testScore + optionalCheckListItem.get().getValue();
                        }
                        if(testCheckListItem.getMarked() == false
                            && optionalCheckListItem.get().getPriorityLevel().equals(PriorityLevel.HIGH)){
                            sb.append(optionalCheckListItem.get().getName()+",");
                            UnmarkedHighPriorityCount++;
                        }
                    }
                }
                testCheckList.setTestCheckListItems(testCheckListItemDTOS.stream().collect(Collectors.toSet()));
                testCheckLists.add(testCheckList);
            }
        }

        Double total = checkListItemRepository.findsumofValue();
        double percentage = (testScore/total)*100;

        securityTest.testScore(percentage);

        if(UnmarkedHighPriorityCount > 0){
            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
            securityTest.setTestDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
        } else if(percentage <= 20){
            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
            securityTest.setTestDescription("Your security is too low!" );
        } else if (percentage <80) {
            securityTest.setSecurityLevel(SecurityLevel.MODERATE);
            securityTest.setTestDescription("Your security settings are moderate." );
        } else{
            securityTest.setSecurityLevel(SecurityLevel.EXCELLENT);
            securityTest.setTestDescription("Your security settings are excellent!" );
        }
        securityTest.setTestCheckLists(testCheckLists);
        SecurityTest response = securityTestRepository.save(securityTest);
        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO(response);
        return securityTestResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecurityTest> findAll() {
        log.debug("Request to get all SecurityTest");
        return securityTestRepository.findAll();
    }

    public Page<SecurityTest> findAllWithEagerRelationships(Pageable pageable) {
        return securityTestRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SecurityTestResponseDTO findOne(Long id) {
        log.debug("Request to get SecurityTest : {}", id);
        Optional<SecurityTest> oneWithEagerRelationships =
            securityTestRepository.findOneWithEagerRelationships(id);
        if(oneWithEagerRelationships.isPresent()){
            SecurityTestResponseDTO securityTestResponseDTO =
                new SecurityTestResponseDTO(oneWithEagerRelationships.get());
            return securityTestResponseDTO;
        }


        return null;
    }

    @Override
    public void UpdateStatus(Long id) {
        Optional<SecurityTest> securityTestById = securityTestRepository.findById(id);
        securityTestById.ifPresent(data->{
            data.setTestStatus("COMPLETED");
            SecurityTest save = securityTestRepository.save(data);
        });
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SecurityTest : {}", id);
        securityTestRepository.deleteById(id);
    }
}

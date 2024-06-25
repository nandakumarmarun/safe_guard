package com.security.analyzer.v1.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.analyzer.v1.Enum.PriorityLevel;
import com.security.analyzer.v1.Enum.SecurityLevel;
import com.security.analyzer.v1.checklist.CheckList;
import com.security.analyzer.v1.checklist.CheckListRepository;
import com.security.analyzer.v1.checklistItem.CheckListItem;
import com.security.analyzer.v1.checklistItem.CheckListItemRepository;
import com.security.analyzer.v1.company.Company;
import com.security.analyzer.v1.company.CompanyRepository;
import com.security.analyzer.v1.config.utils.SecurityUtils;
import com.security.analyzer.v1.securitytest.*;
import com.security.analyzer.v1.securitytest.chart.ChartDTO;
import com.security.analyzer.v1.testchecklist.TestCheckList;
import com.security.analyzer.v1.testchecklist.TestCheckListDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListResponseDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListUpdateDTO;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItem;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemDTO;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemResoponseDTO;
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
 * Service Interface for managing {@link SecurityTest}.
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    private final SecurityTestMapper securityTestMapper;

    private final TestRepository testRepository;

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

        List<Test> tests  = new ArrayList<>();
        String TestID = UUID.randomUUID().toString();
        TestCheckList  testCheckList = new TestCheckList();

        for(TestCheckListDTO testCheckListDTO : securityTestDTO.getTestCheckLists()){
            Optional<CheckList> optionalCheckList = checkListLists
                .stream()
                .filter(data -> data.getId().equals(testCheckListDTO.getCheckListId()))
                .findAny();

            if(optionalCheckList.isPresent())
             {
                 for (TestCheckListItemDTO testCheckListItemDTO : testCheckListDTO.getTestCheckListItems()){

                     Optional<CheckListItem> optionalCheckListItem = optionalCheckList.get().getCheckListItems()
                             .stream()
                             .filter(data -> data.getId().equals(testCheckListItemDTO.getChecklistitemId()))
                             .findAny();

                     if(optionalCheckListItem.isPresent()){
                         CheckListItem checkListItem = optionalCheckListItem.get();
                         CheckList checkList = optionalCheckList.get();
                         Test test = new  Test();
                         test.setApplicationName(securityTestDTO.getApplicationName());
                         test.setTestID(TestID);
                         test.setSystemNo(securityTestDTO.getSystemNo());
                         test.setCompany(optionalCompany.isPresent() ? optionalCompany.get():null);
                         test.setApplicationUser(optionalUser.isPresent() ? optionalUser.get() : null);
                         test.setTestStatus("PROCESSING");
                         test.setCheckListId(checkList.getId());
                         test.setCheckListName(checkList.getName());
                         test.setChecklistitemId(checkListItem.getId());
                         test.setChecklistitemName(checkListItem.getName());
                         test.setMarked(testCheckListItemDTO.getMarked());
                         test.setValue(checkListItem.getValue());
                         tests.add(test);
                     }
                 }
             }
        }

        List<Test> tests1 = testRepository.saveAll(tests);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(tests1);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, List<Test>> testMap = tests1.parallelStream()
                .collect(Collectors.groupingBy(Test::getTestID));

        for (Map.Entry<String, List<Test>> entry : testMap.entrySet()) {
            String testID = entry.getKey();
            List<Test> testList = testMap.get(testID);

            Map<Long, List<Test>> checklistMap = testList.parallelStream()
                    .collect(Collectors.groupingBy(Test::getCheckListId));

            for (Map.Entry<Long, List<Test>> entry2 : checklistMap.entrySet()) {
                Long checklistID = entry2.getKey();
                List<Test> checkList  = checklistMap.get(checklistID);
                Map<Long, List<Test>> checklistitemMap = checkList.parallelStream()
                        .collect(Collectors.groupingBy(Test::getCheckListId));

                for (Map.Entry<Long, List<Test>> entry3 : checklistitemMap.entrySet()) {
                    Long checklistItemID  = entry3.getKey();
                    List<Test> checklistitems = checklistitemMap.get(checklistItemID);
                    for(Test test : checklistitems){
                        TestCheckListItemResoponseDTO testCheckListItemResoponseDTO = new TestCheckListItemResoponseDTO();
                        System.out.println(test.getCheckListId());
                    }
                }
            }
        }




            // Iterate over each checklist in the Test object

//        securityTest.testScore(percentage);
//
//        if(UnmarkedHighPriorityCount > 0){
//            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
//            securityTest.setTestDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
//        } else if(percentage <= 20){
//            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
//            securityTest.setTestDescription("Your security is too low!" );
//        } else if (percentage <80) {
//            securityTest.setSecurityLevel(SecurityLevel.MODERATE);
//            securityTest.setTestDescription("Your security settings are moderate." );
//        } else{
//            securityTest.setSecurityLevel(SecurityLevel.EXCELLENT);
//            securityTest.setTestDescription("Your security settings are excellent!" );
//        }
//        securityTest.setTestCheckLists(testCheckLists);
        SecurityTest response = null;
        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO(response);
        return securityTestResponseDTO;
    }

    @Override
    public SecurityTestResponseDTO update(SecurityTestUpdateDTO securityTestUpdateDTO) {
        return null;
    }

    @Override
    public List<SecurityTest> findAll() {
        return null;
    }

    @Override
    public List<SecurityTestResponseDTO> findAllTests() {
        return null;
    }

    @Override
    public long countBySecurityLevel(SecurityLevel securityLevel) {
        return 0;
    }

    @Override
    public ChartDTO getChart() {
        return null;
    }

    @Override
    public List<SecurityTestResponseDTO> findAllTestslimited() {
        return null;
    }

    @Override
    public Page<SecurityTest> findAllWithEagerRelationships(Pageable pageable) {
        return null;
    }

    @Override
    public SecurityTestResponseDTO findOne(Long id) {
        return null;
    }

    @Override
    public void UpdateStatus(Long id) {

    }

    @Override
    public void delete(Long id) {

    }

//    @Override
//    public SecurityTestResponseDTO update(SecurityTestUpdateDTO securityTestUpdateDTO) {
//        log.debug("Request to save SecurityTest : {}", securityTestUpdateDTO);
//        List<CheckListItem> checkListItems = checkListItemRepository.findAll();
//        List<CheckList> checkListLists = checkListRepository.findAll();
//        Optional<Company> optionalCompany = companyRepository.findById(securityTestUpdateDTO.getCompanyId());
//
//        Optional<User> optionalUser = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin().get());
//
//        double testScore = 0;
//        int UnmarkedHighPriorityCount = 0;
//        StringBuilder sb = new StringBuilder("");
//
//        Optional<SecurityTest> securityTestById = securityTestRepository.findById(securityTestUpdateDTO.getId());
//
//        SecurityTest securityTest = securityTestById.get();
//
//        Set<TestCheckList> testCheckLists = new HashSet<>();
//        securityTest.setId(securityTestUpdateDTO.getId());
//        securityTest.setApplicationName(securityTestUpdateDTO.getApplicationName());
//        securityTest.setSystemNo(securityTestUpdateDTO.getSystemNo());
//        securityTest.company(optionalCompany.isPresent() ? optionalCompany.get():null);
//        securityTest.setApplicationUser(optionalUser.isPresent() ? optionalUser.get() : null);
//        securityTest.setTestStatus("PROCESSING");
//
//
//        for(TestCheckListUpdateDTO testCheckListUpdateDTO : securityTestUpdateDTO.getTestCheckLists()){
//            List<TestCheckListItem>  testCheckListItemDTOS  = new ArrayList<>();
//            TestCheckList  testCheckList = new TestCheckList();
//
//            Optional<CheckList> optionalCheckList = checkListLists
//                .stream()
//                .filter(data -> data.getId().equals(testCheckListUpdateDTO.getCheckListId()))
//                .findAny();
//
//            if(optionalCheckList.isPresent()){
//                testCheckList.setId(testCheckListUpdateDTO.getId());
//                testCheckList.setCheckList(optionalCheckList.get());
//
//
//                for (TestCheckListItemUpdateDTO testCheckListItemUpdateDTO : testCheckListUpdateDTO.getTestCheckListItems()){
//                    TestCheckListItem testCheckListItem = new TestCheckListItem();
//                    Optional<CheckListItem> optionalCheckListItem = optionalCheckList.get().getCheckListItems()
//                        .stream()
//                        .filter(data -> data.getId().equals(testCheckListItemUpdateDTO.getChecklistitemId()))
//                        .findAny();
//
//                    if(optionalCheckListItem.isPresent()){
//                        testCheckListItem.setId(testCheckListItemUpdateDTO.getId());
//                        testCheckListItem.setChecklistitem(optionalCheckListItem.get());
//                        testCheckListItem.setMarked(testCheckListItemUpdateDTO.getMarked());
//                        testCheckListItemDTOS.add(testCheckListItem);
//                        if(testCheckListItem.getMarked()){
//                            testScore = testScore + optionalCheckListItem.get().getValue();
//                        }
//                        if(testCheckListItem.getMarked() == false
//                            && optionalCheckListItem.get().getPriorityLevel().equals(PriorityLevel.HIGH)){
//                            sb.append(optionalCheckListItem.get().getName()+",");
//                            UnmarkedHighPriorityCount++;
//                        }
//                    }
//                }
//                testCheckList.setTestCheckListItems(testCheckListItemDTOS.stream().collect(Collectors.toSet()));
//                testCheckLists.add(testCheckList);
//            }
//        }
//
//        Double total = checkListItemRepository.findsumofValue();
//        double percentage = (testScore/total)*100;
//
//        securityTest.testScore(percentage);
//
//        if(UnmarkedHighPriorityCount > 0){
//            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
//            securityTest.setTestDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
//        } else if(percentage <= 20){
//            securityTest.setSecurityLevel(SecurityLevel.CRITICAL);
//            securityTest.setTestDescription("Your security is too low!" );
//        } else if (percentage <80) {
//            securityTest.setSecurityLevel(SecurityLevel.MODERATE);
//            securityTest.setTestDescription("Your security settings are moderate." );
//        } else{
//            securityTest.setSecurityLevel(SecurityLevel.EXCELLENT);
//            securityTest.setTestDescription("Your security settings are excellent!" );
//        }
//        securityTest.setTestCheckLists(testCheckLists);
//        SecurityTest response = securityTestRepository.save(securityTest);
//        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO(response);
//        return securityTestResponseDTO;
//    }


}

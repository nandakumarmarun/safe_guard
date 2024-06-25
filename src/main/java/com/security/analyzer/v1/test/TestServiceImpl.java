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
import com.security.analyzer.v1.dashboard.Dashboard;
import com.security.analyzer.v1.dashboard.DashboardMapper;
import com.security.analyzer.v1.dashboard.DashboardRepository;
import com.security.analyzer.v1.securitytest.SecurityTest;
import com.security.analyzer.v1.securitytest.SecurityTestDTO;
import com.security.analyzer.v1.securitytest.chart.ChartDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListResponseDTO;
import com.security.analyzer.v1.testchecklist.TestCheckListUpdateDTO;
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

    private final TestRepository testRepository;

    private final CheckListRepository checkListRepository;

    private final CheckListItemRepository checkListItemRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    private final DashboardMapper dashboardMapper;

    private final DashboardRepository dashboardRepository;


    @Override
    public SecurityTestResponseDTO  save(SecurityTestDTO securityTestDTO) {
        log.debug("Request to save SecurityTest : {}", securityTestDTO);
        List<CheckList> checkListLists = checkListRepository.findAll();
        Optional<Company> optionalCompany =
                companyRepository.findById(securityTestDTO.getCompanyId());
        Optional<User> optionalUser = userRepository
                .findByLogin(SecurityUtils.getCurrentUserLogin().orElse(null));
        double testScore = 0;
        int UnmarkedHighPriorityCount = 0;
        StringBuilder sb = new StringBuilder();
        List<Test> tests  = new ArrayList<>();
        String TestID = UUID.randomUUID().toString();

        for(TestCheckListDTO testCheckListDTO : securityTestDTO.getTestCheckLists()){
            Optional<CheckList> optionalCheckList = checkListLists
                .stream()
                .filter(data -> data.getId().equals(testCheckListDTO.getCheckListId()))
                .findAny();
            if(optionalCheckList.isPresent())
             {
                 for (TestCheckListItemDTO testCheckListItemDTO
                         : testCheckListDTO.getTestCheckListItems()){
                     Optional<CheckListItem> optionalCheckListItem =
                             optionalCheckList.get()
                                     .getCheckListItems()
                                     .stream()
                                     .filter(data -> data.getId()
                                             .equals(testCheckListItemDTO
                                                     .getChecklistitemId()))
                                     .findAny();

                     if(optionalCheckListItem.isPresent()){
                         CheckListItem checkListItem = optionalCheckListItem.get();
                         CheckList checkList = optionalCheckList.get();
                         Test test = new  Test();
                         test.setApplicationName(securityTestDTO.getApplicationName());
                         test.setTestID(TestID);
                         test.setSystemNo(securityTestDTO.getSystemNo());
                         test.setCompany(optionalCompany.orElse(null));
                         test.setApplicationUser(optionalUser.orElse(null));
                         test.setTestStatus("PROCESSING");
                         test.setCheckListId(checkList.getId());
                         test.setCheckListName(checkList.getName());
                         test.setChecklistitemId(checkListItem.getId());
                         test.setChecklistitemName(checkListItem.getName());
                         test.setPriorityLevel(checkListItem.getPriorityLevel());
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
        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO();
        for (Map.Entry<String, List<Test>> entry : testMap.entrySet()) {
            String testID = entry.getKey();
            List<Test> testList = testMap.get(testID);
            Map<Long, List<Test>> checklistMap = testList.parallelStream()
                    .collect(Collectors.groupingBy(Test::getCheckListId));
            Test securityTest = testList.get(0);
            securityTestResponseDTO.setApplicationName(securityTest.getApplicationName());
            securityTestResponseDTO.setId(securityTest.getTestID());
            securityTestResponseDTO.setCompanyId(securityTest.getCompany().getId());
            securityTestResponseDTO.setSystemNo(securityTest.getSystemNo());
            securityTestResponseDTO.setUserName(securityTest.getApplicationUser().getLogin());
            securityTestResponseDTO.setUserId(securityTest.getApplicationUser().getId());
            securityTestResponseDTO.setCompanyName(securityTest.getCompany().getCompanyName());
            Set<TestCheckListResponseDTO> testCheckListResponseDTOS = new HashSet<>();
            for (Map.Entry<Long, List<Test>> entry2 : checklistMap.entrySet()) {
                Long checklistID = entry2.getKey();
                List<Test> checkList  = checklistMap.get(checklistID);
                Map<Long, List<Test>> checklistitemMap = checkList.parallelStream()
                        .collect(Collectors.groupingBy(Test::getCheckListId));
                TestCheckListResponseDTO testCheckListResponseDTO = new TestCheckListResponseDTO();
                Test checklistDTO = checkList.get(0);
                testCheckListResponseDTO.setCheckListId(checklistDTO.getCheckListId());
                testCheckListResponseDTO.setChecklistName(checklistDTO.getCheckListName());
                Set<TestCheckListItemResoponseDTO> testCheckListItemResoponseDTOS = new HashSet<>();
                for (Map.Entry<Long, List<Test>> entry3 : checklistitemMap.entrySet()) {
                    Long checklistItemId  = entry3.getKey();
                    List<Test> checklistitems = checklistitemMap.get(checklistItemId);
                    for(Test testCheckListItem : checklistitems){
                        TestCheckListItemResoponseDTO testCheckListItemResoponseDTO =
                                new TestCheckListItemResoponseDTO();
                        testCheckListItemResoponseDTO.setId(testCheckListItem.getId());
                        testCheckListItemResoponseDTO
                                .setChecklistItemName(testCheckListItem.getChecklistitemName());
                        testCheckListItemResoponseDTO.setValue(testCheckListItem.getValue());
                        testCheckListItemResoponseDTO.setMarked(testCheckListItem.getMarked());
                        testCheckListItemResoponseDTO
                                .setChecklistitemId(testCheckListItem.getChecklistitemId());
                        testCheckListItemResoponseDTO
                                .setPriorityLevel(testCheckListItem.getPriorityLevel().toString());
                        testCheckListItemResoponseDTOS.add(testCheckListItemResoponseDTO);
                        if(testCheckListItem.getMarked()){
                            testScore = testScore + testCheckListItem.getValue();
                        }
                        if(!testCheckListItem.getMarked()
                                && testCheckListItem.getPriorityLevel().equals(PriorityLevel.HIGH)){
                            sb.append(testCheckListItem.getPriorityLevel()).append(",");
                            UnmarkedHighPriorityCount++;
                        }
                    }
                }
                testCheckListResponseDTO.setTestCheckListItemResoponseDTOS(testCheckListItemResoponseDTOS);
                testCheckListResponseDTOS.add(testCheckListResponseDTO);
            }
            securityTestResponseDTO.setTestCheckListResponseDTOS(testCheckListResponseDTOS);
        }


        Double total = checkListItemRepository.findsumofValue();
        double percentage = (testScore/total)*100;
        // Iterate over each checklist in the Test object
        securityTestResponseDTO.setTestScore(percentage);

        if(UnmarkedHighPriorityCount > 0){
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
            securityTestResponseDTO.setDescription("Status Changed To Critcal Due To these " + sb.toString() + " unmarked high-priority cases" );
        } else if(percentage <= 20){
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
            securityTestResponseDTO.setDescription("Your security is too low!" );
        } else if (percentage <80) {
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.MODERATE.toString());
            securityTestResponseDTO.setDescription("Your security settings are moderate." );
        } else{
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.EXCELLENT.toString());
            securityTestResponseDTO.setDescription("Your security settings are excellent!" );
        }

        dashboardRepository.save(dashboardMapper.TestToDashboard(securityTestResponseDTO));
        return securityTestResponseDTO;
    }

    @Override
    public SecurityTestResponseDTO update(SecurityTestUpdateDTO securityTestUpdateDTO) {
        log.debug("Request to Update SecurityTest : {}", securityTestUpdateDTO);
        Optional<Company> optionalCompany = companyRepository.findById(securityTestUpdateDTO.getCompanyId());

        Optional<User> optionalUser = userRepository
                .findByLogin(SecurityUtils.getCurrentUserLogin()
                        .orElse(null));

        List<Test> allByTestID = testRepository.findAllByTestID(securityTestUpdateDTO.getId());
        double testScore = 0;
        int UnmarkedHighPriorityCount = 0;
        StringBuilder sb = new StringBuilder();
        List<Test> tests  = new ArrayList<>();

        for(TestCheckListUpdateDTO testCheckListUpdateDTO
                : securityTestUpdateDTO.getTestCheckLists()){
            for (TestCheckListItemUpdateDTO testCheckListItemUpdateDTO
                    : testCheckListUpdateDTO.getTestCheckListItems()){
                   Optional<Test> optionalTest = allByTestID
                            .stream()
                            .filter(data -> data.getChecklistitemId()
                                    .equals(testCheckListItemUpdateDTO.getChecklistitemId()))
                            .findAny();
                   if(optionalTest.isPresent()){
                       Test test = optionalTest.get();
                       test.setApplicationName(securityTestUpdateDTO.getApplicationName());
                       test.setSystemNo(securityTestUpdateDTO.getSystemNo());
                       test.setCompany(optionalCompany.orElse(null));
                       test.setApplicationUser(optionalUser.orElse(null));
                       test.setTestStatus("PROCESSING");
                       test.setMarked(testCheckListItemUpdateDTO.getMarked());
                       tests.add(test);
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
        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO();
        for (Map.Entry<String, List<Test>> entry : testMap.entrySet()) {
            String testID = entry.getKey();
            List<Test> testList = testMap.get(testID);
            Map<Long, List<Test>> checklistMap = testList.parallelStream()
                    .collect(Collectors.groupingBy(Test::getCheckListId));
            Test securityTest = testList.get(0);
            securityTestResponseDTO.setApplicationName(securityTest.getApplicationName());
            securityTestResponseDTO.setId(securityTest.getTestID());
            securityTestResponseDTO.setCompanyId(securityTest.getCompany().getId());
            securityTestResponseDTO.setSystemNo(securityTest.getSystemNo());
            securityTestResponseDTO.setUserName(securityTest.getApplicationUser().getLogin());
            securityTestResponseDTO.setUserId(securityTest.getApplicationUser().getId());
            securityTestResponseDTO.setCompanyName(securityTest.getCompany().getCompanyName());
            Set<TestCheckListResponseDTO> testCheckListResponseDTOS = new HashSet<>();
            for (Map.Entry<Long, List<Test>> entry2 : checklistMap.entrySet()) {
                Long checklistID = entry2.getKey();
                List<Test> checkList  = checklistMap.get(checklistID);
                Map<Long, List<Test>> checklistitemMap = checkList.parallelStream()
                        .collect(Collectors.groupingBy(Test::getCheckListId));
                TestCheckListResponseDTO testCheckListResponseDTO = new TestCheckListResponseDTO();
                Test checklistDTO = checkList.get(0);
                testCheckListResponseDTO.setCheckListId(checklistDTO.getCheckListId());
                testCheckListResponseDTO.setChecklistName(checklistDTO.getCheckListName());
                Set<TestCheckListItemResoponseDTO> testCheckListItemResoponseDTOS = new HashSet<>();
                for (Map.Entry<Long, List<Test>> entry3 : checklistitemMap.entrySet()) {
                    Long checklistItemId  = entry3.getKey();
                    List<Test> checklistitems = checklistitemMap.get(checklistItemId);
                    for(Test testCheckListItem : checklistitems){
                        TestCheckListItemResoponseDTO testCheckListItemResoponseDTO =
                                new TestCheckListItemResoponseDTO();
                        testCheckListItemResoponseDTO.setId(testCheckListItem.getId());
                        testCheckListItemResoponseDTO
                                .setChecklistItemName(testCheckListItem.getChecklistitemName());
                        testCheckListItemResoponseDTO.setValue(testCheckListItem.getValue());
                        testCheckListItemResoponseDTO.setMarked(testCheckListItem.getMarked());
                        testCheckListItemResoponseDTO.
                                setChecklistitemId(testCheckListItem.getChecklistitemId());
                        testCheckListItemResoponseDTO
                                .setPriorityLevel(testCheckListItem.getPriorityLevel().toString());
                        testCheckListItemResoponseDTOS.add(testCheckListItemResoponseDTO);
                        if(testCheckListItem.getMarked()){
                            testScore = testScore + testCheckListItem.getValue();
                        }
                        if(!testCheckListItem.getMarked()
                                && testCheckListItem.getPriorityLevel().equals(PriorityLevel.HIGH)){
                            sb.append(testCheckListItem.getPriorityLevel()).append(",");
                            UnmarkedHighPriorityCount++;
                        }
                    }
                }
                testCheckListResponseDTO.setTestCheckListItemResoponseDTOS(testCheckListItemResoponseDTOS);
                testCheckListResponseDTOS.add(testCheckListResponseDTO);
            }
            securityTestResponseDTO.setTestCheckListResponseDTOS(testCheckListResponseDTOS);
        }


        Double total = checkListItemRepository.findsumofValue();
        double percentage = (testScore/total)*100;
        // Iterate over each checklist in the Test object
        securityTestResponseDTO.setTestScore(percentage);
        if(UnmarkedHighPriorityCount > 0){
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
            securityTestResponseDTO.setDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
        } else if(percentage <= 20){
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
            securityTestResponseDTO.setDescription("Your security is too low!" );
        } else if (percentage <80) {
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.MODERATE.toString());
            securityTestResponseDTO.setDescription("Your security settings are moderate." );
        } else{
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.EXCELLENT.toString());
            securityTestResponseDTO.setDescription("Your security settings are excellent!" );
        }

        Dashboard testToDashboard = dashboardMapper.ToDashboard(securityTestResponseDTO);
        dashboardRepository.save(testToDashboard);
        return securityTestResponseDTO;
    }


    @Override
    public List<SecurityTestResponseDTO> findAllByCompanyId() {
        Optional<User> optionalUser = userRepository
            .findByLogin(SecurityUtils.getCurrentUserLogin().orElse(null));

        List<Test> tests1 = testRepository.findAllByApplicationUserId(optionalUser.get().getId());

        Double total = checkListItemRepository.findsumofValue();

        Map<String, List<Test>> testMap = tests1.parallelStream()
            .collect(Collectors.groupingBy(Test::getTestID));

        List<SecurityTestResponseDTO> securityTestResponseDTOs  =  new ArrayList<>();

        for (Map.Entry<String, List<Test>> entry : testMap.entrySet()) {
            double testScore = 0;
            int UnmarkedHighPriorityCount = 0;
            StringBuilder sb = new StringBuilder();
            String testID = entry.getKey();
            List<Test> testList = testMap.get(testID);
            Map<Long, List<Test>> checklistMap = testList.parallelStream()
                .collect(Collectors.groupingBy(Test::getCheckListId));
            Test securityTest = testList.get(0);
            SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO();
            securityTestResponseDTO.setApplicationName(securityTest.getApplicationName());
            securityTestResponseDTO.setId(securityTest.getTestID());
            securityTestResponseDTO.setCompanyId(securityTest.getCompany().getId());
            securityTestResponseDTO.setSystemNo(securityTest.getSystemNo());
            securityTestResponseDTO.setUserName(securityTest.getApplicationUser().getLogin());
            securityTestResponseDTO.setUserId(securityTest.getApplicationUser().getId());
            securityTestResponseDTO.setCompanyName(securityTest.getCompany().getCompanyName());
            Set<TestCheckListResponseDTO> testCheckListResponseDTOS = new HashSet<>();
            for (Map.Entry<Long, List<Test>> entry2 : checklistMap.entrySet()) {
                Long checklistID = entry2.getKey();
                List<Test> checkList  = checklistMap.get(checklistID);
                Map<Long, List<Test>> checklistitemMap = checkList.parallelStream()
                    .collect(Collectors.groupingBy(Test::getCheckListId));
                TestCheckListResponseDTO testCheckListResponseDTO = new TestCheckListResponseDTO();
                Test checklistDTO = checkList.get(0);
                testCheckListResponseDTO.setCheckListId(checklistDTO.getCheckListId());
                testCheckListResponseDTO.setChecklistName(checklistDTO.getCheckListName());
                Set<TestCheckListItemResoponseDTO> testCheckListItemResoponseDTOS = new HashSet<>();
                for (Map.Entry<Long, List<Test>> entry3 : checklistitemMap.entrySet()) {
                    Long checklistItemId  = entry3.getKey();
                    List<Test> checklistitems = checklistitemMap.get(checklistItemId);
                    for(Test testCheckListItem : checklistitems){
                        TestCheckListItemResoponseDTO testCheckListItemResoponseDTO =
                            new TestCheckListItemResoponseDTO();
                        testCheckListItemResoponseDTO.setId(testCheckListItem.getId());
                        testCheckListItemResoponseDTO
                            .setChecklistItemName(testCheckListItem.getChecklistitemName());
                        testCheckListItemResoponseDTO.setValue(testCheckListItem.getValue());
                        testCheckListItemResoponseDTO.setMarked(testCheckListItem.getMarked());
                        testCheckListItemResoponseDTO.
                            setChecklistitemId(testCheckListItem.getChecklistitemId());
                        testCheckListItemResoponseDTO
                            .setPriorityLevel(testCheckListItem.getPriorityLevel().toString());
                        testCheckListItemResoponseDTOS.add(testCheckListItemResoponseDTO);
                        if(testCheckListItem.getMarked()){
                            testScore = testScore + testCheckListItem.getValue();
                        }
                        if(!testCheckListItem.getMarked()
                            && testCheckListItem.getPriorityLevel().equals(PriorityLevel.HIGH)){
                            sb.append(testCheckListItem.getPriorityLevel()).append(",");
                            UnmarkedHighPriorityCount++;
                        }
                    }
                }
                testCheckListResponseDTO.setTestCheckListItemResoponseDTOS(testCheckListItemResoponseDTOS);
                testCheckListResponseDTOS.add(testCheckListResponseDTO);
            }
            securityTestResponseDTO.setTestCheckListResponseDTOS(testCheckListResponseDTOS);
            double percentage = (testScore/total)*100;
            // Iterate over each checklist in the Test object
            securityTestResponseDTO.setTestScore(percentage);
            if(UnmarkedHighPriorityCount > 0){
                securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
                securityTestResponseDTO.setDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
            } else if(percentage <= 20){
                securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
                securityTestResponseDTO.setDescription("Your security is too low!" );
            } else if (percentage <80) {
                securityTestResponseDTO.setSecurityLevel(SecurityLevel.MODERATE.toString());
                securityTestResponseDTO.setDescription("Your security settings are moderate." );
            } else{
                securityTestResponseDTO.setSecurityLevel(SecurityLevel.EXCELLENT.toString());
                securityTestResponseDTO.setDescription("Your security settings are excellent!" );
            }
            securityTestResponseDTOs.add(securityTestResponseDTO);
        }
        return securityTestResponseDTOs;
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
    public SecurityTestResponseDTO findOne(String id) {
        log.debug("Request to Update SecurityTest : {}", id);

        Optional<User> optionalUser = userRepository
            .findByLogin(SecurityUtils.getCurrentUserLogin()
                .orElse(null));

        List<Test> allByTestID = testRepository.findAllByTestID(id);
        double testScore = 0;
        int UnmarkedHighPriorityCount = 0;
        StringBuilder sb = new StringBuilder();

        Map<String, List<Test>> testMap = allByTestID.parallelStream()
            .collect(Collectors.groupingBy(Test::getTestID));

        SecurityTestResponseDTO securityTestResponseDTO = new SecurityTestResponseDTO();
        for (Map.Entry<String, List<Test>> entry : testMap.entrySet()) {
            String testID = entry.getKey();
            List<Test> testList = testMap.get(testID);
            Map<Long, List<Test>> checklistMap = testList.parallelStream()
                .collect(Collectors.groupingBy(Test::getCheckListId));
            Test securityTest = testList.get(0);
            securityTestResponseDTO.setApplicationName(securityTest.getApplicationName());
            securityTestResponseDTO.setId(securityTest.getTestID());
            securityTestResponseDTO.setCompanyId(securityTest.getCompany().getId());
            securityTestResponseDTO.setSystemNo(securityTest.getSystemNo());
            securityTestResponseDTO.setUserName(securityTest.getApplicationUser().getLogin());
            securityTestResponseDTO.setUserId(securityTest.getApplicationUser().getId());
            securityTestResponseDTO.setCompanyName(securityTest.getCompany().getCompanyName());
            Set<TestCheckListResponseDTO> testCheckListResponseDTOS = new HashSet<>();
            for (Map.Entry<Long, List<Test>> entry2 : checklistMap.entrySet()) {
                Long checklistID = entry2.getKey();
                List<Test> checkList  = checklistMap.get(checklistID);
                Map<Long, List<Test>> checklistitemMap = checkList.parallelStream()
                    .collect(Collectors.groupingBy(Test::getCheckListId));
                TestCheckListResponseDTO testCheckListResponseDTO = new TestCheckListResponseDTO();
                Test checklistDTO = checkList.get(0);
                testCheckListResponseDTO.setCheckListId(checklistDTO.getCheckListId());
                testCheckListResponseDTO.setChecklistName(checklistDTO.getCheckListName());
                Set<TestCheckListItemResoponseDTO> testCheckListItemResoponseDTOS = new HashSet<>();
                for (Map.Entry<Long, List<Test>> entry3 : checklistitemMap.entrySet()) {
                    Long checklistItemId  = entry3.getKey();
                    List<Test> checklistitems = checklistitemMap.get(checklistItemId);
                    for(Test testCheckListItem : checklistitems){
                        TestCheckListItemResoponseDTO testCheckListItemResoponseDTO =
                            new TestCheckListItemResoponseDTO();
                        testCheckListItemResoponseDTO.setId(testCheckListItem.getId());
                        testCheckListItemResoponseDTO
                            .setChecklistItemName(testCheckListItem.getChecklistitemName());
                        testCheckListItemResoponseDTO.setValue(testCheckListItem.getValue());
                        testCheckListItemResoponseDTO.setMarked(testCheckListItem.getMarked());
                        testCheckListItemResoponseDTO.
                            setChecklistitemId(testCheckListItem.getChecklistitemId());
                        testCheckListItemResoponseDTO
                            .setPriorityLevel(testCheckListItem.getPriorityLevel().toString());
                        testCheckListItemResoponseDTOS.add(testCheckListItemResoponseDTO);
                        if(testCheckListItem.getMarked()){
                            testScore = testScore + testCheckListItem.getValue();
                        }
                        if(!testCheckListItem.getMarked()
                            && testCheckListItem.getPriorityLevel().equals(PriorityLevel.HIGH)){
                            sb.append(testCheckListItem.getPriorityLevel()).append(",");
                            UnmarkedHighPriorityCount++;
                        }
                    }
                }
                testCheckListResponseDTO.setTestCheckListItemResoponseDTOS(testCheckListItemResoponseDTOS);
                testCheckListResponseDTOS.add(testCheckListResponseDTO);
            }
            securityTestResponseDTO.setTestCheckListResponseDTOS(testCheckListResponseDTOS);
        }


        Double total = checkListItemRepository.findsumofValue();
        double percentage = (testScore/total)*100;
        // Iterate over each checklist in the Test object
        securityTestResponseDTO.setTestScore(percentage);
        if(UnmarkedHighPriorityCount > 0){
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
            securityTestResponseDTO.setDescription("Status Changed To Critcal Due To these "+sb.toString() + " unmarked high-priority cases" );
        } else if(percentage <= 20){
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.CRITICAL.toString());
            securityTestResponseDTO.setDescription("Your security is too low!" );
        } else if (percentage <80) {
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.MODERATE.toString());
            securityTestResponseDTO.setDescription("Your security settings are moderate." );
        } else{
            securityTestResponseDTO.setSecurityLevel(SecurityLevel.EXCELLENT.toString());
            securityTestResponseDTO.setDescription("Your security settings are excellent!" );
        }
        return securityTestResponseDTO;
    }

    @Override
    public void UpdateStatus(String id) {

    }

    @Override
    public void delete(Long id) {

    }



}

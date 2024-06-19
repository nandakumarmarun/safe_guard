package com.security.analyzer.v1.testchecklist;

import com.security.analyzer.v1.testchecklistitem.TestCheckListItemDTO;
import com.security.analyzer.v1.testchecklistitem.TestCheckListItemUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCheckListUpdateDTO {
    private Long id;
    private Set<TestCheckListItemUpdateDTO> testCheckListItemUpdateDTOS;
    private Long checkListId;
}

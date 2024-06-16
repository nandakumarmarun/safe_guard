package com.security.analyzer.v1.testchecklist;

import com.security.analyzer.v1.testchecklistitem.TestCheckListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCheckListDTO {
    private Set<TestCheckListItemDTO> testCheckListItems;
    private Long checkListId;
}

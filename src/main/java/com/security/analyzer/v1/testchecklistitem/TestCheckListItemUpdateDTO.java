package com.security.analyzer.v1.testchecklistitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCheckListItemUpdateDTO {
    private Long id;
    private Boolean marked;
    private Long checklistitemId;
}

package com.security.analyzer.v1.testchecklistitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCheckListItemDTO {
    private Boolean marked;
    private Long checklistitemId;
}

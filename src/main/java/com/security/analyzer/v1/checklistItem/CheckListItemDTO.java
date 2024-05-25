package com.security.analyzer.v1.checklistItem;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckListItemDTO {

    private String ChecklistItemName;

    private Double value;

    private String priorityLevel;
}

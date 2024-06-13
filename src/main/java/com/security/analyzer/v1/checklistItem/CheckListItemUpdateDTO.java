package com.security.analyzer.v1.checklistItem;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckListItemUpdateDTO {

    private Long id;

    private String ChecklistItemName;

    private Double value;

    private String priorityLevel;

    private Long checkListId;

}

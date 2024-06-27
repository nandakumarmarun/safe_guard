package com.security.analyzer.v1.checklistItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MultiCheckLisItemCreatetDTO {
    private Long checkListId;
    List<CheckListItemCreateDTO> checkListItemCreateDTO;
}

package com.security.analyzer.v1.checklist;

import com.security.analyzer.v1.checklistItem.CheckListItemCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckListRequestDTO {

    private String checklistName;

    private List<CheckListItemCreateDTO> checkListItemDTO;

}

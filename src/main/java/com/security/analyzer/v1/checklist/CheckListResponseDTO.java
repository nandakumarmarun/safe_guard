package com.security.analyzer.v1.checklist;

import com.security.analyzer.v1.checklistItem.CheckListItemResposeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckListResponseDTO {

    private Long id;
    private String checklistName;
    private List<CheckListItemResposeDTO> checkListItemDTO;

}

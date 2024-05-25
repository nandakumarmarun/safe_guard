package com.security.analyzer.v1.checklist;

import com.security.analyzer.v1.Enum.PriorityLevel;
import com.security.analyzer.v1.checklistItem.CheckListItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckListRequestDTO {

    private String checklistName;

    private List<CheckListItemDTO> checkListItemDTO;

}

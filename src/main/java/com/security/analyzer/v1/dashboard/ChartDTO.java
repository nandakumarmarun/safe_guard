package com.security.analyzer.v1.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDTO {
    private long sucessCount;

    private long moderateCount;

    private long Failedcount;

}

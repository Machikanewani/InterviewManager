package com.example.InterviewManager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockEntity {
    private long id;
    private long whichCompanyId;
    private String blockName;
    private String date;
    private String memo;

    public BlockEntity(){}
}

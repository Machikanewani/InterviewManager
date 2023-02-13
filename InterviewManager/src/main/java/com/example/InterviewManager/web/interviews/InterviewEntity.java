package com.example.InterviewManager.web.interviews;

import com.example.InterviewManager.domain.BlockEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InterviewEntity {
    private long companyId;
    @NotBlank
    private String companyName;
    private String link;
    private List<BlockEntity> blocks;

    public InterviewEntity() {}
}

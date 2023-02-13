package com.example.InterviewManager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyEntity {
    private long id;
    private String name;
    private String link;
}

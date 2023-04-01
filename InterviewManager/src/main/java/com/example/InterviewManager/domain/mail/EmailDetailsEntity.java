package com.example.InterviewManager.domain.mail;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailDetailsEntity {
    private String to;
    private String subject;
    private String text;
    private LocalDateTime scheduleTime;
    private long blockId;

    public EmailDetailsEntity(String to, String subject, String text, LocalDateTime scheduleTime, long blockId) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.scheduleTime = scheduleTime;
        this.blockId = blockId;
    }
}

package com.example.InterviewManager.domain.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmailScheduler {
    private final EmailService emailService;
    private static final List<EmailDetailsEntity> scheduledEmailsList = new ArrayList<>();

    @Scheduled(fixedDelay = 3000)
    public void sendScheduledEmail(){
        for(EmailDetailsEntity emailDetails: scheduledEmailsList){
            if(LocalDateTime.now().isAfter(emailDetails.getScheduleTime().minusDays(1))){
                String to = emailDetails.getTo();
                String subject = emailDetails.getSubject();
                String text = emailDetails.getText();

                emailService.sendEmail(to, subject, text);
                scheduledEmailsList.remove(emailDetails);
            }
        }
    }

    public void addToScheduledEmailsList(EmailDetailsEntity emailDetails){
        scheduledEmailsList.add(emailDetails);
    }

    public void removeFromScheduledEmailsList(long blockId){
        for(EmailDetailsEntity emailDetails: scheduledEmailsList){
            if(emailDetails.getBlockId() == blockId){
                scheduledEmailsList.remove(emailDetails);
                return;
            }
        }
    }
}

package com.example.InterviewManager.domain;

import com.example.InterviewManager.domain.mail.EmailDetailsEntity;
import com.example.InterviewManager.domain.mail.EmailScheduler;
import com.example.InterviewManager.web.interviews.InterviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final EmailScheduler emailScheduler;
    private final String subjectTemplate = "Reminder of your upcoming interview process (NO REPLY)";
    private final String bodyTemplate =
            """
                    Dear %s
                    
                    There is going to be an interview or submit dead line of the Assignment for you.
                    
                    The date and time: 
                    
                    date: %d/%s/%s
                    time: %s:%s
                    
                    (This email is send automatically from InterviewManager)
                    """;

    public List<InterviewEntity> findAll(OidcUser user) {
        Long userId = getOrGenerateUserIdByEmail(user.getEmail(), user.getPicture());

        List<CompanyEntity> companies = interviewRepository.findAllCompanies(userId);

        List<InterviewEntity> list = new ArrayList<>();

        companies.forEach(e ->
            list.add(new InterviewEntity(e.getId(), e.getName(), e.getLink(),
                    interviewRepository.findAllBlocksByCompany(e.getId())))
        );

        return list;
    }

    public void createCompany(OidcUser user, String companyName, String link) {
        Long userId = getOrGenerateUserIdByEmail(user.getEmail(), user.getPicture());

        interviewRepository.insertCompany(userId, companyName, link);
    }

    public long getCompanyId(String companyName) {
        return interviewRepository.getCompanyId(companyName);
    }

    public void createBlock(OidcUser user, BlockEntity blockEntity) {
        interviewRepository.insertBlock(blockEntity.getWhichCompanyId(), blockEntity.getBlockName(),
                blockEntity.getDateTime(), blockEntity.getMemo());

        long blockId = interviewRepository.getBlockId(blockEntity.getWhichCompanyId(), blockEntity.getBlockName());

        if(blockEntity.getDateTime() != null){
            createAndAddScheduledEmail(user, blockEntity, blockId);
        }
    }

    public InterviewEntity findInterviewById(long companyId) {

        CompanyEntity companyEntity = interviewRepository.findCompanyById(companyId);

        return new InterviewEntity(companyEntity.getId(), companyEntity.getName(), companyEntity.getLink(),
                interviewRepository.findAllBlocksByCompany(companyId));
    }

    public void editCompany(long companyId, String companyName, String link) {
        interviewRepository.updateCompany(companyId, companyName, link);
    }

    public void editBlocks(OidcUser user, List<BlockEntity> blocks) {
        if(blocks != null){
            blocks.forEach(block -> {
                BlockEntity oldBlock = interviewRepository.findBlockById(block.getId());

                String oldDateTime = oldBlock.getDateTime();
                String newDateTime = block.getDateTime();

                if(oldDateTime == null && newDateTime != null){
                    createAndAddScheduledEmail(user, block, oldBlock.getId());
                }
                else if(oldDateTime != null && newDateTime != null){
                    //datetime is changed
                    if(!oldDateTime.equals(newDateTime)){
                        removeScheduledEmail(block.getId());
                        createAndAddScheduledEmail(user, block, oldBlock.getId());
                    }
                }
                interviewRepository.updateBlock(block.getId(), block.getWhichCompanyId(),
                        block.getBlockName(), block.getDateTime(), block.getMemo());
            });
        }
    }

    public void deleteEntireInterview(long companyId) {
        List<BlockEntity> blocks = interviewRepository.findAllBlocksByCompany(companyId);
        blocks.forEach(block -> removeScheduledEmail(block.getId()));

        interviewRepository.deleteAllBlocks(companyId);

        interviewRepository.deleteCompany(companyId);
    }

    public void deleteBlock(long blockId) {
        interviewRepository.deleteBlock(blockId);
        removeScheduledEmail(blockId);
    }

    private Long getOrGenerateUserIdByEmail(String email, String url){
        Long userId = interviewRepository.getUserIdByEmail(email);
        if(userId == null){
            interviewRepository.insertUser(email, url);
            userId = interviewRepository.getUserIdByEmail(email);
        }
        return userId;
    }

    private void createAndAddScheduledEmail(OidcUser user, BlockEntity blockEntity, long blockId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(blockEntity.getDateTime(), formatter);
        String month = String.format("%02d", dateTime.getMonthValue());
        String dayOfMonth = String.format("%02d", dateTime.getDayOfMonth());
        String hour = String.format("%02d", dateTime.getHour());
        String minute = String.format("%02d", dateTime.getMinute());

        String body = String.format(bodyTemplate,
                user.getAttribute("name"),
                dateTime.getYear(), month, dayOfMonth, hour, minute);

        EmailDetailsEntity emailDetails = new EmailDetailsEntity(user.getEmail(), subjectTemplate, body, dateTime, blockId);

        emailScheduler.addToScheduledEmailsList(emailDetails);
    }

    private void removeScheduledEmail(long blockId){
        emailScheduler.removeFromScheduledEmailsList(blockId);
    }

}

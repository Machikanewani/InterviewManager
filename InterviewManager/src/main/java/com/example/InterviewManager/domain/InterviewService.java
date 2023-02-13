package com.example.InterviewManager.domain;

import com.example.InterviewManager.web.interviews.InterviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;

    public List<InterviewEntity> findAll() {
        List<CompanyEntity> companies = interviewRepository.findAllCompanies();

        List<InterviewEntity> list = new ArrayList<>();

        companies.forEach(e ->
            list.add(new InterviewEntity(e.getId(), e.getName(), e.getLink(),
                    interviewRepository.findAllBlocksByCompany(e.getId())))
        );

        return list;
    }

    public void createCompany(String companyName, String link) {
        interviewRepository.insertCompany(companyName, link);
    }

    public long getCompanyId(String companyName) {
        return interviewRepository.getCompanyId(companyName);
    }

    public void createBlock(BlockEntity blockEntity) {
        interviewRepository.insertBlock(blockEntity.getWhichCompanyId(), blockEntity.getBlockName(),
                blockEntity.getDate(), blockEntity.getMemo());
    }

    public InterviewEntity findInterviewById(long companyId) {
        CompanyEntity companyEntity = interviewRepository.findCompanyById(companyId);
        return new InterviewEntity(companyEntity.getId(), companyEntity.getName(), companyEntity.getLink(),
                interviewRepository.findAllBlocksByCompany(companyId));
    }

    public void editCompany(long companyId, String companyName, String link) {
        interviewRepository.updateCompany(companyId, companyName, link);
    }

    public void editBlocks(List<BlockEntity> blocks) {
        if(!blocks.isEmpty()){
            blocks.forEach(block -> {
                interviewRepository.updateBlock(block.getId(), block.getWhichCompanyId(),
                        block.getBlockName(), block.getDate(), block.getMemo());
            });
        }
    }

    public void deleteEntireInterview(long companyId) {
        interviewRepository.deleteAllBlocks(companyId);

        interviewRepository.deleteCompany(companyId);
    }

    public void deleteBlock(long blockId) {
        interviewRepository.deleteBlock(blockId);
    }
}

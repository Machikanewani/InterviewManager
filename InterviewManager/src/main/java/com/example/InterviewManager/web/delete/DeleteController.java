package com.example.InterviewManager.web.delete;

import com.example.InterviewManager.domain.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/delete")
@RequiredArgsConstructor
public class DeleteController {
    private final InterviewService interviewService;

    @GetMapping("/company/{companyId}")
    public String deleteCompany(@PathVariable long companyId){
        interviewService.deleteEntireInterview(companyId);

        return "redirect:/interviews";
    }

    @GetMapping("/block/{companyId}/{blockId}")
    public String deleteBlock(@PathVariable long companyId, @PathVariable long blockId){
        interviewService.deleteBlock(blockId);
        return "redirect:/edit/" + companyId;
    }
}

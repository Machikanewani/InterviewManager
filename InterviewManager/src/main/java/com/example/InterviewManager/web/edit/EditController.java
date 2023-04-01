package com.example.InterviewManager.web.edit;

import com.example.InterviewManager.domain.InterviewService;
import com.example.InterviewManager.web.interviews.InterviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
@RequiredArgsConstructor
public class EditController {
    private final InterviewService interviewService;

    @GetMapping("/{companyId}")
    public String showEditInterview(@AuthenticationPrincipal OidcUser user, @PathVariable long companyId, Model model){

        model.addAttribute("interviewEntity", interviewService.findInterviewById(companyId));

        return "edit/editInterview.html";
    }

    @PutMapping("/{companyId}")
    public String getEditInterview(@AuthenticationPrincipal OidcUser user, @PathVariable long companyId, InterviewEntity interviewEntity, BindingResult bindingResult, Model model){
        interviewService.editCompany(companyId, interviewEntity.getCompanyName(), interviewEntity.getLink());
        interviewService.editBlocks(user, interviewEntity.getBlocks());
        return "redirect:/interviews";
    }
}

package com.example.InterviewManager.web.interviews;

import com.example.InterviewManager.domain.BlockEntity;
import com.example.InterviewManager.domain.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewsController {
    private final InterviewService interviewService;

    @GetMapping
    public String showList(@AuthenticationPrincipal OidcUser user, Model model){

        model.addAttribute("interviews", interviewService.findAll(user));

        return "/interviews/list.html";
    }

    @GetMapping("/create")
    public String showCreationForm(Model model){
        model.addAttribute("interview", new InterviewEntity());

        return "/interviews/createInterview.html";
    }

    @PostMapping("/postCompany")
    public String getPostCompany(@AuthenticationPrincipal OidcUser user, @Validated InterviewEntity interviewEntity, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return showCreationForm(model);
        }

        interviewService.createCompany(user, interviewEntity.getCompanyName(), interviewEntity.getLink());
        var companyId = interviewService.getCompanyId(interviewEntity.getCompanyName());

        return "redirect:/interviews/newPart/" + companyId;
    }

    @GetMapping("/newPart/{companyId}")
    public String showNewPart(@PathVariable long companyId, Model model){
        BlockEntity blockEntity = new BlockEntity();
        blockEntity.setWhichCompanyId(companyId);

        model.addAttribute("blockEntity", blockEntity);

        return "/interviews/createPart.html";
    }

    @PostMapping("/newPart/{companyId}")
    public String getNewPart(@AuthenticationPrincipal OidcUser user, @PathVariable long companyId, BlockEntity blockEntity,
                             BindingResult bindingResult,Model model){
        blockEntity.setWhichCompanyId(companyId);
        interviewService.createBlock(blockEntity);

        return "redirect:/interviews/newPart/" + companyId;
    }

}

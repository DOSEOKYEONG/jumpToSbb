package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionForm;
import com.ll.exam.sbb.question.QuestionService;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable("id") Integer id, Model model, @Valid AnswerForm answerForm,
                               BindingResult bindingResult, Principal principal) {
        SiteUser siteUser = userService.findByUsername(principal.getName());
        Question question = questionService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute(question);
            return "question_detail";
        }

        Integer answerId = answerService.create(question, answerForm.getContent(), siteUser);

        return String.format("redirect:/question/detail/%d#answer_%d", question.getId(), answerId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = answerService.findById(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        answerForm.setContent(answer.getContent());

        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal,
                                 @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Answer answer = answerService.findById(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerService.modify(answer, answerForm.getContent());

        return String.format("redirect:/question/detail/%d#answer_%d", answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(@PathVariable("id") Integer id, Principal principal) {
        Answer answer = answerService.findById(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerService.delete(answer);

        return String.format("redirect:/question/detail/%d", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerRecommend(@PathVariable("id") Integer id, Principal principal) {
        Answer answer = answerService.findById(id);
        SiteUser siteUser = userService.findByUsername(principal.getName());

        answerService.recommend(answer, siteUser);

        return String.format("redirect:/question/detail/%d#answer_%d", answer.getQuestion().getId(), answer.getId());
    }
}

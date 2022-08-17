package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable("id") Integer id, Model model, @Valid AnswerForm answerForm,
                               BindingResult bindingResult) {
        Question question = questionService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute(question);
            return "question_detail";
        }

        answerService.create(question, answerForm.getContent());

        return String.format("redirect:/question/detail/%d", id);
    }
}

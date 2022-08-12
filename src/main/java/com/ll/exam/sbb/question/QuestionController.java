package com.ll.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController
{
    private final QuestionService questionService;

    private final QuestionRepository questionRepository;

    @GetMapping("/list")
    public String showList(Model model) {
        List<Question> questionList = questionRepository.findAll();

        model.addAttribute(questionList);
        return "question_list";
    }
}

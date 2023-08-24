package com.example.temnikovJavaProject.controllers;

import com.example.temnikovJavaProject.models.TestPost;
import com.example.temnikovJavaProject.repo.TestPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//Контроллеры
//Это API, к которой будет общаться специалист на Front-end через запросы.
@Controller
public class MainController {

    @Autowired
    private TestPostRepository testPostRepository;

    @GetMapping("/") // при переходе на главную страницу вызовиться ->
    public String home(Model model) {
        Iterable<TestPost> tests = testPostRepository.findAll();
        model.addAttribute("tests", tests);

        model.addAttribute("title", "Main page");

        return "home"; // вызов шаблона home
    }

}

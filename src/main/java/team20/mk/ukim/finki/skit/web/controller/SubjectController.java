package team20.mk.ukim.finki.skit.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;
import team20.mk.ukim.finki.skit.service.CategoryService;
import team20.mk.ukim.finki.skit.service.SubjectService;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String getSubjects(Model model){
        List<Subject> allSubjects= subjectService.getAllSubjects();
        model.addAttribute("subjects", allSubjects);
        model.addAttribute("bodyContent", "subjects");
        return "master-template";
    }

    @GetMapping("/{id}")
    public String getItemsInCategory(@PathVariable Long id, Model model){
        List<Item> items= subjectService.getAllItemsForSubject(id);
        model.addAttribute("products", items);
        model.addAttribute("bodyContent", "items-in-subject");
        return "master-template";
    }
}

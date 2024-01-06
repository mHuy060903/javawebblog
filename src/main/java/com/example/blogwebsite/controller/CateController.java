package com.example.blogwebsite.controller;

import com.example.blogwebsite.model.Category;
import com.example.blogwebsite.reponsitory.CateReponsitory;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
@Slf4j
@Controller
@RequestMapping("admin")
public class CateController {

    @Autowired
    CateReponsitory cateReponsitory;
    @GetMapping("/categories/add")
    public ModelAndView displayAddCate(Model model) {
        ModelAndView modelAndView = new ModelAndView("admin/addcate.html");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("action", "Add Category");
        return modelAndView;
    }

    @GetMapping("/categories")
    public ModelAndView displayCategories(Model model) {
        List<Category> categoryList = cateReponsitory.findAll();
//        for(int i = 0; i < categoryList.size(); i++) {
//            System.out.println(categoryList.get(i).getCreatedAt());
//        }
        ModelAndView modelAndView = new ModelAndView("admin/categories.html");
        modelAndView.addObject("categories", categoryList);
        return modelAndView;
    }

    @PostMapping("/addcate")
    public ModelAndView addNewCate(Model model, @Valid @ModelAttribute("category") Category category, Errors errors) {
        if(errors.hasErrors()){


            ModelAndView modelAndView = new ModelAndView("redirect:/admin/categories/add");
            return modelAndView;
        }
        cateReponsitory.save(category);
        ModelAndView modelAndView1 = new ModelAndView("redirect:/admin/categories");


        return modelAndView1;
    }

    @RequestMapping("/deleteCate")
    public ModelAndView deleteCategory(Model model, @RequestParam int id) {
        cateReponsitory.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/categories?delete");
        return modelAndView;
    }

    @GetMapping("/updateCate")
    public ModelAndView showEditCategory(Model model, @RequestParam int id) {
     Optional<Category> category = cateReponsitory.findById(id);
     if(category.isPresent()) {
     Category categoryEdit = category.get();
     ModelAndView modelAndView1 = new ModelAndView("admin/addcate.html");
     modelAndView1.addObject("category", categoryEdit);
     modelAndView1.addObject("action", "Edit Category");
     return modelAndView1;
     }

     ModelAndView modelAndView = new ModelAndView("redirect:/admin/categories");
     return modelAndView;
    }


}

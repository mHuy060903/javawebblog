package com.example.blogwebsite.controller;

import com.example.blogwebsite.model.Category;
import com.example.blogwebsite.model.Post;
import com.example.blogwebsite.reponsitory.CateReponsitory;
import com.example.blogwebsite.reponsitory.PostReponsitory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class PostController {
    @Autowired
    CateReponsitory cateReponsitory;

    @Autowired
    PostReponsitory postReponsitory;

    @GetMapping("/posts/add")
    public ModelAndView displayAddCate(Model model) {
        ModelAndView modelAndView = new ModelAndView("admin/addpost.html");
        List<Category> categoryList = cateReponsitory.findAll();
        modelAndView.addObject("catelist",categoryList);

        modelAndView.addObject("post", new Post());
        modelAndView.addObject("action", "Add Post");
        return modelAndView;
    }

    @PostMapping("/addpost")
    public ModelAndView addPost(Model model, @RequestParam("file")MultipartFile file, @Valid @ModelAttribute("post") Post post, Errors errors) throws IOException, SQLException {
        if(post.getPostId() != 0) {


            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                post.setImageData(blob);
            } else {
                Post postOld = postReponsitory.findById(post.getPostId()).get();
                post.setImageData(postOld.getImageData());
            }
        }
        postReponsitory.save(post);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/posts");
        return modelAndView;
    }

    @GetMapping("/posts")
    public ModelAndView displayAllPosts(Model model) {
        ModelAndView modelAndView = new ModelAndView("admin/posts.html");
        List<Post> posts = postReponsitory.findAll();
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") int id) throws IOException, SQLException
    {
        Post post = postReponsitory.findById(id).get();

        byte [] imageBytes = null;
        imageBytes = post.getImageData().getBytes(1,(int) post.getImageData().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("/deletePost")
    public ModelAndView deletePost(Model model, @RequestParam("id") int id) {
        Optional<Post> postOptional = postReponsitory.findById(id);
        if(postOptional.isPresent()) {
            postReponsitory.deleteById(id);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/posts");
        return modelAndView;
    }

    @GetMapping("/updatePost")
    public ModelAndView updatePost(Model model, @RequestParam("id") int id) {
        Optional<Post> postOptional = postReponsitory.findById(id);
        if(postOptional.isPresent()) {
            Post post = postOptional.get();
            ModelAndView modelAndView = new ModelAndView("admin/addpost.html");
            List<Category> categoryList = cateReponsitory.findAll();
            modelAndView.addObject("catelist",categoryList);
            modelAndView.addObject("post",post);
            modelAndView.addObject("action", "Edit Post");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/posts");
        return modelAndView;
    }
}

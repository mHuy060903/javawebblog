package com.example.blogwebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Blob;

@Data
@Entity
@Table(name = "post")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int postId;

    @NotBlank(message = "Name must be not blank")
    @Size(min=5, message="Name must be at least 5 characters long")
    private String title;

    @NotBlank(message = "Name must be not blank")
    @Size(min=5, message="Name must be at least 5 characters long")
    private String subTitle;
    @Lob
    private Blob imageData;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Category.class)
    @JoinColumn(name = "cate_id", referencedColumnName = "cateId",nullable = false)
    private Category category;


    @NotBlank(message = "Name must be not blank")
    @Size(min=5, message="Name must be at least 5 characters long")
    private String content;

}

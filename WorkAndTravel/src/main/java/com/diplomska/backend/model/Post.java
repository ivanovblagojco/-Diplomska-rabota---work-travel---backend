package com.diplomska.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post", schema="wat")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<File> files;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}

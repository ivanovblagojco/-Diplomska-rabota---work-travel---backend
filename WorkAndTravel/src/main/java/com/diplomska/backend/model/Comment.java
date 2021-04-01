package com.diplomska.backend.model;

import com.diplomska.backend.helpers.CommentHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comment", schema="wat")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @JsonIgnore
    public CommentHelper getAsCommentHelper(){
        CommentHelper commentHelper = new CommentHelper();

        commentHelper.setId(id);
        commentHelper.setDescription(description);
        commentHelper.setEmail(user.getEmail());

        return commentHelper;
    }
}


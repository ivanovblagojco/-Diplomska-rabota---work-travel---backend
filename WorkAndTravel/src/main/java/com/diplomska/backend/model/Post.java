package com.diplomska.backend.model;

import com.diplomska.backend.helpers.PostHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post", schema="wat")
public class Post{
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



    public PostHelper getAsPostHelper(){
        PostHelper p = new PostHelper();

        p.setId(this.id);
        p.setTitle(this.title);
        p.setDescription(this.description);
        p.setMime_type(files.get(0).getMime_type());
        p.setBytes(Base64.encodeBase64String(files.get(0).getContent()));

        return p;
    }



}

package com.diplomska.backend.model;

import com.diplomska.backend.helpers.UserHelper;
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
@Table(name="user", schema="wat")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String telephone;

    private Boolean is_agency;

    private Boolean is_enabled;

    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<File> files;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Token token;


    @JsonIgnore
    public UserHelper getAsUserHelper(){
        UserHelper userHelper = new UserHelper();

        userHelper.setEmail(email);
        userHelper.setName(name);
        userHelper.setSurname(surname);
        userHelper.setRole(role.getName());

        return userHelper;
    }
}

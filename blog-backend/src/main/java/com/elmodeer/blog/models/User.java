package com.elmodeer.blog.models;

import com.elmodeer.blog.serialization.AddressCustomDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
//    @JsonIgnore
    private String password;

    private String aboutMe;

    private String firstName;

    private String lastName;

    private String imageUrl;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonDeserialize(using = AddressCustomDeserializer.class)
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    public User(String username, String email,
                String firstName , String aboutMe, String lastName,
                Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.roles = roles;
    }

    public User(String username, String email, String encode) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addArticle(Article article) {
        articles.add(article);
        article.setAuthor(this);
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.setAuthor(null);
    }
}

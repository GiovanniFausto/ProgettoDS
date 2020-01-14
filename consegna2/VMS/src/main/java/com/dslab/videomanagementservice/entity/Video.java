package com.dslab.videomanagementservice.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @ManyToOne
    private User user;

    private String stato;

    //Getter e Setter


    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getStato() {return stato;}

    public void setStato(String stato) {this.stato = stato;}

}

package it.uniroma3.siw.hz.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    @ManyToOne
    private Movie movie;

    public Image(){

    }
    public Image(String name) {
        this.name  = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

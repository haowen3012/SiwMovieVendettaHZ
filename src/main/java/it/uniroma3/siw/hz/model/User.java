package it.uniroma3.siw.hz.model;

import jakarta.persistence.*;


@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String name;
	private String surname;
	private String email;

	private String picture;

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
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String photo) {
		this.picture = photo;
	}

	@Transient
	public String getPictureImagePath() {
		if (this.picture == null || id == null) return null;

		return "/files/" + id + "/" + this.picture;
	}
}
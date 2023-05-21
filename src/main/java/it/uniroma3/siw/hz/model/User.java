package it.uniroma3.siw.hz.model;

import it.uniroma3.siw.hz.oauth.AuthenticationProvider;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "users") // cambiamo nome perchè in postgres user e' una parola riservata
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


	private String userName;
	private String name;
	private String surname;
	private String email;

	private String picture;

	@CreationTimestamp
	private LocalDateTime creationTimestamp;

	@UpdateTimestamp
	private LocalDateTime lastUpdateTimestamp;

	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthenticationProvider oAuthProvider;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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


	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(LocalDateTime creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public LocalDateTime getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public AuthenticationProvider getoAuthProvider() {
		return oAuthProvider;
	}

	public void setoAuthProvider(AuthenticationProvider oAuthProvider) {
		this.oAuthProvider = oAuthProvider;
	}

	@Transient
	public String getPictureImagePath() {
		if (this.picture == null || id == null) return null;

		return "/files/" + id + "/" + this.picture;
	}
}
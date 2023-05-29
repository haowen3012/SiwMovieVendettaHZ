package it.uniroma3.siw.hz.model;

import it.uniroma3.siw.hz.oauth.AuthenticationProvider;
import it.uniroma3.siw.hz.repository.ImageRepository;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


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

	@OneToOne
	private Image picture;

	@CreationTimestamp
	private LocalDateTime creationTimestamp;

	@UpdateTimestamp
	private LocalDateTime lastUpdateTimestamp;

	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthenticationProvider oAuthProvider;


	@OneToMany(mappedBy = "author")
	private Set<Review> reviewSet;

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

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image photo) {
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


	public Set<Review> getReviewSet() {
		return reviewSet;
	}

	public void setReviewSet(Set<Review> reviewSet) {
		this.reviewSet = reviewSet;
	}


}
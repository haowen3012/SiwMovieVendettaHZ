package it.uniroma3.siw.hz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CollectionIdJavaType;
import org.hibernate.annotations.CollectionType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
	private String title;


	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;



	@OneToOne
	private Image image;





	@OneToMany(mappedBy = "movie")
	private Set<Image> scenes;
	
	@ManyToOne
	private Artist director;

	@ManyToMany
	private Set<Artist> actors;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Set<Image> getScenes() {
		return scenes;
	}

	public void setScenes(Set<Image> scenes) {
		this.scenes = scenes;
	}

	public Artist getDirector() {
		return director;
	}

	public void setDirector(Artist director) {
		this.director = director;
	}

	public Set<Artist> getActors() {
		return actors;
	}

	public void setActors(Set<Artist> actors) {
		this.actors = actors;
	}



	@Override
	public int hashCode() {
		return Objects.hash(title, releaseDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return Objects.equals(title, other.title) && releaseDate.equals(other.releaseDate);
	}


	@Transient
	public String getImagePath() {
		if (this.image == null || id == null) return null;

		return "/files/movieFiles/image" + id + "/" + this.image.getName();
	}


	@Transient
	public String getScenePath(Long id) {
		if (this.image == null || id == null) return null;

		return "/files/movieFiles/scenes" + id + "/" + this.scenes;
	}
}


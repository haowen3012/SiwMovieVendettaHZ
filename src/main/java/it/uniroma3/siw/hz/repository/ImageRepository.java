package it.uniroma3.siw.hz.repository;

import it.uniroma3.siw.hz.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository  extends CrudRepository<Image, Long> {
}

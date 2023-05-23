package it.uniroma3.siw.hz.service;

import it.uniroma3.siw.hz.FileUploadUtil;
import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;



    @Transactional
    public Artist getArtist(Long id){

        return this.artistRepository.findById(id).get();
    }

    @Transactional
    public Collection<Artist> getAllArtists(){
        return (Collection<Artist>) this.artistRepository.findAll();


    }

    @Transactional
    public Collection<Artist> getActorsNotInMovie(Long movieId){

        return (Collection<Artist>) this.artistRepository.findActorsNotInMovie(movieId);
    }


    @Transactional
    public Artist saveArtist(Artist artist){
        return this.artistRepository.save(artist);
    }


    @Transactional
    public Artist addArtistPhoto(MultipartFile multipartFile, Artist artist) throws IOException {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        artist.setPicture(fileName);

        this.saveArtist(artist);

        String uploadDir = "files/artistFiles/" + artist.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return artist;
    }
}

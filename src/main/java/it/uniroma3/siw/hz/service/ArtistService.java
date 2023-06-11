package it.uniroma3.siw.hz.service;

import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Image;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import it.uniroma3.siw.hz.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ImageRepository imageRepository;



    @Transactional
    public Artist getArtist(Long id){

        return this.artistRepository.findById(id).get();
    }

    @Transactional
    public Collection<Artist> getAllArtists(){
        return (Collection<Artist>) this.artistRepository.findAll();


    }

    @Transactional
    public Collection<Artist> getArtistsNotDirectingMovie(Movie movie){

        return this.artistRepository.findArtistByDirectedMoviesNotContaining(movie);
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

        if(!multipartFile.isEmpty()) {

            artist.setPicture(imageRepository.save(new Image(multipartFile.getName(), multipartFile.getBytes())));

            this.saveArtist(artist);
        }

        return artist;
    }

    @Transactional
    public Artist updateArtist(Long idOldArtist, Artist newArtist, MultipartFile multipartFile) throws IOException{

        Artist oldArtist = this.getArtist(idOldArtist);

        if(!multipartFile.isEmpty()){


            Image picture = oldArtist.getPicture();

            if(picture==null){

                oldArtist.setPicture(new Image(multipartFile.getOriginalFilename(), multipartFile.getBytes()));

            }
              else{

                 Image oldPicture = oldArtist.getPicture();

                 oldPicture.setName(multipartFile.getOriginalFilename());


                     oldPicture.setBytes(multipartFile.getBytes());
            }




        }

        BeanUtils.copyProperties(newArtist, oldArtist, new String[]{"id","starredMovies","directedMovies","picture"});

        this.artistRepository.save(oldArtist);

        return oldArtist;
    }


    @Transactional
    public void deleteArtist(Long idArtist){


        Artist artist = this.getArtist(idArtist);


        artist.getStarredMovies().forEach(movie -> movie.getActors().remove(artist));
        artist.getStarredMovies().clear();

        artist.getDirectedMovies().forEach(movie -> movie.setDirector(null));
        artist.getDirectedMovies().clear();

        artistRepository.delete(artist);
    }
}

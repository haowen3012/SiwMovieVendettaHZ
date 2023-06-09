package it.uniroma3.siw.hz.controller;

import it.uniroma3.siw.hz.FileUploadWrapper;
import it.uniroma3.siw.hz.controller.validator.ArtistValidator;
import it.uniroma3.siw.hz.controller.validator.MultipartFileValidator;
import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import it.uniroma3.siw.hz.service.ArtistService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
public class ArtistController {
	
	@Autowired 
	private ArtistService artistService;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private MultipartFileValidator multipartFileValidator;

	@Autowired
	private ArtistValidator artistValidator;

	@GetMapping(value="/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewArtist.html";
	}

	/*
	@GetMapping(value="/admin/indexArtist")
	public String indexArtist() {
		return "admin/indexArtist.html";
	}
*/

	
	@PostMapping("/admin/artist")
	public String newArtist(@Valid @ModelAttribute("artist") Artist artist,BindingResult artistBindingResult, @Valid @ModelAttribute FileUploadWrapper fileUploadWrapper
			, BindingResult fileUploadWrapperBindingResult, Model model) {

		this.artistValidator.validate(artist,artistBindingResult);
		this.multipartFileValidator.validate(fileUploadWrapper, fileUploadWrapperBindingResult);

		if (!artistBindingResult.hasErrors() && !fileUploadWrapperBindingResult.hasErrors()) {

			try {
				this.artistService.addArtistPhoto(fileUploadWrapper.getImage(), artist);
			}catch (IOException e) {
				this.artistRepository.save(artist);
			}

			model.addAttribute("artist", artist);
			return "artist.html";

		} else {

			return "admin/formNewArtist.html"; 
		}
	}

	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistRepository.findById(id).get());
		return "artist.html";
	}

	@GetMapping("/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistRepository.findAll());
		return "artists.html";
	}

/*

	@Transactional
	@RequestMapping(value={"/addArtistPhoto/{id}"}, method = RequestMethod.POST)
	public String addArtistPhoto(@RequestParam("artistPicture")  MultipartFile multipartFile,@PathVariable("id")
	Long id) throws IOException {

		Artist artist = this.artistService.getArtist(id);

		this.artistService.addArtistPhoto(multipartFile,artist);

		return "admin/indexAdmin.html";
	}


*/
	
	@RequestMapping(value={"/updateArtist/{idA}"}, method = RequestMethod.GET)
	public String updateArtist(@PathVariable("idA") Long idArtist,Model model){

		model.addAttribute("artist", this.artistService.getArtist(idArtist));
		return "admin/formUpdateArtist.html";

	}


	@RequestMapping(value={"/update/artist/{idA}"}, method = RequestMethod.POST)
	public String updateArtist(@Valid @ModelAttribute Artist newArtist,BindingResult artistBindingResult, @PathVariable("idA") Long idArtist,
							   @Valid @ModelAttribute FileUploadWrapper fileUploadWrapper, BindingResult fileUploadBindingResult, Model model,
							   RedirectAttributes redirectAttributes){

        this.artistValidator.setUpdating(true);

		this.multipartFileValidator.validate(fileUploadWrapper, fileUploadBindingResult);
		this.artistValidator.validate(newArtist,artistBindingResult);

		this.artistValidator.setUpdating(false);
		if(!fileUploadBindingResult.hasErrors() && !artistBindingResult.hasErrors()) {

			try {
				model.addAttribute("artist", this.artistService.updateArtist(idArtist, newArtist, fileUploadWrapper.getImage()));
				return "redirect:/artist/" + idArtist;
			}catch (IOException e){

				redirectAttributes.addFlashAttribute("fileUploadError","An error occured while uploading the input file");
				return "redirect/updateMovieFields/" + idArtist;
			}
		}

          return "admin/formUpdateArtist.html";
	}


	@RequestMapping(value="/deleteArtist/{idA}", method = RequestMethod.GET)
	public String deleteArtist(@PathVariable("idA") Long idA,Model model,RedirectAttributes redirectAttributes){

          this.artistService.deleteArtist(idA);
		  redirectAttributes.addFlashAttribute("artistDeleted",true);

		  return "redirect:/artist";
	}
}

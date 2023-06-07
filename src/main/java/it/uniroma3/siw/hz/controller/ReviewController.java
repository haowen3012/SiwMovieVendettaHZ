package it.uniroma3.siw.hz.controller;

import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.service.ReviewService;
import org.hibernate.bytecode.internal.bytebuddy.PassThroughInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReviewController {


    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SessionData sessionData;


    @RequestMapping(value={"/userReviews"}, method = RequestMethod.GET)
    public String getUserReviews(Model model){
        User loggedUser = this.sessionData.getLoggedUser();

        model.addAttribute("reviews",this.reviewService.getUserReviews(loggedUser));

        return "reviews.html";
    }

    @RequestMapping(value="/deleteReview/{idR}", method = RequestMethod.GET)
    public String removeUserReview(Model model, @PathVariable("idR") Long idReview){
           User loggedUser = this.sessionData.getLoggedUser();

           this.reviewService.deleteReview(idReview);

           model.addAttribute("reviews",this.reviewService.getUserReviews(loggedUser));
           model.addAttribute("reviewDeleted",true);


             return "reviews.html";

    }


    @RequestMapping(value="/admin/deleteReview/{idM}/{idR}", method = RequestMethod.GET)
    public String deleteReviewByAdmin(Model model,@PathVariable("idR") Long idReview,@PathVariable("idM")Long idMovie){

        this.reviewService.deleteReview(idReview);

        return "redirect:/movie/" + idMovie;

    }

}

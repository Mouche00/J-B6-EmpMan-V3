package controllers;

import models.JobOffer;
import services.interfaces.JobOfferService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/job_offer")
public class JobOfferController extends HttpServlet {
    @Inject
    private JobOfferService jobOfferService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        jobOfferService.save(new JobOffer());
    }
}
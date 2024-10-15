package ma.yc.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.yc.entity.JobOffer;
import ma.yc.service.JobOfferService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "home", value = {"/home"})
public class HomeServlet extends HttpServlet {

    private final static String HOME = "/home";

    @Inject
    private JobOfferService jobOfferService;

    public void doGet ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals(HOME)) {
            List<JobOffer> jobOffers = jobOfferService.findAll();
            req.setAttribute("jobOffers", jobOffers);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}

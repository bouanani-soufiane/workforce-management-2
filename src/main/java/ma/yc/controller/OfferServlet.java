package ma.yc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.yc.entity.JobOffer;
import ma.yc.service.JobOfferService;
import ma.yc.util.ResponseWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "offer", value = {"/offer", "/offer/create", "/offer/edit", "/offer/store", "/offer/update", "/offer/delete"})
public class OfferServlet extends HttpServlet {

    private static final String ACTION_HOME = "/offer";
    private static final String ACTION_CREATE = "/offer/create";
    private static final String ACTION_EDIT = "/offer/edit";
    private static final String ACTION_STORE = "/offer/store";
    private static final String ACTION_UPDATE = "/offer/update";
    private static final String ACTION_DELETE = "/offer/delete";

    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private JobOfferService jobOfferService;
    @Inject
    private ResponseWriter responseWriter;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_HOME:
                listOffers(req, resp);
                break;
            case ACTION_CREATE:
                createOffer(req, resp);
                break;
            case ACTION_EDIT:
                editOffer(req, resp);
                break;
            default:
                responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_STORE:
                storeOffer(req, resp);
                break;
            case ACTION_UPDATE:
                updateOffer(req, resp);
                break;
            case ACTION_DELETE:
                deleteOffer(req, resp);
                break;
            default:
                responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    private void deleteOffer(HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing offer ID.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            JobOffer offer = jobOfferService.findById(id);
            if (offer == null) {
                responseWriter.writeResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Offer not found.");
            } else {
                jobOfferService.delete(offer);
                resp.sendRedirect(req.getContextPath() + "/offer");
            }
        } catch (NumberFormatException e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid offer ID format.");
        } catch (Exception e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the offer.");
            e.printStackTrace();
        }
    }

    private void updateOffer(HttpServletRequest req, HttpServletResponse resp) {
        // Implement the update offer logic here
    }

    private void storeOffer(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String requiredSkills = req.getParameter("requiredSkills");
            String dateEndParam = req.getParameter("dateEnd");

            if (title == null || title.isEmpty() || description == null || description.isEmpty() || requiredSkills == null || requiredSkills.isEmpty() || dateEndParam == null || dateEndParam.isEmpty()) {
                responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }

            LocalDateTime dateEnd = LocalDateTime.parse(dateEndParam);

            JobOffer offer = new JobOffer();
            offer.setTitle(title);
            offer.setDescription(description);
            offer.setRequiredSkills(requiredSkills);
            offer.setDatePublish(LocalDateTime.now());
            offer.setActive(true);
            offer.setDateEnd(dateEnd);
            jobOfferService.create(offer);
            responseWriter.writeResponse(resp, HttpServletResponse.SC_CREATED, "Offer " + offer.getTitle() + " created successfully");

            String redirectPath = req.getContextPath() + "/offer";
            resp.sendRedirect(redirectPath);
        } catch (Exception e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the offer.");
            e.printStackTrace();
        }
    }

    private void listOffers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobOffer> offers = jobOfferService.findAll();
        req.setAttribute("offers", offers);
        req.getRequestDispatcher("offer/offers.jsp").forward(req, resp);
    }

    private void editOffer(HttpServletRequest req, HttpServletResponse resp) {
        // Implement the edit offer logic here
    }

    private void createOffer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/offer/create.jsp").forward(req, resp);
    }
}
package ma.yc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ma.yc.entity.Candidature;
import ma.yc.entity.JobOffer;
import ma.yc.enums.ApplicationStatus;
import ma.yc.service.CandidatureService;
import ma.yc.service.JobOfferService;
import ma.yc.util.CloudinaryService;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(name = "candidate", value = {"/candidate", "/candidate/create", "/candidate/edit", "/candidate/store", "/candidate/update", "/candidate/delete"})
@MultipartConfig
public class CandidateServlet extends HttpServlet {

    private static final String ACTION_CREATE = "/candidate/create";
    private static final String ACTION_STORE = "/candidate/store";

    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private CandidatureService service;
    @Inject
    private JobOfferService jobOfferService;
    @Inject
    private CloudinaryService cloudinaryService;

    public void doGet ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_CREATE:
                this.createCandidate(req, resp);
                break;
            default:
                writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    private void createCandidate ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String jobOfferIdParam = req.getParameter("jobOfferId");
        req.setAttribute("jobOfferId", jobOfferIdParam);
        req.getRequestDispatcher("/candidate/create.jsp").forward(req, resp);
    }

    public void doPost ( HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_STORE:
                this.storeCandidate(req, resp);
                break;
            default:
                writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    private void storeCandidate ( HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        try {
            String jobOfferIdParam = req.getParameter("jobOfferId");
            Long jobOfferId = Long.parseLong(jobOfferIdParam);
            JobOffer jobOffer = jobOfferService.findById(jobOfferId);

            Candidature candidature = new Candidature();
            candidature.setName(req.getParameter("name"));
            candidature.setEmail(req.getParameter("email"));
            candidature.setApplicationStatus(ApplicationStatus.PENDING);
            candidature.setSubmissionDate(LocalDateTime.now());
            candidature.setJobOffer(jobOffer);
            candidature.setSkills(req.getParameter("skills"));

            String resumeUrl = uploadResume(req);
            candidature.setResume(resumeUrl);

            service.create(candidature);

            writeResponse(resp, HttpServletResponse.SC_CREATED, "Candidate with name " + candidature.getName() + " successfully applied.");

        } catch (Exception e) {
            writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the candidature.");
            e.printStackTrace();
        }
    }

    private String uploadResume ( HttpServletRequest req ) throws IOException, ServletException {
        Part resumePart = req.getPart("resume");
        if (resumePart == null || resumePart.getSize() == 0) {
            throw new IllegalArgumentException("resume part is empty");
        }
        try (InputStream inputStream = resumePart.getInputStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            return cloudinaryService.uploadFile(fileBytes);
        }
    }


    private void writeResponse ( HttpServletResponse resp, int statusCode, String message ) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(statusCode);
            out.write(objectMapper.writeValueAsString(new CandidateServlet.ResponseMessage(message)));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ResponseMessage {
        public String message;

        public ResponseMessage ( String message ) {
            this.message = message;
        }
    }

}
package ma.yc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.yc.entity.JobOffer;
import ma.yc.service.JobOfferService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "offer", value = {"/offer", "/offer/create", "/offer/edit", "/offer/store", "/offer/update", "/offer/delete"})
public class OfferServlet extends HttpServlet {

    private static final String ACTION_HOME = "/offer";
    private static final String ACTION_CREATE = "/offer/create";
    private static final String ACTION_EDIT = "/offer/edit";
    private static final String ACTION_STORE = "/offer/store";
    private static final String ACTION_UPDATE = "/offer/update";
    private static final String ACTION_DELETE = "/offer/delete";

    ObjectMapper objectMapper;
    private Weld weld;
    private JobOfferService service;

    public void init () {
        this.weld = new Weld();
        WeldContainer container = weld.initialize();
        this.service = container.select(JobOfferService.class).get();
        this.objectMapper = new ObjectMapper();
    }

    public void doGet ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_HOME:
                this.listOffers(req, resp);
                break;
            case ACTION_CREATE:
                this.createOffer(req, resp);
                break;
            case ACTION_EDIT:
                this.editOffer(req, resp);
                break;
            default:
                writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }

    }

    public void doPost ( HttpServletRequest req, HttpServletResponse resp ) {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_STORE:
                this.storeOffers(req, resp);
                break;
            case ACTION_UPDATE:
                this.updateOffer(req, resp);
                break;
            case ACTION_DELETE:
                this.deleteOffer(req, resp);
                break;


        }
    }

    private void deleteOffer ( HttpServletRequest req, HttpServletResponse resp ) {
    }

    private void updateOffer ( HttpServletRequest req, HttpServletResponse resp ) {

    }

    private void storeOffers ( HttpServletRequest req, HttpServletResponse resp ) {

    }


    private void listOffers ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        List<JobOffer> offers = this.service.findAll();
        req.setAttribute("offers", offers);
        req.getRequestDispatcher("offer/offers.jsp").forward(req, resp);
    }

    private void editOffer ( HttpServletRequest req, HttpServletResponse resp ) {

    }

    private void createOffer ( HttpServletRequest req, HttpServletResponse resp ) {

    }


    private void writeResponse ( HttpServletResponse resp, int statusCode, String message ) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(statusCode);
            out.write(objectMapper.writeValueAsString(new OfferServlet.ResponseMessage(message)));
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

    @Override
    public void destroy () {
        if (weld != null) {
            weld.shutdown();
        }
    }
}

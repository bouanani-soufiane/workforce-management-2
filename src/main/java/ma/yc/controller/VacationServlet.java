package ma.yc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.yc.entity.Employee;
import ma.yc.entity.Vacation;
import ma.yc.enums.VacationStatus;
import ma.yc.service.EmployeeService;
import ma.yc.service.VacationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "vacation", value = {"/vacation", "/vacation/create", "/vacation/accept", "/vacation/store", "/vacation/reject", "/vacation/delete"})
public class VacationServlet extends HttpServlet {

    private static final String ACTION_HOME = "/vacation";
    private static final String ACTION_CREATE = "/vacation/create";
    private static final String ACTION_STORE = "/vacation/store";
    private static final String ACTION_REJECT = "/vacation/reject";
    private static final String ACTION_ACCEPT = "/vacation/accept";

    @Inject
    ObjectMapper objectMapper;
    @Inject
    private VacationService service;
    @Inject
    private EmployeeService employeeService;

    public void doGet ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_HOME:
                this.listVacation(req, resp);
                break;
            case ACTION_CREATE:
                this.createVacation(req, resp);
                break;
            default:
                writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }

    }

    private void createVacation ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        req.getRequestDispatcher("/vacation/create.jsp").forward(req, resp);
    }

    private void listVacation ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        List<Vacation> vacations = this.service.findAll();
        req.setAttribute("vacations", vacations);
        req.getRequestDispatcher("vacation/vacations.jsp").forward(req, resp);
    }

    public void doPost ( HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_STORE:
                this.storeVacation(req, resp);
                break;
            case ACTION_REJECT:
                this.rejectVacation(req, resp);
                break;
            case ACTION_ACCEPT:
                this.acceptVacation(req, resp);
                break;
            default:
                writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    private void acceptVacation ( HttpServletRequest req, HttpServletResponse resp ) {

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing vacation ID.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            Vacation vacation = service.findById(id);
            if (vacation == null) {
                writeResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Vacation not found.");
            } else {
                service.setStatusAccepted(vacation);
                resp.sendRedirect(req.getContextPath() + "/vacation");
            }
        } catch (NumberFormatException e) {
            writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid vacation ID format.");
        } catch (Exception e) {
            writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the vacation.");
            e.printStackTrace();
        }


    }

    private void rejectVacation ( HttpServletRequest req, HttpServletResponse resp ) {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing vacation ID.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            Vacation vacation = service.findById(id);
            if (vacation == null) {
                writeResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Vacation not found.");
            } else {
                service.setStatusRejected(vacation);
                resp.sendRedirect(req.getContextPath() + "/vacation");
            }
        } catch (NumberFormatException e) {
            writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid vacation ID format.");
        } catch (Exception e) {
            writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the vacation.");
            e.printStackTrace();
        }
    }

    private void storeVacation ( HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        try {
            String dateStartParam = req.getParameter("dateStart");
            String dateEndParam = req.getParameter("dateEnd");
            String reason = req.getParameter("reason");

            if (dateStartParam == null || dateEndParam == null || reason == null || reason.isEmpty()) {
                writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing required fields: dateStart, dateEnd, or reason.");
                return;
            }

            Employee employee = employeeService.findById(52L);
            LocalDate startDate;
            LocalDate endDate;
            startDate = LocalDate.parse(dateStartParam);
            endDate = LocalDate.parse(dateEndParam);
            Vacation vacation = new Vacation();
            vacation.setStartDate(startDate);
            vacation.setEndDate(endDate);
            vacation.setReason(reason);
            vacation.setEmployee(employee);
            vacation.setVacationStatus(VacationStatus.PENDING);

            service.create(vacation);

            String redirectPath = req.getContextPath() + "/vacation";
            writeResponse(resp, HttpServletResponse.SC_CREATED, "Vacation " + vacation.getId() + " added successfully");
            resp.sendRedirect(redirectPath);
        } catch (Exception e) {

            e.printStackTrace();
            writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while saving the vacation.");
        }
    }


    private void writeResponse ( HttpServletResponse resp, int statusCode, String message ) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(statusCode);
            out.write(objectMapper.writeValueAsString(new VacationServlet.ResponseMessage(message)));
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

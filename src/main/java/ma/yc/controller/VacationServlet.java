package ma.yc.controller;

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
import ma.yc.util.ResponseWriter;

import java.io.IOException;
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
    private VacationService vacationService;
    @Inject
    private EmployeeService employeeService;
    @Inject
    private ResponseWriter responseWriter;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_HOME:
                listVacations(req, resp);
                break;
            case ACTION_CREATE:
                createVacation(req, resp);
                break;
            default:
                responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_STORE:
                storeVacation(req, resp);
                break;
            case ACTION_REJECT:
                rejectVacation(req, resp);
                break;
            case ACTION_ACCEPT:
                acceptVacation(req, resp);
                break;
            default:
                responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    private void acceptVacation(HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing vacation ID.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            Vacation vacation = vacationService.findById(id);
            if (vacation == null) {
                responseWriter.writeResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Vacation not found.");
            } else {
                vacationService.setStatusAccepted(vacation);
                resp.sendRedirect(req.getContextPath() + "/vacation");
            }
        } catch (NumberFormatException e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid vacation ID format.");
        } catch (Exception e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the vacation.");
            e.printStackTrace();
        }
    }

    private void rejectVacation(HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing vacation ID.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            Vacation vacation = vacationService.findById(id);
            if (vacation == null) {
                responseWriter.writeResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Vacation not found.");
            } else {
                vacationService.setStatusRejected(vacation);
                resp.sendRedirect(req.getContextPath() + "/vacation");
            }
        } catch (NumberFormatException e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid vacation ID format.");
        } catch (Exception e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the vacation.");
            e.printStackTrace();
        }
    }

    private void storeVacation(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String dateStartParam = req.getParameter("dateStart");
            String dateEndParam = req.getParameter("dateEnd");
            String reason = req.getParameter("reason");

            if (dateStartParam == null || dateEndParam == null || reason == null || reason.isEmpty()) {
                responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing required fields: dateStart, dateEnd, or reason.");
                return;
            }

            Employee employee = employeeService.findById(52L);
            LocalDate startDate = LocalDate.parse(dateStartParam);
            LocalDate endDate = LocalDate.parse(dateEndParam);
            Vacation vacation = new Vacation();
            vacation.setStartDate(startDate);
            vacation.setEndDate(endDate);
            vacation.setReason(reason);
            vacation.setEmployee(employee);
            vacation.setVacationStatus(VacationStatus.PENDING);

            vacationService.create(vacation);

            String redirectPath = req.getContextPath() + "/vacation";
            responseWriter.writeResponse(resp, HttpServletResponse.SC_CREATED, "Vacation " + vacation.getId() + " added successfully");
            resp.sendRedirect(redirectPath);
        } catch (Exception e) {
            e.printStackTrace();
            responseWriter.writeResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while saving the vacation.");
        }
    }

    private void createVacation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/vacation/create.jsp").forward(req, resp);
    }

    private void listVacations(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Vacation> vacations = vacationService.findAll();
        req.setAttribute("vacations", vacations);
        req.getRequestDispatcher("vacation/vacations.jsp").forward(req, resp);
    }
}
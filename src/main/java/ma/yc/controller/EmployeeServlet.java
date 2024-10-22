package ma.yc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.yc.entity.Employee;
import ma.yc.entity.FamillyAllowance;
import ma.yc.entity.valueObject.SocialSecurityNumber;
import ma.yc.service.EmployeeService;
import ma.yc.util.ResponseWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@WebServlet(name = "em", value = {"/", "/employee/create", "/employee/edit", "/employee/store", "/employee/update", "/employee/delete", "/employee/search", "/employee/filter"})
public class EmployeeServlet extends HttpServlet {

    private static final String ACTION_HOME = "/";
    private static final String ACTION_CREATE = "/employee/create";
    private static final String ACTION_EDIT = "/employee/edit";
    private static final String ACTION_STORE = "/employee/store";
    private static final String ACTION_UPDATE = "/employee/update";
    private static final String ACTION_DELETE = "/employee/delete";
    private static final String ACTION_SEARCH = "/employee/search";
    private static final String ACTION_FILTER = "/employee/filter";

    @Inject
    private EmployeeService employeeService;
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private ResponseWriter responseWriter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_HOME:
                employeeList(req, res);
                break;
            case ACTION_CREATE:
                req.getRequestDispatcher("/employee/create.jsp").forward(req, res);
                break;
            case ACTION_EDIT:
                editEmployee(req, res);
                break;
            default:
                responseWriter.writeResponse(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json");
        final String action = req.getServletPath();
        switch (action) {
            case ACTION_STORE:
                storeEmployee(req, res);
                break;
            case ACTION_UPDATE:
                updateEmployee(req, res);
                break;
            case ACTION_SEARCH:
                searchEmployee(req, res);
                break;
            case ACTION_FILTER:
                filterByDepartment(req, res);
                break;
            case ACTION_DELETE:
                deleteEmployee(req, res);
                break;
            default:
                responseWriter.writeResponse(res, HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                break;
        }
    }

    private void filterByDepartment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        String[] selectedDepartments = getSelectedDepartments(req);
        List<Employee> filteredEmployees = getFilteredEmployees(selectedDepartments);
        String jsonResponse = objectMapper.writeValueAsString(filteredEmployees);
        responseWriter.writeResponse(res, HttpServletResponse.SC_OK, jsonResponse);
    }

    private String[] getSelectedDepartments(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String jsonString = sb.toString();
        return objectMapper.readValue(jsonString, String[].class);
    }

    private List<Employee> getFilteredEmployees(String[] selectedDepartments) {
        if (selectedDepartments != null && selectedDepartments.length > 0) {
            return employeeService.filterByDepartment(selectedDepartments);
        } else {
            return employeeService.findAll();
        }
    }

    private void searchEmployee(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        String searchTerm = getSearchTerm(req);
        if (searchTerm == null || searchTerm.isEmpty()) {
            responseWriter.writeResponse(res, HttpServletResponse.SC_BAD_REQUEST, "Search query is missing");
        } else {
            List<Employee> employees = employeeService.search(searchTerm);
            if (employees.isEmpty()) {
                responseWriter.writeResponse(res, HttpServletResponse.SC_NOT_FOUND, "No employees found for search query: " + searchTerm);
            } else {
                String jsonResponse = objectMapper.writeValueAsString(employees);
                responseWriter.writeResponse(res, HttpServletResponse.SC_OK, jsonResponse);
            }
        }
    }

    private String getSearchTerm(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String jsonString = sb.toString();
        Map<String, String> jsonMap = objectMapper.readValue(jsonString, Map.class);
        return jsonMap.get("searchTerm");
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        Long id = Long.parseLong(idParam);
        Employee employee = employeeService.findById(id);
        boolean deleted = employeeService.delete(employee);
        if (deleted) {
            resp.sendRedirect("/workforce");
        } else {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Employee not found");
        }
    }

    private void updateEmployee(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Employee employee = employeeService.findById(id);
            if (employee == null) {
                responseWriter.writeResponse(res, HttpServletResponse.SC_NOT_FOUND, "Employee not found");
                return;
            }
            updateEmployeeDetails(req, employee);
            employeeService.update(employee);
            responseWriter.writeResponse(res, HttpServletResponse.SC_OK, "Employee updated successfully");
        } catch (Exception e) {
            responseWriter.writeResponse(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateEmployeeDetails(HttpServletRequest req, Employee employee) {
        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        employee.setPhone(req.getParameter("phone"));
        employee.setDepartement(req.getParameter("department"));
        employee.setPassword("1234");
        employee.setAddress(req.getParameter("address"));
        employee.setJobTitle(req.getParameter("jobTitle"));
        employee.setBirthDate(LocalDate.parse(req.getParameter("birthDate")));
        employee.setSecurityNumber(new SocialSecurityNumber(req.getParameter("securityNumber")));
        updateFamilyAllowance(req, employee);
    }

    private void updateFamilyAllowance(HttpServletRequest req, Employee employee) {
        FamillyAllowance allowance = new FamillyAllowance();
        allowance.setSalary(Double.parseDouble(req.getParameter("salary")));
        allowance.setChildrenCount(Integer.parseInt(req.getParameter("childrenCount")));
        employee.setFamillyAllowance(allowance);
    }

    private void employeeList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Employee> employees = employeeService.findAll();
        req.setAttribute("employeeList", employees);
        req.getRequestDispatcher("admin/dashboard.jsp").forward(req, res);
    }

    private void editEmployee(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Long id = Long.parseLong(idParam);
        req.setAttribute("employee", employeeService.findById(id));
        req.getRequestDispatcher("/employee/edit.jsp").forward(req, res);
    }

    private void storeEmployee(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Employee employee = createEmployee(req);
            employeeService.create(employee);
            responseWriter.writeResponse(resp, HttpServletResponse.SC_CREATED, "Employee " + employee.getName() + " created successfully");
        } catch (Exception e) {
            responseWriter.writeResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Error creating the employee: " + e.getMessage());
        }
    }

    private Employee createEmployee(HttpServletRequest req) {
        Employee employee = new Employee();
        employee.setName(req.getParameter("name"));
        employee.setEmail(req.getParameter("email"));
        employee.setPhone(req.getParameter("phone"));
        employee.setDepartement(req.getParameter("department"));
        employee.setPassword("1234");
        employee.setAddress(req.getParameter("address"));
        employee.setJobTitle(req.getParameter("jobTitle"));
        employee.setBirthDate(LocalDate.parse(req.getParameter("birthDate")));
        employee.setSecurityNumber(new SocialSecurityNumber(req.getParameter("securityNumber")));
        employee.setHireDate(LocalDate.now());
        employee.setSoldVacation(18);
        employee.setRole(false);
        employee.setFamillyAllowance(createFamilyAllowance(req));
        return employee;
    }

    private FamillyAllowance createFamilyAllowance(HttpServletRequest req) {
        FamillyAllowance allowance = new FamillyAllowance();
        allowance.setSalary(Double.parseDouble(req.getParameter("salary")));
        allowance.setChildrenCount(Integer.parseInt(req.getParameter("childrenCount")));
        return allowance;
    }
}
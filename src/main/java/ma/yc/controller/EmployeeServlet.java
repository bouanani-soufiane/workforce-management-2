package ma.yc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "em" , value = {"/" ,"/list","/create","/edit","/store","/update"})
public class EmployeeServlet extends HttpServlet {

    public void doGet( HttpServletRequest request , HttpServletResponse response ) throws IOException, ServletException {
        final String action = request.getServletPath();

        switch(action){
            case "/" :
                request.setAttribute("name" , "soufiane");
                request.getRequestDispatcher("index.jsp").forward(request,response);

        }
    }
}

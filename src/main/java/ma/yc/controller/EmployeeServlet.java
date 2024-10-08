package ma.yc.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "em" , urlPatterns = {"/","/list","/create","edit","store","update"})
public class EmployeeServlet extends HttpServlet {

    public void doGet( HttpServletRequest request , HttpServletResponse response ){
        final String action = request.getServletPath();

        switch(action){
            case "/" :

        }
    }
}

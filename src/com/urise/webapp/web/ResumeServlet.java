package com.urise.webapp.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes" : "Hello " + name + '!');
    }
}
//<table style="width:100%">
//<tr>
//<th>Firstname</th>
//<th>Lastname</th>
//<th>Age</th>
//</tr>
//<tr>
//<td>Jill</td>
//<td>Smith</td>
//<td>50</td>
//</tr>
//<tr>
//<td>Eve</td>
//<td>Jackson</td>
//<td>94</td>
//</tr>
//</table>
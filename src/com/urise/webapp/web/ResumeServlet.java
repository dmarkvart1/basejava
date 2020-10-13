package com.urise.webapp.web;

import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setContentType("text/html; charset=UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes" : "Hello " + name + '!');
        request.setAttribute("resumes", sqlStorage.getAllSorted());
        request.getRequestDispatcher("myindex.jsp").forward(request, response);
    }
}
package org.grigorovich.test_web_app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.grigorovich.test_web_app.model.Cat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cats")
@Slf4j
public class CatController extends HttpServlet {

    private Cat cat;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();

        cat = new Cat("Barsik", 12);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // super.service(request,response);
        response.setContentType("text/html");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("<h2>Name: " + cat.getName() + "; Age: " + cat.getAge() + "</h2>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("destroy метод в CatController");
    }

}

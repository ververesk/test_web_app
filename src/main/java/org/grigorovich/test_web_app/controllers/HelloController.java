package org.grigorovich.test_web_app.controllers;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
//Перед определением класса указана аннотация WebServlet, которая указывает,
// с какой конечной точкой будет сопоставляться данный сервлет.
// То есть данный сервлет будет обрабатывать запросы по адресу /hello
@Slf4j
public class HelloController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Hello World Servlet</title></head>");
        out.println("<body>");
        out.println("<h1>Hello World!</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("destroy метод в HelloController");
    }
}


package org.grigorovich.test_web_app.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exceptions")
//Перед определением класса указана аннотация WebServlet, которая указывает,
// с какой конечной точкой будет сопоставляться данный сервлет.
// То есть данный сервлет будет обрабатывать запросы по адресу /hello
public class ExceptionController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int x = 0;
        int y = 8 / x;
    }
}


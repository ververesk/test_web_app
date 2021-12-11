package org.grigorovich.test_web_app.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommonJsonController extends HttpServlet {

    protected void writeEntityToBody(Object obj, HttpServletResponse resp) throws IOException {
        obj = (obj instanceof Optional) ? ((Optional<?>) obj).orElse(null) : obj;
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(obj);
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }

    protected <T> T toEntity(Class<T> clazz, HttpServletRequest req) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();
        T entity = mapper.readValue(body, clazz);
        return entity;
    }
}


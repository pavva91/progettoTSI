package com.valerio.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by valer on 27/11/2017.
 */
@WebServlet(name = "BackgroundServlet")
public class BackgroundServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = "";
        HttpSession session = request.getSession();
        String paramValue= request.getParameter("action");
        if (paramValue.equals("Start")){ // aggiungi attributo dalla sessione

            session.setAttribute("MySessionVariable", "start");
            text = "Started";
        }
        if (paramValue.equals("Stop")){ // rimuovi attributo dalla sessione
            session.removeAttribute("MySessionVariable");
            text = "Stopped";
        }


        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(text);       // Write response body.
    }
}

package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Carta;
import modelsDAO.CartaDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PedirCartaServlet", urlPatterns = "/pedirCarta")
public class PedirCartaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //  if (req.getSession().getAttribute("usuario") != null) {
            Carta cartaAleatoria = CartaDAO.abrirCarta();
            Gson gson = new Gson();
            String json = gson.toJson(cartaAleatoria);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
       // } else {}
          //  resp.sendRedirect("./plantillas/login.html");
    }
}

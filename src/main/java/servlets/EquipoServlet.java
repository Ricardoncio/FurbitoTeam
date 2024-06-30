package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Equipo;
import modelsDAO.EquipoDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EquipoServlet", urlPatterns = "/equipo")
public class EquipoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUsuario = Integer.parseInt(req.getParameter("idUser"));
        Equipo equipo = EquipoDAO.recuperarEquipo(idUsuario);

        if (equipo != null) {
            String respuesta = new Gson().toJson(equipo);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(respuesta);
            out.flush();
        } else {
            resp.sendError(400);
        }
    }
}
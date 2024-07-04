package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelsDAO.UsuarioDAO;

import java.io.IOException;

@WebServlet(name = "BorrarEquipoServlet", urlPatterns = "/borrarEquipo")
public class BorrarEquipoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUser = 0;
        if (req.getParameter("idUser") != null && !req.getParameter("idUser").matches("null"))
            idUser = Integer.parseInt(req.getParameter("idUser"));
        if (idUser != 0)
            UsuarioDAO.borrarEquipo(idUser);
    }
}

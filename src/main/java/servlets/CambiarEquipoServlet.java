package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelsDAO.EquipoDAO;

import java.io.IOException;

@WebServlet(name = "CambiarEquipoServlet",urlPatterns = "/cambiarEquipo")
public class CambiarEquipoServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idUsuario = Integer.parseInt(req.getParameter("idUser"));
        int idCarta = Integer.parseInt(req.getParameter("idCarta"));
        String pos = req.getParameter("pos");

        int status = EquipoDAO.cambiarJugadores(pos,idUsuario,idCarta);
        if (status == 400) {
            resp.sendError(status);
        }
    }
}

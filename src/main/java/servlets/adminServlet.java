package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelsDAO.RankingDAO;
import modelsDAO.UsuarioDAO;
import util.Conector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(name = "AdminServlet", urlPatterns = "/admin")
public class adminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        // La conexión se cierra dentro del método partePuntos
        Connection con = new Conector().getMYSQLConnection();
        if (con != null) {
            String tiradas = req.getParameter("nuevasTiradas");
            if (tiradas != null && !tiradas.isEmpty()) {
                UsuarioDAO.parteTiradas(req, con);
            }
            if (req.getParameter("borrarPuntos") != null
                    && req.getParameter("borrarPuntos").equals("on")){
                RankingDAO.resetRanking();
            }
            UsuarioDAO.partePuntos(req, con);
        }

        try {
            resp.sendRedirect("./plantillas/admin.html");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}

package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Carta;
import modelsDAO.CartaDAO;
import modelsDAO.RankingDAO;
import modelsDAO.UsuarioDAO;
import util.Conector;

import java.io.IOException;
import java.sql.Connection;

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
            if (!req.getParameter("linkCarta").isEmpty() && !req.getParameter("tierCarta").isEmpty()
                    && !req.getParameter("pacCarta").isEmpty() && !req.getParameter("shoCarta").isEmpty()
                    && !req.getParameter("pasCarta").isEmpty() && !req.getParameter("driCarta").isEmpty()
                    && !req.getParameter("defCarta").isEmpty() && !req.getParameter("phyCarta").isEmpty()) {
                Carta carta = new Carta();
                carta.setImageLink(req.getParameter("linkCarta"));
                carta.setTier(Integer.parseInt(req.getParameter("tierCarta")));
                carta.setPac(Integer.parseInt(req.getParameter("pacCarta")));
                carta.setSho(Integer.parseInt(req.getParameter("shoCarta")));
                carta.setPas(Integer.parseInt(req.getParameter("pasCarta")));
                carta.setDri(Integer.parseInt(req.getParameter("driCarta")));
                carta.setDef(Integer.parseInt(req.getParameter("defCarta")));
                carta.setPhy(Integer.parseInt(req.getParameter("phyCarta")));
                carta.calcularMedia();
                CartaDAO.crearCarta(carta);
            }
        }

        try {
            resp.sendRedirect("./plantillas/admin.html");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}

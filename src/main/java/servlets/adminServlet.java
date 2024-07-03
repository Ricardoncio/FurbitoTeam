package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelsDAO.UsuarioDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminServlet", urlPatterns = "/admin")
public class adminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int nuevasTiradas = Integer.parseInt(req.getParameter("nuevasTiradas"));
        List<Integer> ids = new ArrayList<>();
        if (req.getParameter("pablo") != null && req.getParameter("pablo").matches("on"))
            ids.add(1);
        if (req.getParameter("daneti") != null && req.getParameter("daneti").matches("on"))
            ids.add(2);
        if (req.getParameter("talleres") != null && req.getParameter("talleres").matches("on"))
            ids.add(3);
        if (req.getParameter("pukaso") != null && req.getParameter("pukaso").matches("on"))
            ids.add(4);
        if (req.getParameter("price") != null && req.getParameter("price").matches("on"))
            ids.add(5);
        if (req.getParameter("yayo") != null && req.getParameter("yayo").matches("on"))
            ids.add(6);

        if (nuevasTiradas > 0 && !ids.isEmpty())
            UsuarioDAO.sumarTirada(nuevasTiradas, ids);

        resp.sendRedirect("./plantillas/admin.html");
    }
}

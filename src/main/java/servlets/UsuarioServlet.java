package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Usuario;
import modelsDAO.UsuarioDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UsuarioServlet", urlPatterns = "/usuario")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUser = Integer.parseInt(req.getParameter("idUser"));
        Usuario usuario = null;
        if (idUser != 0)
            usuario = UsuarioDAO.recuperarUsuario(idUser);

        String respuesta = new Gson().toJson(usuario);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(respuesta);
        out.flush();
    }
}
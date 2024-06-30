package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Usuario;
import modelsDAO.UsuarioDAO;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombreUsuario = req.getParameter("nombreUsuario");
        String pass = req.getParameter("pass");
        int idUsuario = UsuarioDAO.credencialesOK(nombreUsuario, pass);

    }
}

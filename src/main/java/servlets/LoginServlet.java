package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Usuario;
import modelsDAO.UsuarioDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombreUsuario = req.getParameter("nombreUsuario");
        String pass = req.getParameter("pass");
        Usuario usuario = UsuarioDAO.credencialesOK(nombreUsuario, pass);
        String usuarioJSON = new Gson().toJson(usuario);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(usuarioJSON);
        out.close();
    }
}

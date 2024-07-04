package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Ranking;
import modelsDAO.RankingDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RankingServlet", urlPatterns = "/ranking")
public class RankingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Ranking ranking = RankingDAO.recuperarRanking();

        String json = new Gson().toJson(ranking);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.close();
    }
}

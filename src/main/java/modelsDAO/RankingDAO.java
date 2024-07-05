package modelsDAO;

import models.Ranking;
import util.Conector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RankingDAO {

    public static Ranking recuperarRanking() {
        Ranking ranking = new Ranking();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM rankingversion ORDER BY puntos DESC");
            ResultSet rs = ps.executeQuery();
            int contador = 0;
            while (rs.next()) {
                String[] jugador = {rs.getString(1), rs.getString(2)};
                switch (contador) {
                    case 0 -> ranking.setPrimero(jugador);
                    case 1 -> ranking.setSegundo(jugador);
                    case 2 -> ranking.setTercero(jugador);
                    case 3 -> ranking.setCuarto(jugador);
                    case 4 -> ranking.setQuinto(jugador);
                    case 5 -> ranking.setSexto(jugador);
                }
                contador++;
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return ranking;
    }

    public static void resetRanking() {
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE rankingVersion SET puntos = 0 WHERE 1 = 1;");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }
}

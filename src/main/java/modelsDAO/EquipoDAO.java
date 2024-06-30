package modelsDAO;

import models.Carta;
import models.Equipo;
import util.Conector;
import util.FurbitoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EquipoDAO {

    public static Equipo recuperarEquipo(int idUsuario) {
        Equipo equipo = null;
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM equipo AS e INNER JOIN cartas AS c on e.id_carta = c.id WHERE id_usuario = " + idUsuario);
            while (resultSet.next()) {
                Carta carta = new Carta();
                carta.setId(resultSet.getInt("e.id_carta"));
                carta.setImageLink(resultSet.getString("c.imageLink"));
                carta.setTier(resultSet.getInt("tier"));
                carta.setAlt(resultSet.getString("alt"));
                switch (resultSet.getString("pos")) {
                    case "por" -> equipo.setPor(carta);
                    case "defd" -> equipo.setDefd(carta);
                    case "defi" -> equipo.setDefi(carta);
                    case "deld" -> equipo.setDeld(carta);
                    case "deli" -> equipo.setDeli(carta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return equipo;
    }

    public static int cambiarJugadores(String pos, int idUsuario, int idCarta) {
        int status = 200;
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();
            int i = statement.executeUpdate("UPDATE equipo SET id_carta = '" + idCarta + "' WHERE pos = '" + pos + "'");
            if (i == 0) {
                throw new FurbitoException("No se ha cambiado ninguna carta");
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        } catch (FurbitoException e) {
            status = 400;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return status;
    }
}

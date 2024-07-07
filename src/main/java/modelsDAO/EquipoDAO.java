package modelsDAO;

import models.Carta;
import models.Equipo;
import util.Conector;
import util.FurbitoException;

import java.sql.*;

public class EquipoDAO {

    public static Equipo recuperarEquipo(int idUsuario) {
        Equipo equipo = new Equipo();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT imageLink,e.id_carta,pos,tier,estilo FROM equipo AS e INNER JOIN poolDeCartas AS p on e.id_carta = p.id_unico INNER JOIN modelosCartas as m on p.id_modelo = m.id WHERE e.id_usuario = " + idUsuario);
            while (resultSet.next()) {
                Carta carta = new Carta();
                carta.setId(resultSet.getInt("id_carta"));
                carta.setImageLink(resultSet.getString("imageLink"));
                carta.setTier(resultSet.getInt("tier"));
                carta.setEstilo(resultSet.getString("estilo"));
                switch (resultSet.getString("pos")) {
                    case "por" -> equipo.setPor(carta);
                    case "defd" -> equipo.setDefd(carta);
                    case "defi" -> equipo.setDefi(carta);
                    case "deld" -> equipo.setDeld(carta);
                    case "deli" -> equipo.setDeli(carta);
                }
            }

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

        return equipo;
    }

    public static int cambiarJugadores(String pos, int idUsuario, int idCarta) {
        int status = 200;
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE equipo SET id_carta = ? WHERE pos = ? AND id_usuario = ?");
            if (idCarta == 0)
                statement.setObject(1, null);
            else
                statement.setInt(1, idCarta);
            statement.setString(2, pos);
            statement.setInt(3, idUsuario);
            int i = statement.executeUpdate();
            if (i == 0) {
                throw new FurbitoException("No se ha cambiado ninguna carta");
            }

        } catch (SQLException  e) {
            e.printStackTrace(System.out);
        } catch (FurbitoException e) {
            status = 400;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
            }
        }

        return status;
    }

    public static Equipo recuperarEquipoPartido(int idUsuario) {
        Equipo equipo = new Equipo();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * " +
                            "FROM equipo AS e INNER JOIN poolDeCartas AS p ON e.id_carta = p.id_unico INNER JOIN modelosCartas AS m ON p.id_modelo = m.id " +
                            "WHERE e.id_usuario = " + idUsuario);
            while (rs.next()) {
                Carta carta = new Carta();
                carta.setId(rs.getInt("id_carta"));
                carta.setImageLink(rs.getString("imageLink"));
                carta.setEstilo(rs.getString("estilo"));
                carta.setMedia(rs.getInt("PAC"));
                carta.setMedia(rs.getInt("SHO"));
                carta.setMedia(rs.getInt("PAS"));
                carta.setMedia(rs.getInt("DRI"));
                carta.setMedia(rs.getInt("DEF"));
                carta.setMedia(rs.getInt("PHY"));
                switch (rs.getString("pos")) {
                    case "por" -> equipo.setPor(carta);
                    case "defd" -> equipo.setDefd(carta);
                    case "defi" -> equipo.setDefi(carta);
                    case "deld" -> equipo.setDeld(carta);
                    case "deli" -> equipo.setDeli(carta);
                }
            }

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

        return equipo;
    }
}

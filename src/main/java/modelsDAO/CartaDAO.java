package modelsDAO;

import models.Carta;
import util.Conector;

import java.sql.*;
import java.util.*;

public class CartaDAO {

    public static List<Carta> recuperarCartas(Connection con) throws SQLException {
        List<Carta> cartasArr = new ArrayList<>();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM cartas");

        while (rs.next()) {
            Carta carta = new Carta();
            carta.setImageLink(rs.getString("imageLink"));
            carta.setTier(rs.getInt("tier"));
            carta.setAlt(rs.getString("alt"));
            cartasArr.add(carta);
        }

        return cartasArr;
    }

    private static void guardarCartaEnColeccion(Connection con, Carta carta, int idUsuario) throws SQLException {
        Carta nuevaCarta = new Carta();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM poolDeCartas WHERE tier = ? AND imageLink = ? ORDER BY id_unico DESC LIMIT 1");
        ps.setInt(1,carta.getTier());
        ps.setString(2, carta.getImageLink());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nuevaCarta.setId(rs.getInt("id_unico"));
            nuevaCarta.setImageLink(rs.getString("imageLink"));
            nuevaCarta.setTier(rs.getInt("tier"));
            nuevaCarta.setAlt(rs.getString("alt"));
        }

        ps = con.prepareStatement("INSERT INTO inventario VALUES (?,?)");
        ps.setInt(1,idUsuario);
        ps.setInt(2,nuevaCarta.getId());
        ps.executeUpdate();
    }

    public static void guardarCartaEnPool(Connection con, Carta carta) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO poolDeCartas (imageLink,tier,alt) VALUES (?,?,?)");
        ps.setString(1,carta.getImageLink());
        ps.setInt(2,carta.getTier());
        ps.setString(3,carta.getAlt());
        ps.executeUpdate();
    }

    public static void restarTirada(Connection con, int idUsuario) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("UPDATE usuarios SET tiradas = tiradas - 1 WHERE id = " + idUsuario);
    }

    public static Carta abrirCarta(int idUsuario) {
        Carta cartaElegida = null;
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            List<Carta> cartasArr = recuperarCartas(con);

            double rng = Math.random() * 100 + 1;
            int tier;
            if (rng <= 3)
                // Aqui deberia ser 5 pero como no hay cartas especiales aun no quiero que me de fallos a la hora de testear
                tier = 4;
            else if (rng <= 10)
                tier = 4;
            else if (rng <= 25)
                tier = 3;
            else if (rng <= 62.5)
                tier = 2;
            else
                tier = 1;

            List<Carta> cartasArrPorTier = cartasArr.stream().filter(carta -> carta.getTier() == tier).toList();
            int i = new Random().nextInt(cartasArrPorTier.size());
            cartaElegida = cartasArrPorTier.get(i);
            guardarCartaEnPool(con, cartaElegida);
            guardarCartaEnColeccion(con, cartaElegida, idUsuario);
            restarTirada(con, idUsuario);


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

        return cartaElegida;
    }
}

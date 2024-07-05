package modelsDAO;

import models.Carta;
import util.Conector;

import java.sql.*;
import java.util.*;

public class CartaDAO {

    public static List<Carta> recuperarCartas(Connection con) throws SQLException {
        List<Carta> cartasArr = new ArrayList<>();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id,imageLink,tier FROM modelosCartas");

        while (rs.next()) {
            Carta carta = new Carta();
            carta.setId_modelo(rs.getInt("id"));
            carta.setImageLink(rs.getString("imageLink"));
            carta.setTier(rs.getInt("tier"));
            cartasArr.add(carta);
        }

        return cartasArr;
    }

    public static void guardarCartaEnPool(Connection con, Carta carta, int idUser) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO poolDeCartas (id_modelo, id_usuario) VALUES (?,?)");
        ps.setInt(1,carta.getId_modelo());
        ps.setInt(2,idUser);
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
            // 5 -> especial; 4 -> JV; 3 -> oro; 2 -> plata; 1 -> bronce
            if (rng <= 3)
                // Aquí debería ser 5, pero como no hay cartas especiales aún no quiero que me dé fallos a la hora de testear
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
            guardarCartaEnPool(con, cartaElegida, idUsuario);
            restarTirada(con, idUsuario);


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

        return cartaElegida;
    }

    public static void crearCarta(Carta carta) {
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO modelosCartas (imageLink, tier, media, pac, sho, pas, dri, def, phy) " +
                            "VALUES (?,?,?,?,?,?,?,?,?);");
            ps.setString(1,carta.getImageLink());
            ps.setInt(2,carta.getTier());
            ps.setInt(3,carta.getMedia());
            ps.setInt(4,carta.getPac());
            ps.setInt(5,carta.getSho());
            ps.setInt(6,carta.getPas());
            ps.setInt(7,carta.getDri());
            ps.setInt(8,carta.getDef());
            ps.setInt(9,carta.getPhy());
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

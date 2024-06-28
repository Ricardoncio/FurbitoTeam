package modelsDAO;

import models.Carta;

import java.sql.*;
import java.util.*;

public class CartaDAO {

    public static List<Carta> recuperarCartas() {
        List<Carta> cartasArr = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FurbitoTeam","root","1234");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cartas")) {

            while (rs.next()) {
                Carta carta = new Carta();
                carta.setId(rs.getInt("id"));
                carta.setImageLink(rs.getString("imageLink"));
                carta.setTier(rs.getInt("tier"));
                cartasArr.add(carta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartasArr;
    }

    public static Carta abrirCarta() {
        List<Carta> cartasArr = recuperarCartas();

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

        return cartasArrPorTier.get(i);
    }
}

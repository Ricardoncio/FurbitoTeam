package modelsDAO;

import models.Carta;
import models.Usuario;
import util.Conector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static Usuario credencialesOK(String nombreUsuario, String pass) {
        Usuario usuario = null;
        Connection con = null;
        
        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT id,nombre_usuario,tiradas FROM usuarios WHERE nombre_usuario = ? AND pass = ?");
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setTiradas(rs.getInt("tiradas"));
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

        return usuario;
    }

    public static List<Carta> recuperarColeccion(int idUsuario) {
        List<Carta> coleccion = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM inventario AS i INNER JOIN poolDeCartas AS p on i.id_carta = p.id_unico WHERE i.id_usuario = " + idUsuario);
            while (resultSet.next()) {
                Carta carta = new Carta();
                carta.setId(resultSet.getInt("p.id_unico"));
                carta.setImageLink(resultSet.getString("p.imageLink"));
                carta.setTier(resultSet.getInt("p.tier"));
                carta.setAlt(resultSet.getString("p.alt"));
                coleccion.add(carta);
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

        return coleccion;
    }

    public static void sumarTirada(int cantidad, List<Integer> ids) {
        Connection con = null;
        String update = "UPDATE usuarios SET tiradas = tiradas + " + cantidad + " WHERE ";
        for (int i = 0; i < ids.size(); i++) {
            if (i < ids.size() - 1)
                update += "id = " + ids.get(i) + " OR ";
            else
                update += "id = " + ids.get(i);
        }

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement ps = con.prepareStatement(update);
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

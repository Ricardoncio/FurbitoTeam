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
            PreparedStatement stmt = con.prepareStatement("SELECT id,nombre_usuario FROM usuarios WHERE nombre_usuario = ? AND pass = ?");
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
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
        List<Integer> idCartas = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT  * FROM inventario WHERE id_usuario = " + idUsuario);
            while (resultSet.next()) {
                idCartas.add(resultSet.getInt("id_carta"));
            }

            List<Carta> cartasArr = CartaDAO.recuperarCartas(con);
            for (Integer i : idCartas) {
                cartasArr.parallelStream().filter(carta -> carta.getId() == i).forEach(coleccion::add);
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
}

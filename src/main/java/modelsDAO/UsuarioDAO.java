package modelsDAO;

import models.Carta;
import models.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public static Usuario credencialesOK(String nombreUsuario, String pass) {
        Usuario usuario = null;
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FurbitoTeam","root","1234");
            PreparedStatement stmt = con.prepareStatement("SELECT id,nombre_usuario FROM usuarios WHERE nombre_usuario = ? AND pass = ?");
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}

package modelsDAO;

import models.Carta;
import models.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public static int credencialesOK(String nombreUsuario, String pass) {
        int id = 0;
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FurbitoTeam","root","1234");
            PreparedStatement stmt = con.prepareStatement("SELECT id FROM usuarios WHERE nombre_usuario = ? AND pass = ?");
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return id;
    }
}

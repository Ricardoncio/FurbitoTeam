package modelsDAO;

import jakarta.servlet.http.HttpServletRequest;
import models.Carta;
import models.Usuario;
import util.Conector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public class UsuarioDAO {

    public static Usuario recuperarUsuario(int idUser) {
        Usuario usuario = new Usuario();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id,nombre_usuario,tiradas FROM usuarios WHERE id = ?");
            ps.setInt(1,idUser);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setTiradas(rs.getInt("tiradas"));
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

        return usuario;
    }

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

        return usuario;
    }

    public static List<Carta> recuperarColeccion(int idUsuario) {
        List<Carta> coleccion = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM pooldecartas AS p INNER JOIN modelosCartas AS m on p.id_modelo = m.id WHERE p.id_usuario = " + idUsuario);
            while (resultSet.next()) {
                Carta carta = new Carta();
                carta.setId(resultSet.getInt("p.id_unico"));
                carta.setImageLink(resultSet.getString("m.imageLink"));
                carta.setTier(resultSet.getInt("m.tier"));
                carta.setMedia(resultSet.getInt("m.media"));
                coleccion.add(carta);
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

        return coleccion.stream().sorted().toList();
    }

    public static List<Carta> recuperarColeccionFiltrada(int idUsuario) {
        List<Carta> coleccion = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM pooldecartas AS p INNER JOIN modelosCartas AS m on p.id_modelo = m.id WHERE p.id_usuario = " + idUsuario);
            while (resultSet.next()) {
                Carta carta = new Carta();
                carta.setId(resultSet.getInt("p.id_unico"));
                carta.setImageLink(resultSet.getString("m.imageLink"));
                carta.setTier(resultSet.getInt("m.tier"));
                carta.setMedia(resultSet.getInt("m.media"));
                if (coleccion.stream().noneMatch(c -> c.getImageLink().equals(carta.getImageLink()))) {
                    coleccion.add(carta);
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

        return coleccion.stream().sorted().toList();
    }

    public static void sumarTiradas(int cantidad, List<Integer> ids, Connection con) {
        StringBuilder update = new StringBuilder("UPDATE usuarios SET tiradas = tiradas + " + cantidad + " WHERE ");
        for (int i = 0; i < ids.size(); i++) {
            if (i < ids.size() - 1)
                update.append("id = ").append(ids.get(i)).append(" OR ");
            else
                update.append("id = ").append(ids.get(i));
        }

        try {
            PreparedStatement ps = con.prepareStatement(update.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void parteTiradas(HttpServletRequest req, Connection con) {
        int nuevasTiradas = Integer.parseInt(req.getParameter("nuevasTiradas"));
        List<Integer> ids = new ArrayList<>();
        if (req.getParameter("pablo") != null && req.getParameter("pablo").equals("on"))
            ids.add(1);
        if (req.getParameter("daneti") != null && req.getParameter("daneti").equals("on"))
            ids.add(2);
        if (req.getParameter("talleres") != null && req.getParameter("talleres").equals("on"))
            ids.add(3);
        if (req.getParameter("pukaso") != null && req.getParameter("pukaso").equals("on"))
            ids.add(4);
        if (req.getParameter("price") != null && req.getParameter("price").equals("on"))
            ids.add(5);
        if (req.getParameter("yayo") != null && req.getParameter("yayo").equals("on"))
            ids.add(6);

        if (nuevasTiradas > 0 && !ids.isEmpty())
            sumarTiradas(nuevasTiradas, ids, con);
    }

    private static void sumarPuntos(HttpServletRequest req, Connection con, String top, int puntos) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE rankingVersion SET puntos = puntos + ? WHERE nombre_usuario = ?");
        String usuario;
        if (req.getParameter(top) != null && !req.getParameter(top).isEmpty()) {
            usuario = req.getParameter(top);
            ps.setInt(1, puntos);
            ps.setString(2, usuario);
            ps.executeUpdate();
        }
    }
    public static void partePuntosIRL(HttpServletRequest req, Connection con) {
        try {
            sumarPuntos(req,con,"top1",6);
            sumarPuntos(req,con,"top2",5);
            sumarPuntos(req,con,"top3",4);
            sumarPuntos(req,con,"top4",3);
            sumarPuntos(req,con,"top5",2);
            sumarPuntos(req,con,"top6",1);

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

    public static void borrarEquipo(int idUser) {
        Connection con = null;

        try {
            con = new Conector().getMYSQLConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE equipo SET id_carta = null WHERE id_usuario = ?");
            ps.setInt(1,idUser);
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

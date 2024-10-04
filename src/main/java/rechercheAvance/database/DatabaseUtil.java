package rechercheAvance.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/rechercheproduit";
    private static final String USERNAME = "mon_utilisateur";
    private static final String PASSWORD = "mon_mot_de_passe";

    // Charger le pilote JDBC au moment de l'initialisation de la classe
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erreur lors du chargement du pilote JDBC", e);
        }
    }

    // Méthode pour établir une connexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // Méthode pour fermer une connexion
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

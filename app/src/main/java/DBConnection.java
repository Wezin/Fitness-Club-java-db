import java.sql.Connection; //To represent a database connection
import java.sql.DriverManager; // Opens a connection to the database
import java.sql.SQLException; //Handles database connection errors
import io.github.cdimascio.dotenv.Dotenv;


//Class allows us to connect to the database
public class DBConnection {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static final String URL = dotenv.get("DB_URL", "jdbc:postgresql://localhost:5432/fitness_club");
    private static final String USER = dotenv.get("DB_USER", "postgres");
    private static final String PASSWORD = dotenv.get("DB_PASS", "");

    public static Connection getConnection() throws SQLException { //Opens and returns a connection
        return DriverManager.getConnection(URL, USER, PASSWORD); //return the connection
    }
}
import entities.AlbumRepository;
import entities.Artist;
import com.mysql.cj.jdbc.Driver;
import entities.ArtistRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainApplication {
    public static void main(String[] args) {

        registerJDBCDriver();
        Connection mysqlDbConnection = getConnection("mysql");
        AlbumRepository albumRepository = new AlbumRepository(mysqlDbConnection);
        ArtistRepository artistRepository = new ArtistRepository(mysqlDbConnection);
        executeStatement(mysqlDbConnection, "DROP DATABASE IF EXISTS music;");
        executeStatement(mysqlDbConnection, "CREATE DATABASE IF NOT EXISTS music;");
        executeStatement(mysqlDbConnection, "USE music;");
        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS music.album(")
                .append("id int not null,")
                .append("title text not null,")
                .append("year int not null,")
                .append("artist text not null,")
                .append("price int not null);")
                .toString());
        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS musicStore.artist(")
                .append("id int auto_increment primary key,")
                .append("first_name text not null,")
                .append("last_name text not null,")
                .append("instrument text not null,")
                .toString());

//        albumRepository.create(new Car(12L,"Toyota", "Camry", "Red", 232L));
//        albumRepository.create(new Car(13L,"Toyota", "Camry", "Blue", 132L));

/*
        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("INSERT INTO automobiles.carTable(")
                .append("id, make, model, color, vin) ")
                .append("VALUES (12, 'Toyota', 'Camry', 'Red', 232;")
                .toString());

        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("INSERT INTO automobiles.carTable(")
                .append("id, make, model, color, vin) ")
                .append("VALUES (13, 'Toyota', 'Camry', 'Red', 232;")
                .toString());

*/
        System.out.println(albumRepository.readAll());
    }

    static ResultSet executeQuery(Connection connection, String sqlQuery) {
        try {
            Statement statement = getScrollableStatement(connection);
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    static void printResults(ResultSet resultSet) {
        try {
            for (int rowNumber = 0; resultSet.next(); rowNumber++) {
                String firstColumnData = resultSet.getString(1);
                String secondColumnData = resultSet.getString(2);
                String thirdColumnData = resultSet.getString(3);
                String fourthColumnData = resultSet.getString(4);
                String fifthColumnData = resultSet.getString(5);
                System.out.println(new StringJoiner("\n")
                        .add("Row number = " + rowNumber)
                        .add("First Column = " + firstColumnData)
                        .add("Second Column = " + secondColumnData)
                        .add("Third Column = " + thirdColumnData)
                        .add("Fourth Column = " + fourthColumnData)
                        .add("Fifth column = " + fifthColumnData));
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    static void executeStatement(Connection connection, String sqlStatement) {
        try {
            Statement statement = getScrollableStatement(connection);
            statement.execute(sqlStatement);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    static Statement getScrollableStatement(Connection connection) {
        int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
        int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
        try { // scrollable statements can be iterated more than once without closing
            return connection.createStatement(resultSetType, resultSetConcurrency);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    static Connection getConnection(String dbVendor) {
        String username = "root";
        String password = "zipcode0";
        String url = new StringBuilder()
                .append("jdbc:")
                .append(dbVendor)
                .append("://127.0.0.1/")
                .append("?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
                .toString();
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    static void registerJDBCDriver() {
        // Attempt to register JDBC Driver
        try {
            DriverManager.registerDriver(Driver.class.newInstance());
        } catch (InstantiationException | IllegalAccessException | SQLException e1) {
            throw new RuntimeException(e1);
        }

        Artist art = new Artist(4L, "Poker", "Wooper", "Vocal");


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unitT");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(art);
        entityManager.getTransaction().commit();

        System.out.println(art);
    }
}

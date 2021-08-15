package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepository implements Repo {
        private Connection connection;

        public ArtistRepository(Connection connection) {
            this.connection = connection;
        }

        @Override
        public Connection getConnection() {
            return connection;
        }

        public void create(Artist artist) {
            executeStatement(
                    String.format(new StringBuilder()
                                    .append("INSERT INTO music.artist(")
                                    .append("id, first_name, last_name, instrument) ")
                                    .append("VALUES (%s, '%s', '%s', '%s');")
                                    .toString(),
                            artist.getId(),
                            artist.getFirst_name(),
                            artist.getLast_name(),
                            artist.getInstrument()));
        }

        public List<Artist> readAll() {
            ResultSet resultSet = executeQuery(String.format("Select * FROM music.artist;"));
            List<Artist> list = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    String instrument = resultSet.getString(4);
                    list.add(new Artist(
                            Long.parseLong(id),
                            firstName,
                            lastName,
                            instrument));
                }
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }
            return list;
        }

        public Artist read(Long id) {
            return readAll()
                    .stream()
                    .filter(artist -> artist.getId().equals(id))
                    .findAny()
                    .get();
        }

        public void update(Long id, Artist newArtistData) {
            executeStatement(
                    String.format(new StringBuilder()
                            .append("UPDATE music.album")
                            .append("SET artist_id =" + newArtistData.getId())
                            .append("first_name=" + newArtistData.getFirst_name())
                            .append("last_name=" + newArtistData.getLast_name())
                            .append("instrument=" + newArtistData.getInstrument())
                            .append("WHERE id=" + id.toString() + ";")
                            .toString()));
        }

        public void delete(Long id) {
            executeStatement(
                    String.format(new StringBuilder()
                                    .append("DELETE FROM music.artist(")
                                    .append("WHERE id = %s; ")
                                    .toString(),
                            id));

        }

        public void delete(Artist artist) {
            Long id = artist.getId();
            delete(id);
        }
    }
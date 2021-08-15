package entities;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository implements Repo{
    private Connection connection;

    public AlbumRepository(Connection connection){
        this.connection=connection;
    }
    @Override

    public Connection getConnection() {
        return connection;
    }

    public void create(Album album){
        executeStatement(
                String.format(new StringBuilder()
                .append("INSERT INTO music.album(")
                .append("artist_id, artist, title, year, price) ")
                .append("VALUES (%s, '%s', '%s', '%s', %s);")
                .toString(),
                album.getId(),
                album.getArtist(),
                album.getTitle(),
                album.getYear(),
                album.getPrice()));
    }

    public List<Album> readAll(){
        ResultSet resultSet =executeQuery(String.format("Select * FROM music.album;"));
        List<Album> list = new ArrayList<>();
        try {
            while(resultSet.next()) {
                String id = resultSet.getString(1);
                String artist = resultSet.getString(2);
                String title = resultSet.getString(3);
                String year = resultSet.getString(4);
                String price = resultSet.getString(5);
                list.add(new Album(
                        Long.parseLong(id),
                        artist,
                        title,
                        Integer.parseInt(year),
                        Integer.parseInt(price)));
            }
        }catch (SQLException throwables){
            throw new RuntimeException(throwables);
        }
        return list;
    }
    public Album read(Long id){
        return readAll()
                .stream()
                .filter(album -> album.getId().equals(id))
                .findAny()
                .get();
    }

    public void update(Long id, Album newAlbumData){
        executeStatement(
                String.format(new StringBuilder()
                                .append("UPDATE music.album")
                                .append("SET artist_id =" + newAlbumData.getId())
                                .append("artist=" + newAlbumData.getArtist())
                                .append("price=" + newAlbumData.getPrice())
                                .append("title=" + newAlbumData.getTitle())
                                .append("year=" + newAlbumData.getYear())
                                .append("WHERE id=" + id.toString() + ";" )
                                .toString()));
    }

    public void delete(Long id){
        executeStatement(
                String.format(new StringBuilder()
                                .append("DELETE FROM music.album(")
                                .append("WHERE id = %s; ")
                                .toString(),
                                id));

    }

    public void delete(Album album) {
        Long id= album.getId();
        delete(id);
    }
    }

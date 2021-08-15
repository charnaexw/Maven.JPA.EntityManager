package services;

import entities.Album;

import java.util.List;

public class AlbumService implements Service{

    public AlbumService(){}

    public void create (Album newAlbum){
        entityManager.getTransaction().begin();
        entityManager.persist(newAlbum);
        entityManager.getTransaction().commit();
    }
    public List<Album> readAll(){
        return entityManager.createQuery("SELECT * FROM music.album", Album.class).getResultList();
    }
    public Album readById(Long id){
        return entityManager.find(Album.class,id);
    }
    public void update(Long id, Album newAlbum){

        entityManager.getTransaction().begin();
        Album b = entityManager.find(Album.class,id);
        b.setArtist(newAlbum.getArtist());
        b.setPrice(newAlbum.getPrice());
        b.setTitle(newAlbum.getTitle());
        b.setYear(newAlbum.getYear());
    }
    public void deleteById(Long id){
        Album b = readById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(b);
        entityManager.getTransaction().commit();
    }
}

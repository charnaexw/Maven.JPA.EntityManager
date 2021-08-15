package services;

import entities.Album;
import entities.Artist;

import java.util.List;

public class ArtistService implements Service
{

    public ArtistService(){}

    public void create (Artist newArtist){
        entityManager.getTransaction().begin();
        entityManager.persist(newArtist);
        entityManager.getTransaction().commit();
    }
    public List<Artist> readAll(){
        return entityManager.createQuery("SELECT * FROM music.artist", Artist.class).getResultList();
    }
    public Artist readById(Long id){
        return entityManager.find(Artist.class,id);
    }
    public void update(Long id, Artist newArtist){

        entityManager.getTransaction().begin();
        Artist b = entityManager.find(Artist.class,id);
        b.setFirst_name(newArtist.getFirst_name());
        b.setLast_name(newArtist.getLast_name());
        b.setInstrument(newArtist.getInstrument());
    }
    public void deleteById(Long id){
        Artist b = readById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(b);
        entityManager.getTransaction().commit();
    }

}

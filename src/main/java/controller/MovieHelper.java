package controller;

import java.util.List;
import controller.MovieHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Movie;

/**
 * @author Kenneth Nimmo - Knimmo
 * CIS175 - Fall 2021
 * Oct 6, 2023
 */
public class MovieHelper {

	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("WebMoviesList");

	public void insertMovie(Movie m) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();
		em.close();
	}

	public List<Movie> showAllMovies() {
		EntityManager em = emfactory.createEntityManager();
		List<Movie> allMovies = em.createQuery("SELECT m FROM Movie m").getResultList();
		return allMovies;
	}

	public void deleteMovie(Movie toDelete) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Movie> typedQuery = em.createQuery(
				"select movie from Movie movie where movie.id = :selectedId and movie.title = :selectedTitle and movie.genre = :selectedGenre",
				Movie.class);
		typedQuery.setParameter("selectedTitle", toDelete.getTitle());
		typedQuery.setParameter("selectedGenre", toDelete.getGenre());
		typedQuery.setParameter("selectedId", toDelete.getId());
		typedQuery.setMaxResults(1);
		Movie result = typedQuery.getSingleResult();
		em.remove(result);
		em.getTransaction().commit();
		em.close();
	}

	public Movie searchForMovieById(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		Movie found = em.find(Movie.class, id);
		em.close();
		return found;
	}

	public void updateMovie(Movie movieToUpdate) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(movieToUpdate);
		em.getTransaction().commit();
		em.close();
	}
	
    public List<Movie> getAllMovies() {
        EntityManager em = emfactory.createEntityManager();
        TypedQuery<Movie> typedQuery = em.createQuery("SELECT m from Movie m", Movie.class);
        List<Movie> movies = null;
        try {
            movies = typedQuery.getResultList();
        } finally {
            em.close();
        }
        return movies;
    }
    
    public Movie getMovieById(int id) {
        EntityManager em = emfactory.createEntityManager();
        Movie foundMovie = null;
        try {
            foundMovie = em.find(Movie.class, id);
        } finally {
            em.close();
        }
        return foundMovie;
    }
}
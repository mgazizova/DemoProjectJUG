package ru.mgazizova.demodb.datasource;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import ru.mgazizova.demodb.model.Film;

/**
 * @author vtarasov
 * @since 04.11.18
 */
@Service
public class FilmFinderImpl implements FilmFinder {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Film load(int id) {
        return em.find(Film.class, id);
    }

    @Override
    public List<Film> list() {
        return em.createQuery("from " + Film.class.getName() + " order by name", Film.class).getResultList();
    }

    @Override
    public List<Film> list(String search, Set<Integer> years, Set<String> genres) {
        String jpql = "from " + Film.class.getName() + " where 1 = 1";

        if (search != null && !search.trim().isEmpty()) {
            jpql += " and name like :name";
        }

        if (!years.isEmpty()) {
            jpql += " and year in (:years)";
        }

        if (!genres.isEmpty()) {
            jpql += " and genre in (:genres)";
        }

        jpql += " order by name";

        TypedQuery<Film> query = em.createQuery(jpql, Film.class);

        if (search != null && !search.trim().isEmpty()) {
            query.setParameter("name", "%" + search + "%");
        }

        if (!years.isEmpty()) {
            query.setParameter("years", years);
        }

        if (!genres.isEmpty()) {
            query.setParameter("genres", genres);
        }

        return query.getResultList();
    }
}

package org.albert.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.albert.entity.Film;
import org.albert.entity.Film$;

import java.util.Optional;

@ApplicationScoped
public class FilmRepository
{
    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Film> getById(short filmId)
    {
        /*
         * equal() causes "org.hibernate.query.SemanticException: Cannot compare left expression of type 'java.lang.Short' with right expression of type 'java.lang.Object'"
         * Issue: https://github.com/speedment/jpa-streamer/issues/390
         * */
//        return jpaStreamer.stream(Film.class)
//                .filter(Film$.filmId.equal(filmId))
//                .findFirst();

        return jpaStreamer.stream(Film.class)
                .filter(Film$.filmId.in(filmId))
                .findFirst();
    }
}

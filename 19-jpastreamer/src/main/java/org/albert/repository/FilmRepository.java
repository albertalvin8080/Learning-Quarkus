package org.albert.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.albert.entity.Film;
import org.albert.entity.Film$;

import java.util.Optional;
import java.util.stream.Stream;

/*
 * WARNING: Remember to add columnDefinition to entities with the type as Object
 * */
@ApplicationScoped
public class FilmRepository
{
    @Inject
    JPAStreamer jpaStreamer;

    private static final long PAGE_SIZE = 20;

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

    public Stream<Film> getPagedFilms(long page)
    {
        return jpaStreamer.stream(Film.class)
                .skip(PAGE_SIZE * page)
                .limit(PAGE_SIZE);
    }

    public Stream<Film> getPagedFilmsProjection(long page)
    {
        /*
         * WARNING: you must define a constructor inside Film.class for filmId, title and description.
         *
         * Projection.select(...) is used to fetch only the necessary columns from the database.
         * */
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.description))
                .skip(PAGE_SIZE * page)
                .limit(PAGE_SIZE);
    }

    public Stream<Film> filterByMinLengthAndTitleStartCharacters(short length, String startsWith)
    {
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                .filter(Film$.length.greaterOrEqual(length).and(Film$.title.startsWith(startsWith)))
                .sorted(Film$.length);
//                .sorted(Film$.length.reversed());
    }

    public Stream<Film> getAllFilmsAndActors()
    {
        // Join
        final StreamConfiguration<Film> streamConfiguration = StreamConfiguration.of(Film.class)
                .joining(Film$.actors);

        return jpaStreamer.stream(streamConfiguration)
                .sorted(Film$.replacementCost.reversed());
    }

    @Transactional
    public void updateFilmTitleById(short id, Film film)
    {
        /* ISSUE:
         * [Conversion from java.sql.Date to BLOB is not supported.]
         * OR
         * [Data truncated for column 'release_year' at row 1] (for Timestamp)
         *
         * SOLUTION:
         * Change release_year type to Integer.
         * */
        jpaStreamer.stream(Film.class)
                .filter(Film$.filmId.in(id))
                .forEach(f -> {
                    f.setTitle(film.getTitle());
                });
    }
}

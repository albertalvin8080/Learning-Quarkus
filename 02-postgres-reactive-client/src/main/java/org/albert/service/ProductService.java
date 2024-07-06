package org.albert.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.albert.entity.Author;

@ApplicationScoped
public class ProductService
{
    @Inject
    PgPool pgPoolClient;

    public Multi<Author> getAll()
    {
        return pgPoolClient.query("SELECT id, name FROM author ORDER BY id ASC;").execute()
                .onItem()
                .transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet))
                .onItem()
                .transform(row -> new Author(row.getLong(0), row.getString(1)));
    }

    public Uni<Author> findById(Long id)
    {
        String query = "SELECT id, name FROM author WHERE id = $1";
        return pgPoolClient.preparedQuery(query)
                .execute(Tuple.of(id))
                .onItem()
                .transform(rowSet -> {
                    if (rowSet.iterator().hasNext())
                    {
                        final Row row = rowSet.iterator().next();
                        Long tempId = row.getLong("id");
                        String tempName = row.getString("name");
                        return new Author(tempId, tempName);
                    }
                    else return null;
                });
    }

    public Uni<Long> save(Author author)
    {
        String query = """
                INSERT INTO author (name) VALUES ($1) RETURNING id
                """;
        return pgPoolClient.preparedQuery(query)
                .execute(Tuple.of(author.getName()))
                .onItem()
                .transform(rowSet -> rowSet.iterator().next().getLong("id"));
    }

    public Uni<Boolean> delete(Long id)
    {
        String query = """
                DELETE FROM author WHERE id = $1
                """;

        return pgPoolClient.preparedQuery(query).execute(Tuple.of(id))
                .onItem()
                // rowCount() returns the number of AFFECTED rows, not the actual row.
                .transform(rowSet -> rowSet.rowCount() == 1);
    }

    public Uni<Boolean> update(Author author)
    {
        String query = """
                UPDATE author SET name = $2 WHERE id = $1
                """;

        return pgPoolClient.preparedQuery(query)
                .execute(Tuple.of(author.getId(), author.getName()))
                .onItem()
                .transform(rowSet -> rowSet.rowCount() == 1);
    }
}

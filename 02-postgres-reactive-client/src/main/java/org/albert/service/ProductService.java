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
        return pgPoolClient.query("SELECT id, name FROM author;").execute()
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
}

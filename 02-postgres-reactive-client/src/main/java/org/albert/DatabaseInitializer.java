package org.albert;

import io.quarkus.runtime.Startup;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

// WARNING: You must use the @Startup because threads created for handling requests
// cannot be blocked to create the database. You could also use this @PostConstruct
// inside ProductService if you had annotated it with @Startup.
@Startup
@ApplicationScoped
public class DatabaseInitializer {

    @Inject
    PgPool pgPoolClient;

    @PostConstruct
    public void init() {
        String dropTable = "DROP TABLE IF EXISTS author;";
        String createTable = """
                    CREATE TABLE IF NOT EXISTS author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(40) NOT NULL
                    );
                """;
        String insert = """
            INSERT INTO author (name) VALUES ('Fraz Bonaparta'), ('Emil Sebe');
        """;

        pgPoolClient.query(dropTable).execute()
                .flatMap(unusedRowSet -> pgPoolClient.query(createTable).execute())
                .flatMap(unusedRowSet -> pgPoolClient.query(insert).execute())
                .await()
                .indefinitely();
    }
}

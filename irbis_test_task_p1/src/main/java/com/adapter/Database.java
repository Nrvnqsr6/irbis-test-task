package com.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        try {
            Database.Connect();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void Connect() throws SQLException {
        // connection = DriverManager
        //     .getConnection("jdbc:postgresql://localhost:5432/irbis-test-task"
        //         , "root", "root");
        connection = DriverManager
            .getConnection(
                System.getenv("connection_path"),
                System.getenv("user"),
                System.getenv("password"));
    }

    public static Statement GetStatement() {
        try {
            return Database.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void InitDB() throws SQLException {
        Statement statement = getConnection().createStatement();

        var query = """
            CREATE TABLE IF NOT EXISTS public.links
                (
                    id SERIAL PRIMARY KEY,
                    url text NOT NULL,
                    name text NOT NULL,
                    UNIQUE (url)
                )
        """;
        tryExecuteUpdate(statement, query);
        //statement = connection.createStatement();
        

        query = """
            CREATE TABLE IF NOT EXISTS public.rubrics
                (
                    id SERIAL PRIMARY KEY,
                    name text NOT NULL,
                    source_id integer NOT NULL,
                    constraint fk_source
                    FOREIGN KEY(source_id)
                        REFERENCES links(id)
                );
        """;
        //statement = connection.createStatement();
        tryExecuteUpdate(statement, query);

        query = """
            CREATE TABLE IF NOT EXISTS public.news
                (
                    id SERIAL PRIMARY KEY,
                    title text NOT NULL,
                    link text NOT NULL UNIQUE,
                    date timestamp NOT NULL,
                    rubric_id integer NOT NULL,
                    constraint fk_rubric
                        FOREIGN KEY(rubric_id)
                            REFERENCES rubrics(id)
                );
        """;
        //statement = connection.createStatement();
        tryExecuteUpdate(statement, query);

        query = """
            INSERT INTO links (url, name)
            VALUES ('https://lenta.ru', 'Лента'),
            ('https://ria.ru', 'РИА Новости');
        """;
        //statement = connection.createStatement();
        tryExecuteUpdate(statement, query);

        query = """
            CREATE TABLE IF NOT EXISTS public.auth
            (
                id SERIAL PRIMARY KEY,
                key text NOT NULL,
                UNIQUE (key)
            )
                """;
        tryExecuteUpdate(statement, query);

        query = """
            INSERT INTO auth (key)
            VALUES ('key1'),
            ('key2');
                """;
        tryExecuteUpdate(statement, query);
        // statement.executeUpdate(query);
    }

    private static void tryExecuteUpdate(Statement statement, String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

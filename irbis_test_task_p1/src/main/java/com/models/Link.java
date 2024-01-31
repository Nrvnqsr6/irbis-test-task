package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.adapter.Database;

public class Link {
    private String name;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private String url;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void Insert() throws SQLException {
        var statement = Database.GetStatement();

        if (statement != null)
            statement.executeUpdate(String.format("""
                INSERT INTO links (url, name)
                VALUES ('%s', '%s');
                """, this.url, this.name)
            );
    }

    public static int getIdByURL(String url) throws SQLException {
        var statement = Database.GetStatement();

        if (statement != null) {
            var res = statement.executeQuery(String.format("""
                    SELECT id FROM links 
                    WHERE url = '%s'
                    """, url)
            );
            res.next();
            return res.getInt("id");
        }
        else 
            throw new SQLException("Wrong source");
    }
}

package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.adapter.Database;

public class Rubric {
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private int source_id;

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public void Insert() throws SQLException {

        var statement = Database.GetStatement();
        var isExist = statement.executeQuery(String.format("""
            SELECT id FROM rubrics 
            WHERE name = '%s' AND source_id = '%d'
            """, this.name, this.source_id)
        );

        if (isExist.isBeforeFirst()) {
            throw new SQLException("Такая рубрика уже существует");
        }

        if (statement != null)
            statement.executeUpdate(String.format("""
                INSERT INTO rubrics (name, source_id)
                VALUES ('%s', '%d');
                """, this.name, this.source_id)
            );
    }

    public static int SelectByRubric(String rubric, int source_id) throws SQLException {
        var statement = Database.GetStatement();

        if (statement != null) {
            
            var res = statement.executeQuery(String.format("""
                    SELECT id FROM rubrics 
                    WHERE name = '%s' AND source_id = '%d'
                    """, rubric, source_id)
            );
            res.next();
            return res.getInt("id");
        }
        return -1;
    }
}

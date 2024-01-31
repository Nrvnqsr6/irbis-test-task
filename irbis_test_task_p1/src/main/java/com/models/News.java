package com.models;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.crypto.Data;

import com.adapter.Database;

public class News {
    private String title;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    private int rubric_id;
    
    public int getRubric() {
        return rubric_id;
    }
    public void setRubric(int rubric) {
        this.rubric_id = rubric;
    }

    private String link;

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    private Date datetime;

    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void Insert() throws SQLException {
        var statement = Database.GetStatement();

        if (statement != null)
            statement.executeUpdate(String.format("""
                INSERT INTO news (title, link, date, rubric_id)
                VALUES ('%s', '%s', '%s', '%s');
                """, 
                this.title, 
                this.link, 
                new Timestamp(this.datetime.getTime()), 
                this.rubric_id)
            );
    }
}

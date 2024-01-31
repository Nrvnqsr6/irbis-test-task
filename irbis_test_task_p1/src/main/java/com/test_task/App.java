package com.test_task;

import java.sql.SQLException;

import com.adapter.Database;

public class App 
{
    /**
     * @param args
     * @throws ClassNotFoundException 
     */
    public static void main( String[] args ) throws ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        
        try {
            Database.Connect();
             Database.InitDB();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        
        Startup.StartParsing();
    }


}

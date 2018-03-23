package dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author 
 */
public class connection {
    Connection con ;
    ResultSet rs;
    Statement st;
    
    
    Config c = new Config();
    
    public void openDb()
    {
         try
        {
  
            
        try {
                Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(c.url, c.user, c.password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       
        }
        catch(Exception ee)
        {
        
            System.out.println(ee);
        }
    }
    public void closeDb()
    {
        try{
        con.commit();
        con.close();
        }
        catch(Exception ee)
        {
            
            
        }
             
    }
    public int exMyQuery(String qry)
    {  
       int rw = 0;
       String str = "1";  
        try
        {
          //  System.out.println(qry);
            rw = st.executeUpdate(qry);
        }
            catch(Exception ee)
            {
                str = ee.toString();

                System.out.println(str);
            }
        finally
        {
            closeDb();
        }
        
    return rw;
    }
    public ResultSet selectQuery(String qry)
      { 
          try
        {
            rs = st.executeQuery(qry);
        }
      catch(Exception ee)
      {
          System.out.println(ee);
      }
          
        
        return rs;
    }
    
    
    
}


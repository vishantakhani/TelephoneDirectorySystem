/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lenovo
 */
public class dbcon {
   
 
     
   static Statement st=null;   
   static ResultSet rs=null;
   public static void main(String[] args) throws ClassNotFoundException, SQLException {
       
     try{
       
         connection cn = new connection();
         
         cn.openDb();
          System.out.println("Opened database successfully");
   String sql ="select * from sec_address";
   
   rs=cn.selectQuery(sql);
     
     while(rs.next())
     {
         System.out.println(" * ");
 //System.out.println(rs.getInt("uid")+" "+rs.getInt("paddid" +" "+rs.getInt("pconid")));
     }
     
     }catch (Exception e ){
     System.out.println(e);
     }
    }
    
} 

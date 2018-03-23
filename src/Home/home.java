/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Home;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;

public class home {
public static void doConnection() throws ClassNotFoundException
{
        try {
                Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tel-dir","postgres","1234");
            con.setAutoCommit(true);
        
     st=con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
    }
}
    static Connection con ;
    static ResultSet rs;
    static ResultSet rs2;
    static Statement st;
    static int uid;
    static String pass;
    static Scanner sc=new Scanner(System.in); 
   public static void main(String[] args) throws ClassNotFoundException, SQLException 
    {
        
    doConnection();    
    while(true)
    {
        System.out.println("--------------------");
        System.out.println(" Enter 1 to Login");
        System.out.println(" Enter 2 to Signup");
        System.out.println(" Enter 3 to Exit");
        System.out.print(" Enter ---> ");
       int choice=sc.nextInt();
        switch(choice)
        {
            case 1: 
                  System.out.println("--------------------");
                  System.out.print(" Enter User Id --> ");
                  uid=sc.nextInt();
                  System.out.print(" Enter Password --> ");
                 sc.nextLine();
                  pass=sc.nextLine();
                
                 loginUser(uid,pass); 
                  
                break;
            case 2:
                 signUp();
                break;
            case 3: 
                System.out.println("Thank You...");
                System.exit(0);
               break;
             default: System.out.println("Select Valid Option ");
        }
    }
 }

     static void loginUser(int uid, String pass) throws SQLException 
    {
        try {
            
       String qry="select * from user_profile where uid="+uid+" and password='"+pass+"'"; 
       rs=st.executeQuery(qry);
      //  System.out.println("1 "+rs.getInt(1));
     if(!rs.next())
     {
         System.out.println(" Please Enter Valid Details ");
         System.out.println(" Thank You ");
     }
     else 
     {
         gotoOption();
     }
        } catch (SQLException e) {
            System.out.println(e);
        }
       
    }

    private static void gotoOption() throws SQLException 
    {
        System.out.println("---------------------------------");
        System.out.println(" Enter 1 to search ");
        System.out.println(" Enter 2 to get your details ");
        System.out.println(" Enter 3 to delete your details");
        System.out.println(" Enter 4 to logout ");
        System.out.println(" Enter 5 to edit profile");
        System.out.print(" Enter -->  ");
        int choice=sc.nextInt();
        switch(choice)
        {
            case 1: searchRecord();
                   break;
            case 2: getMyDetails(uid);
                   break;
            case 3: deleteMyData(uid);
                   break;
            case 4:
                   uid=0;
                   pass=null;
                   System.out.println("Logout........");
                  System.exit(0);
                   break;
            case 5:
                   System.out.println("");
                   System.out.println(" Enter 1 to Change Password ");
                   System.out.println(" Enter 2 to Change Primary Address ");
                   System.out.println(" Enter 3 to Remove Secondary Address ");
                   System.out.println(" Enter 4 to Change Primary Contact ");
                   System.out.println(" Enter 5 to Remove Secondary Contact ");
                   System.out.println(" Enter 6 to Add Secondary Address ");
                   System.out.println(" Enter 7 to add Secondary Contact ");
                   
                   System.out.println("Enter 8 to goback to home");
                   System.out.print("Enter --> ");
                   int choice2=sc.nextInt();
                   System.out.println("-----------------------");   
                   switch(choice2)
                      {
                       case 1:
                               changePassword(uid);
                              break;
                       case 2:
                               changePrimaryAddress(uid);
                            break;
                       case 3:
                               removeSecondaryAddress(uid); 
                             break;
                           
                       case 4:
                              changePrimaryContact(uid);
                            break;
                       case 5:
                               removeSecondaryContact(uid);
                             break;
                       case 6:
                           String qry="select paddid from user_profile where uid="+uid;
                           rs=st.executeQuery(qry);
                           rs.next();
                           addSecAddress(rs.getInt("paddid"));
                           
                           break;
                       
                       case 7:
                               String qry2="select pconid from user_profile where uid="+uid;
                           rs=st.executeQuery(qry2);
                           rs.next();
                           addSecContact(rs.getInt("pconid"));
                           break;
                           
                       case 8:
                            gotoOption();
                           break;
                        default:
                             System.out.println("Enter Valid Option");
                            gotoOption();
                      }
                break;
            default:
                 System.out.println("Select Valid Option ");
                 gotoOption();
        }
    
    }

    private static void deleteMyData(int uid) throws SQLException 
    {
        try {
            
      String qry1="delete from sec_address where paddid ="+uid;
      
      String qry2="delete from sec_contact where pconid="+uid;
      
      String qry3="delete from primary_address where paddid="+uid;
       
      String qry4="delete from primary_contact where pconid="+uid;
       
      String qry5="delete from user_profile where uid="+uid;
      
      st.executeUpdate(qry1);
      st.executeUpdate(qry2);
      st.executeUpdate(qry3);
      st.executeUpdate(qry4);
      st.executeUpdate(qry5);

        System.out.println("DATA SUCCESSFULLY DELETED....... ");
              } catch (SQLException e) 
        {
            System.out.println(e);
            gotoOption();
        }
      
}

    private static void getMyDetails(int uid) throws SQLException 
    {
        try {
    
            
         System.out.println("--------------------------------");
         String qry1="select paddid,pconid from user_profile where uid="+uid;
         
         int paddid,pconid;
         rs2=st.executeQuery(qry1);
         rs2.next();
         paddid=rs2.getInt("paddid");
         
         pconid=rs2.getInt("pconid");
         
         getUserProfileData(uid);
         getPaddData(paddid);
         getSaddData(paddid);
         getPconData(pconid);
         getSconData(pconid);
         rs2.close();
         gotoOption();
        } catch (SQLException e) 
        {
            System.out.println(e);
            gotoOption();
        }
    
      }

    private static void getUserProfileData(int uid) throws SQLException 
    {
        try {
       
            String qry="select * from user_profile where uid="+uid;
       rs2=st.executeQuery(qry);
       rs2.next();
        System.out.println("-------------------------");
        System.out.println("USER DATA ");
           System.out.println("");
        System.out.println("User id = "+rs2.getInt("uid"));
        System.out.println("First Name = "+rs2.getString("fname"));
        System.out.println("Last Name = "+rs2.getString("lname"));
        System.out.println("Date Of Birth = "+rs2.getDate("dob"));
       rs2.close();
        } catch (SQLException e) {
            System.out.println(e);
            gotoOption();
        }
        
    }

    private static void getPaddData(int paddid) throws SQLException 
    {
       
        try {
        String qry="select * from primary_address where paddid = "+paddid;
        rs2=st.executeQuery(qry);
        rs2.next();
        System.out.println("--------------------------");
        System.out.println("PRIMARY ADDRESS DETAILS");
        System.out.println("");
        System.out.println("Address Id = "+rs2.getInt("paddid")); 
        System.out.println("Address Line 1 = "+rs2.getString("addline1"));
        System.out.println("Address Line 2 = "+rs2.getString("addline2"));
        System.out.println("Address Line 3 = "+rs2.getString("addline3"));
        System.out.println("Address Type = "+rs2.getString("addtype"));
        System.out.println("City = "+rs2.getString("city"));
        System.out.println("Pincode = "+rs2.getInt("pin"));
        rs2.close();
        } catch (SQLException e) 
        {
            System.out.println(e);
            gotoOption();
        }
        }

    private static void getPconData(int pconid) throws SQLException 
    {
        try {
             String qry="select * from primary_contact where pconid="+pconid;
      rs2=st.executeQuery(qry);
      rs2.next();
        System.out.println("-------------------------");
        System.out.println("PRIMARY CONTACT DETAILS");
          System.out.println("");
        System.out.println("Contact Id = "+rs2.getInt("pconid"));
        System.out.println("Phone Number = "+rs2.getBigDecimal("pnum"));
        System.out.println("Email Id = "+rs2.getString("email"));
        rs2.close();
        } catch (SQLException e) {
            System.out.println(e);
      gotoOption();
        }
    }

    private static void getSaddData(int paddid) throws SQLException 
    {
        try {
            
    String qry="select * from sec_address where paddid="+paddid;
   rs2=st.executeQuery(qry);
        
   while(rs2.next())
   {
       System.out.println("------------------------------");
        System.out.println("SECONDARY ADDRESS DERAILS");
       System.out.println("");
       System.out.println("Address Id = "+rs2.getInt("saddid"));
       System.out.println("Address Line 1 = "+rs2.getString("addline1"));
        System.out.println("Address Line 2 = "+rs2.getString("addline2"));
        System.out.println("Address Line 3 = "+rs2.getString("addline3"));
        System.out.println("Address Type = "+rs2.getString("addtype"));
        System.out.println("City = "+rs2.getString("city"));
        System.out.println("Pincode = "+rs2.getInt("pin"));
        System.out.println("");
        System.out.println("");
   
   }
        } catch (SQLException e) {
            System.out.println(e);
        gotoOption();
        }
    
    
    }

    private static void getSconData(int pconid) throws SQLException 
    {
        try {
            
      String qry="select * from sec_contact where pconid="+pconid;
      rs2=st.executeQuery(qry);
        while(rs2.next())
        {
            System.out.println("-------------------------------");
        System.out.println("SECONDARY CONTACT DETAILS");
        
            System.out.println("");
            System.out.println("Contact Id = "+rs2.getInt("sconid"));
            System.out.println("Phone Number = "+rs2.getBigDecimal("pnum"));
            System.out.println("STD Number = "+rs2.getBigDecimal("stdnum"));
            System.out.println("");
            System.out.println("");
            
        }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private static void searchRecord() throws SQLException 
    {
        System.out.println("---------------------------");
        System.out.println(" Enter 1 to Search Address Details");
        System.out.println(" Enter 2 to Search Contact Details");
        System.out.println(" Enter 3 to Goto Home");
        System.out.print("Enter --> ");
        int choice=sc.nextInt();
        switch(choice)
        {
            case 1:
                      System.out.println("-----------------------------");
                      System.out.println(" Enter 1 to Search by Address Id");
                      System.out.println(" Enter 2 to Search by Name ");
                      System.out.println(" Enter 3 to Search by city ");
                      System.out.println(" Enter 4 to Goto Home ");
                     // System.out.println("Enter 4 to search by pincode");
                      System.out.print(" Enter --> ");
                      int choice2=sc.nextInt();
                         switch(choice2)
                         {
                             case 1: 
                                 System.out.println("----------------------------");
                                 System.out.println("");
                                 System.out.print(" Enter Address Id --> ");
                                 int paddid=sc.nextInt();
                                 searchById(paddid);
                                 break;
                             case 2:
                                 System.out.println("-----------------------------");
                                    System.out.println("");
                                    System.out.print(" Enter Name --> ");
                                     String name=sc.next();
                                    searchByName(name);
                                 break;
                             case 3:   
                                 System.out.println("------------------------------");
                                 System.out.println("");
                                    System.out.print("Enter City --> ");
                                     String city=sc.next();
                                    searchByCity(city);
                                 break;
                           case 4:
                         gotoOption();
                           break;
                           default:
                                  System.out.println("Select Valid Option ");
                                  searchRecord();
                         }
                      break;
            case 2:
                     System.out.println("-----------------------------");
                     System.out.println("Enter 1 for search by name");
                     System.out.println("Enter 2 for search by Contact Id");
                     System.out.println("Enter 3 to search by city ");
                      System.out.println("Enter 4 to goto home ");
                     System.out.print("Enter --> ");
                     int choice3=sc.nextInt();
                     switch(choice3)
                     {
                         case 1:
                             System.out.println("---------------------------------");
                             System.out.println("");
                                    System.out.print("Enter Name --> ");
                                     String name=sc.next();
                                    searchByNameCon(name);
                             break;
                         case 2:
                             System.out.println("----------------------------------");
                             System.out.println("");
                                 System.out.print("Enter Address Id --> ");
                                 int pconid=sc.nextInt();
                                 searchByIdCon(pconid);
                              break;
                         case 3:
                             System.out.println("---------------------------------");
                             System.out.println("");
                             System.out.print("Enter City --> ");
                             String city=sc.next();
                             searchByCityCon(city);
                             break;
                         
                         case 4:
                             gotoOption();
                                     break;
                                
                          default:
                              System.out.println("Enter Valid Option");
                              gotoOption();
                         
                         
                         
                     }
                     break;
            case 3:
                  gotoOption();
                break;
            default:
                 System.out.println("Select Valid Option");
                 searchRecord();
        }
        
    }

    private static void searchByName(String name) throws SQLException 
    {
        try {
            
        String qry="select paddid from user_profile where fname='"+name+"'";
        rs=st.executeQuery(qry);
        while(rs.next())
        {
        
            System.out.println("");
            getPaddData(rs.getInt("paddid"));
            getSaddData(rs.getInt("paddid"));
        }
        gotoOption();
    
        } catch (SQLException e) {
            //System.out.println(e);
            gotoOption();
        }
    }

    private static void searchByCity(String city) throws SQLException 
    {
        try {
            
        String qry="select paddid from primary_address where city='"+city+"'";
        rs=st.executeQuery(qry);
        while(rs.next())
        {
            System.out.println("");
            getPaddData(rs.getInt("paddid"));
            getSaddData(rs.getInt("paddid"));
        }
        gotoOption();
        } catch (SQLException e) {
            //System.out.println(e);
            gotoOption();
        }
    }

    private static void searchByPin(int pin) throws SQLException 
    {
        try {
             String qry="select paddid from primary_address where pin="+pin;
      rs=st.executeQuery(qry);
      while(rs.next())
      {
          System.out.println("");
          getPaddData(rs.getInt("paddid"));
          getSaddData(rs.getInt("paddid"));
      }
      gotoOption();

        } catch (SQLException e) {
        //    System.out.println(e);
        gotoOption();
        }
 
    }

    private static void searchById(int paddid) throws SQLException 
    {
    
        getPaddData(paddid);
        getSaddData(paddid);
        
        gotoOption();
    }

    private static void searchByNameCon(String name) throws SQLException 
    {
        try {
            
    String qry="select pconid from user_profile where fname='"+name+"'"; 
    rs=st.executeQuery(qry);
    while(rs.next())
    {
        System.out.println("");
        getPconData(rs.getInt("pconid"));
        getSconData(rs.getInt("pconid"));
    }
    
    gotoOption();
        } catch (SQLException e) {
           // System.out.println(e);
            gotoOption();
        }
        }

    private static void searchByIdCon(int pconid) throws SQLException 
    {
     
        getPconData(pconid);
        getSconData(pconid);
        
        gotoOption();
    }

    private static void signUp() throws SQLException
    {
        System.out.println("-------------------------");
        System.out.print("Enter User Id ");
        int newuid=sc.nextInt();
        
        System.out.println("Enter Password ");
       sc.nextLine();
        String pass1=sc.nextLine();
        
        System.out.println("Confirm Password ");
        String pass2=sc.nextLine();
        if(!pass1.equals(pass2)){
            System.out.println("Please Enter Valid Password ");
            signUp();
            
        }
           
        System.out.print("Enter First Name ");
        String fname=sc.next();
        
        System.out.print("Enter Last Name ");
        String lname=sc.next();
        
        System.out.print("Enter Date Of Birth(yyyy-mm-dd) ");
        String d=sc.next();
        
        System.out.print("Enter Primary Number ");
        BigInteger pnum=sc.nextBigInteger();
        
        System.out.print("Enter Email Address(xxx@domain.com) ");
         String mail=sc.next();
         
        System.out.print("Enter Address Line 1 ");
        sc.nextLine();
        String add1=sc.nextLine();
        
        System.out.print("Enter Address Line 2 ");
       //sc.nextLine();
        String add2=sc.nextLine();
        
        System.out.print("Enter Address Line 3 ");
        //sc.nextLine();
        String add3=sc.nextLine();
                
        
        System.out.print("Enter Address Type (home/office) ");
        String addtype=sc.next();
        
        System.out.print("Enter City ");
        String city=sc.next();
        
        System.out.print("Enter Pincode ");
        int pin=sc.nextInt();
        
        try {
            String qry2="insert into primary_address values("+newuid+",'"+add1+"','"+add2+"','"+add3+"','"+addtype+"','"+city+"',"+pin+") ; insert into primary_contact values("+newuid+","+pnum+",'"+mail+"') ; insert into user_profile values("+newuid+",'"+fname+"','"+lname+"','"+d+"',"+newuid+","+newuid+",'"+pass1+"' ); ";
            st.executeUpdate(qry2);
            
            
   /*         String qry3="";
            st.executeUpdate(qry3);
            
            String qry1="";
            st.executeUpdate(qry1);
            
            System.out.println("Successfully Signup...");   
     */       
            
        } catch (SQLException e) {
         System.out.println(e);
        //    signUp();
        }
    }

    private static void addSecAddress(int paddid) throws SQLException 
    {
        try {
        System.out.println("-------------------");
      
        System.out.print("Enter Address Line 1 ");
        String add1=sc.next();
        
        System.out.print("Enter Address Line 2 ");
        String add2=sc.next();
        
        System.out.print("Enter Address Line 3 ");
        String add3=sc.next();
        
        System.out.print("Enter Address Type (home/office) ");
        String addtype=sc.next();
        
        System.out.print("Enter City ");
        String city=sc.next();
        
        System.out.print("Enter Pincode ");
        int pin=sc.nextInt();
        
        String qry="insert into sec_address(paddid,addline1,addline2,addline3,addtype,city,pin) values("+paddid+",'"+add1+"','"+add2+"','"+add3+"','"+city+"',"+pin+")";
    
         st.executeUpdate(qry);    
         gotoOption();
        
        } 
        catch (SQLException e) 
        {
            
            System.out.println(e);
        gotoOption();
        }
        
    }

    private static void addSecContact(int pconid) throws SQLException 
    {
        try {
              System.out.println("----------------------");
        System.out.println("");
        BigInteger pnum,stdnum;
        pnum=stdnum=null;
        
        System.out.print(" Enter Phone Number ");
        pnum=sc.nextBigInteger();
        
        System.out.println(" Enter STD Number ");
        stdnum=sc.nextBigInteger();
        if(pnum==null && stdnum== null)
        {
            System.out.println(" Enter At Least One Number ");
            addSecAddress(pconid);
        }
        else
        {
            String qry="insert into sec_contact(pconid,pnum,stdnum) values("+pconid+","+pnum+","+stdnum+")";
            st.executeUpdate(qry);
            System.out.println("Contact Successfully Added ");
            gotoOption();
        }
        } catch (SQLException e) {
            
            System.out.println(e);
            gotoOption();
        }
    }

    private static void changePassword(int uid) throws SQLException 
    {
        try {
            System.out.println("---------------------");
        System.out.println("");
        System.out.print(" Enter Password --> ");
        String pass1=sc.next();
        
        System.out.print(" Confirm Password --> ");
        String pass2=sc.next();
        
        if(!pass1.equals(pass2))
        {
            System.out.println("Wrong Password ");
            gotoOption();
        }
        else
        {
            String qry="update user_profile set password='"+pass1+"' where uid="+uid+" ";
            st.executeUpdate(qry);
            System.out.println("Password Changed Successfully...");
            gotoOption();
        }
      
        } catch (SQLException e) {
            System.out.println(e);
            gotoOption();
        }
    }

    private static void changePrimaryAddress(int uid) throws SQLException 
    {
        try {
             
       String qry1="select paddid from user_profile where uid="+uid;
       rs=st.executeQuery(qry1);
       rs.next();
       int paddid=rs.getInt("paddid");
       
        System.out.print("Enter Address Line 1 ");
        sc.nextLine();
        String add1=sc.nextLine();
        
        System.out.print("Enter Address Line 2 ");
        sc.nextLine();
        String add2=sc.nextLine();
        
        System.out.print("Enter Address Line 3 ");
        sc.nextLine();
        String add3=sc.nextLine();
                  
        System.out.print("Enter Address Type (home/office) ");
        String addtype=sc.next();
        
        System.out.print("Enter City ");
        String city=sc.next();
        
        System.out.print("Enter Pincode ");
        int pin=sc.nextInt();
        
        String qry2="update primary_address set addline1='"+add1+"', addline2='"+add2+"',addline3='"+add3+"',addtype='"+addtype+"',city='"+city+"',pin="+pin+" where paddid="+paddid+"";
      st.executeUpdate(qry2);
    
        System.out.println("Address Successfully Updated ");
        gotoOption();
        } catch (SQLException e) {
            System.out.println(e);
            gotoOption();
        }
    }

    private static void removeSecondaryAddress(int uid) throws SQLException 
    {
        try {
            int paddid;
    String qry1="select paddid from user_profile where uid="+uid;
    rs=st.executeQuery(qry1);
    rs.next();
    paddid=rs.getInt("paddid");
    
   String qry2="select saddid from sec_address where paddid="+paddid;
   rs=st.executeQuery(qry2);
   if(!rs.next())
   {
       System.out.println("No Secondary Address Available ...");
       gotoOption();
   }
   while(rs.next())
   {
       System.out.print(" "+rs.getInt("saddid")+" ,");
   }
        System.out.print("Enter Secondary Address Id From Above ..");
        int saddid=sc.nextInt();
        
    String qry3="select * from sec_address where saddid="+saddid+" and paddid="+paddid;
    rs=st.executeQuery(qry3);
    if(!rs.next())
    {
        System.out.println("");
        System.out.println("Please Enter Currest Address Id ");
        removeSecondaryAddress(uid);
    }
    else
    {
        String qry4="delete from sec_address where saddid="+saddid;
        st.executeUpdate(qry4);
        System.out.println("Address Successfully Removed with id "+saddid);
        gotoOption();
    }
    
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    

    private static void changePrimaryContact(int uid) throws SQLException 
    {
        try {
                String qry1="select pconid from user_profile where uid="+uid;
      rs=st.executeQuery(qry1);
    rs.next();
    int pconid=rs.getInt("pconid");
    
    
        System.out.print("Enter Primary Number ");
        BigInteger pnum=sc.nextBigInteger();
        
        System.out.print("Enter Email Address(xxx@domain.com) ");
         String mail=sc.next();
    
    String qry2="update primary_contact set pnum="+pnum+", email='"+mail+"' where pconid="+pconid;
st.executeUpdate(qry2);
        System.out.println("Data Successfully Updated..");
        gotoOption();
        } catch (SQLException e) {
            System.out.println(e);
        gotoOption();
        }
    }

    private static void removeSecondaryContact(int uid) throws SQLException 
    {
        try {
            
        String qry="select pconid from user_profile where uid="+uid;
        rs=st.executeQuery(qry);
        rs.next();
        int pconid=rs.getInt("pconid");
        
        String qry2="select sconid from sec_contact where pconid="+pconid;
        
        rs=st.executeQuery(qry2);
        if(!rs.next())
        {
            System.out.println("No Secondary Contact Found ..");
            gotoOption();
                    
        }
        while(rs.next())
        {
            System.out.print(" "+rs.getInt("sconid")+", ");
        }
        
        System.out.println("");
        System.out.print("Enter Contact Id From Above ..");
        int sconid=sc.nextInt();
        
        String qry3="select * from sec_contact where sconid="+sconid+" and pconid="+pconid+" ";
    if(!rs.next())
    {
        System.out.println("");
        System.out.println("Please Enter Currest Address Id ");
       gotoOption();
    }
    else
    {
        String qry4="delete from sec_contact where sconid="+sconid;
        st.executeUpdate(qry4);
        System.out.println("Contact Succcessfully Removed with id "+sconid);
         gotoOption();
    }
        } catch (SQLException e) {
            System.out.println(e);
            gotoOption();
        }
    }

    private static void searchByCityCon(String city) throws SQLException {
     try {
            
        String qry="select pconid from user_profile where paddid in(select paddid from primary_address where city='"+city+"')";
        rs=st.executeQuery(qry);
     
        while(rs.next())
        {
             System.out.println("");
            getPconData(rs.getInt("pconid"));
            getSconData(rs.getInt("pconid"));
        
       }
        gotoOption();
        } catch (SQLException e) {
            System.out.println(e);
            gotoOption();
        }}
    
}
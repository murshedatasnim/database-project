package sample.proj;

import java.sql.*;

public class DbAdapter {
    String jdbcUrl="jdbc:postgresql://localhost:5432/magooshgre";
    String username="postgres";
    String password="1234";

    Connection conn=null;
    Statement stat=null;
    ResultSet rs=null;

    public DbAdapter() {

    }

    public void connect()
    {
        try {
            conn= DriverManager.getConnection(jdbcUrl,username,password);
            System.out.println("database connection established");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try {
            if(stat!=null)
                {
                    stat.close();
                }
            if (rs!=null){
                rs.close();
            }
            if (conn!=null)
                conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

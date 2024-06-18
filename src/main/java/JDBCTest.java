import java.sql.*;

public class JDBCTest {

// Sizden bir JDBC testi yapmaniz istendiginde yapacaginiz ilk is
    // DATABASE ACCESS INFORMATION'larini edinmek olmalidir.

    // Ilgili Database Administrator'dan bu bilgileri alacagiz.

	/*
             type	jdbc:mysql
            host/ip	99.99.99.9
            port	9999
            database_name	u999999999_muhammedtec
            username	u99999999_muhammeduser
            password	9999999
     */


    // Verilen bilgilerden bir URL bir USERNAME ve bir de PASSWORD cikartmamiz gerekiyor.


	/*
     URL: "jdbc:mysql://99.99.99.9/u999999999_muhammedtec";
    USERNAME= "u99999999_muhammeduser";
    PASSWORD="9999999";
     */



    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //   1. ADIM OLARAK DOGRU SURUCUYU YUKLEME


        Class.forName("com.mysql.cj.jdbc.Driver");

        //  2. ADIM OLARAK DATABASE BAGLANTISI OLUSTURMAK.

        String URL = "jdbc:mysql://99.99.99.9/u999999999_muhammedtec";
        String USERNAME= "u99999999_muhammeduser";
        String PASSWORD="9999999";

        Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);


        //  3.ADIM OALRAK SQL QUERY'SI OLUSTUR VE CALISTIR.

        String Query = "SELECT * FROM u99999999_muhammeduser.users";
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


        ResultSet resultSet = statement.executeQuery(Query);


        // 4. ADIM OLARAK SONUCLARI ISLE

        resultSet.next();
        System.out.println(resultSet.getString("firstname"));   // birinci satir firstname verisini (Mehmet) getirecek.

        resultSet.next();
        System.out.println(resultSet.getString("firstname"));

        resultSet.next();
        System.out.println(resultSet.getString("lastname"));
        System.out.println(resultSet.getString("email"));
        System.out.println(resultSet.getString("mobile"));

        resultSet.absolute(91);
        System.out.println(resultSet.getString("email"));   // manavgatliyim@gmail.com  = manavgatliyim@gmail.com

        resultSet.next();
        System.out.println(resultSet.getString("firstname"));


        resultSet.absolute(55);
        System.out.println(resultSet.getString("email"));  //mustafaanar@gmail.com


        resultSet.previous();
        System.out.println(resultSet.getString("email"));


        resultSet.first();
        System.out.println(resultSet.getString("email"));  // elff931@gmail.com  = elff931@gmail.com


        resultSet.next();
        System.out.println(resultSet.getInt("id"));


        resultSet.next();
        System.out.println(resultSet.getInt(9));

        // 5. ADIM OLARAK BALANTIYI SONLANDIR.

        resultSet.close();
        statement.close();
        connection.close();


    }

}
package stepDefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import manage.QueryManage;
import utilities.ConfigReader;
import utilities.JDBCReusableMethods;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class StepDefinition {

    String query;
    ResultSet resultSet;
    int rowCount;
    PreparedStatement preparedStatement;
    Faker faker = new Faker();
    int ID;


    QueryManage queryManage = new QueryManage();

    @Given("Database baglantisi kurulur.")
    public void database_baglantisi_kurulur()  {
        JDBCReusableMethods.getConnection();
    }
    @Given("Query01 hazirlanir ve calistirilir.")
    public void query01_hazirlanir_ve_calistirilir() {

        query = queryManage.getQuery01();
        try {
            resultSet =	JDBCReusableMethods.getStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Given("Query01 icin donen sonuclar islenir.")
    public void query01_icin_donen_sonuclar_islenir() throws SQLException {

        resultSet.next();
        int expectedResult= 1;
        int actualResult = resultSet.getInt("user_id");

        System.out.println( "actualresult = " + resultSet.getInt("user_id"));

        assertEquals(expectedResult,actualResult);

    }
    @Given("Database baglantisi kapatilir.")
    public void database_baglantisi_kapatilir() {

        JDBCReusableMethods.closeConnection();

    }



    @Given("Query02 hazirlanir ve calistirilir.")
    public void query02_hazirlanir_ve_calistirilir() throws SQLException {

        resultSet = JDBCReusableMethods.getStatement().executeQuery(queryManage.getQuery02());

    }
    @Given("Query02 icin donen sonuclar islenir.")
    public void query02_icin_donen_sonuclar_islenir() throws SQLException {

        // 5 Minutes
        // 10 Minutes

        List<String> expectedResultList = new ArrayList<>();
        expectedResultList.add("5 Minutes");
        expectedResultList.add("10 Minutes");

        List<String> actualResultList = new ArrayList<>();
        while(resultSet.next()) {

            String name = resultSet.getString("name");
            actualResultList.add(name);
        }

        for( int i =0; i< actualResultList.size(); i++){
            assertEquals(expectedResultList.get(i),actualResultList.get(i));
            System.out.println( i + ". index dogrulandi.");

        }

    }

// ************* UPDATE EXECUTION ***************************

    @Given("UpdateQuery01 hazirlanir ve calistirilir.")
    public void update_query01_hazirlanir_ve_calistirilir() throws SQLException {

        query = queryManage.getUpdateQuery01();
        rowCount = JDBCReusableMethods.updateQuery(query);


    }
    @Given("UpdateQuery01 icin donen sonuclar islenir.")
    public void update_query01_icin_donen_sonuclar_islenir() {

        System.out.println("etkilenen satir sayisi = "+ rowCount);
        int expectedRowCount = 18;
        int actualRowCount = rowCount;
        assertEquals(expectedRowCount,actualRowCount);
    }




// *************** Prepared Statement ile UPDATE QUERY *********

    @Given("PreparedUpdateQuery hazirlanir ve calistirilir.")
    public void prepared_update_query_hazirlanir_ve_calistirilir() throws SQLException {

        query = queryManage.getPreparedUpdateQuery();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // update users set mobile  = ? where username LIKE ?

        preparedStatement.setString(1, "135");
        preparedStatement.setString(2, "%e_");

        rowCount = preparedStatement.executeUpdate();

    }
    @Given("PreparedUpdateQuery icin donen sonuclar islenir.")
    public void prepared_update_query_icin_donen_sonuclar_islenir() {

        int expectedRowCount = 18;
        assertEquals( expectedRowCount , rowCount);

    }
    // ------------------------------------------------------------------------
    @Given("UpdateQuery02 hazirlanir ve calistirilir.")
    public void update_query02_hazirlanir_ve_calistirilir() throws SQLException {
        query = queryManage.getUpdateQuery02();
        rowCount = JDBCReusableMethods.getStatement().executeUpdate(query);

    }
    @Given("UpdateQuery02 icin donen sonuclar islenir.")
    public void update_query02_icin_donen_sonuclar_islenir() {

        int expected = 1;
        assertEquals(expected, rowCount);
    }

// *******************Prepared ile cozum **************************

    @Given("PreparedUpdateQuery02 hazirlanir ve calistirilir.")
    public void prepared_update_query02_hazirlanir_ve_calistirilir() throws SQLException {

        query = queryManage.getPreparedUpdateQuery02();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // update admin_notifications set is_read = ? where id = ?;

        preparedStatement.setInt(1,0);
        preparedStatement.setInt(2,3);

        rowCount = preparedStatement.executeUpdate();

    }
    @Given("PreparedUpdateQuery02 icin donen sonuclar islenir.")
    public void prepared_update_query02_icin_donen_sonuclar_islenir() {

        int expectedResult = 1;
        assertEquals(expectedResult, rowCount);

    }


    // ********************* INSERT QUERY *****************************
    @Given("InsertQuery01 hazirlanir ve calistirilir.")
    public void ınsert_query01_hazirlanir_ve_calistirilir() throws SQLException {

        //insert into admin_password_resets (id,email,token,status) values (?,?,?,?)

        query = queryManage.getPreparedInsertQuery01();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);


        preparedStatement.setInt(1,17);
        preparedStatement.setString(2,"teamyuzotuzbes@gmail.com");
        preparedStatement.setString(3,"t135");
        preparedStatement.setInt(4,9);

        rowCount = preparedStatement.executeUpdate();



    }
    @Given("InsertQuery01 icin donen sonuclar islenir.")
    public void ınsert_query01_icin_donen_sonuclar_islenir() {

        assertEquals(1,rowCount);


    }



    // *********Insert Query 02 ***********

    @Given("InsertQuery02 hazirlanir ve calistirilir.")
    public void ınsert_query02_hazirlanir_ve_calistirilir() throws SQLException {
        query = queryManage.getPreparedInsertQuery02();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // insert into device_tokens (id, user_id, is_app, token) values (?,?,?,?);

        preparedStatement.setInt(1,9);
        preparedStatement.setInt(2,135);
        preparedStatement.setInt(3,1);
        preparedStatement.setString(4,"team135");

        rowCount = preparedStatement.executeUpdate();

    }
    @Given("InsertQuery02 icin donen sonuclar islenir.")
    public void ınsert_query02_icin_donen_sonuclar_islenir() {


        assertEquals(1, rowCount);

    }

// ************* DELETE QUERY01 ************

    @Given("DeleteQuery01 hazirlanir ve calistirilir.")
    public void delete_query01_hazirlanir_ve_calistirilir() throws SQLException {

        query = queryManage.getDeleteQuery01();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        String silinecekID = ConfigReader.getProperty("silinecekID");

        //  delete from update_logs where id= ?;

        preparedStatement.setString(1,silinecekID);

        rowCount = preparedStatement.executeUpdate();



        //--------------------------------------------------------
        String controlQuery = queryManage.getDeleteControlQuery();

        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(controlQuery);

        // select * from update_logs where id = ?;
        preparedStatement.setString(1,silinecekID);
        resultSet = preparedStatement.executeQuery();

    }
    @Given("Istenen datanin silindigi dogrulanir.")
    public void ıstenen_datanin_silindigi_dogrulanir() throws SQLException {

        assertEquals(1, rowCount);  // standart test
        assertFalse(resultSet.next());       // control testi

    }

// ******************* INSERT DELETE SELECT ****************

    @Given("Support_Atachment yablosuna veri kaydedilir.")
    public void support_atachment_yablosuna_veri_kaydedilir() throws SQLException {

        query = queryManage.getSupportAttachmentInsertQuery();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        ID = faker.number().numberBetween(662,700);
        System.out.println("Eklenen data ID'si "+ID);

        // insert into support_attachments (id, support_message_id, attachment,created_at) values (?,?,?,?)

        preparedStatement.setInt(1,ID);
        preparedStatement.setInt(2,456987123);
        preparedStatement.setString(3,"658401a61409c1703149990.png");
        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));


        rowCount = preparedStatement.executeUpdate();

    }
    @Given("DeleteQuery02 hazirlanir ve calistirilir.")
    public void delete_query02_hazirlanir_ve_calistirilir() throws SQLException {


        query = queryManage.getDeleteQuery02();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // delete from support_attachments where id = ?

        preparedStatement.setInt(1,ID);
        rowCount = preparedStatement.executeUpdate();
        System.out.println("Silinen data ID'si "+ ID);


    }
    @Given("Verinin silindigi dogrulanir.")
    public void verinin_silindigi_dogrulanir() throws SQLException {

        assertEquals(1,rowCount);

        query = queryManage.getDeleteControlQuery02();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // select * from support_attachments where id = ?

        preparedStatement.setInt(1,ID);
        resultSet = preparedStatement.executeQuery();

        assertFalse(resultSet.next());

        if (!resultSet.next()){
            System.out.println(ID+" id numarali data bulunamadi");
        }else {
            System.out.println(resultSet.getString("id"));
        }


    }


















}
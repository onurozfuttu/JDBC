package com.kraft.tests.jdbc_tests.day2;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdbc3_ListOfMapExample {

    String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    String dbUsername = "postgres";
    String dbPassword = "password";

    @Test
    public void test1() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select firstname, lastname, salary, jobid from employees");

        List<Map<String, Object>> queryData = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("firstname", "Eren");
        row1.put("lastname", "Çengel");
        row1.put("salary", 100000);
        row1.put("jobid", "QA");

        System.out.println("row1 = " + row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("firstname", "Alperen");
        row2.put("lastname", "Çengel");
        row2.put("salary", 120000);
        row2.put("jobid", "Dev");

        System.out.println("row2 = " + row2);

        queryData.add(row1);
        queryData.add(row2);

        //get the Eren's lastname directly from the list
        System.out.println("queryData.get(0).get(\"lastname\") = " + queryData.get(0).get("lastname"));
        //get the Alperen's salary
        System.out.println("queryData.get(1).get(\"salary\") = " + queryData.get(1).get("salary"));

        System.out.println("");

        //--
        //how to fill out a list of map with the information that comes from database

        List<Map<String, Object>> queryData2 = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //go to first line
        resultSet.first();
        //create a map that will contain the data of first line
        Map<String, Object> row1Dynamic = new HashMap<>();
        row1Dynamic.put(resultSetMetaData.getColumnName(1),resultSet.getString("firstname"));
        row1Dynamic.put(resultSetMetaData.getColumnName(2), resultSet.getString("lastname"));
        row1Dynamic.put(resultSetMetaData.getColumnName(3), resultSet.getString("salary"));
        row1Dynamic.put(resultSetMetaData.getColumnName(4), resultSet.getString("jobid"));
        System.out.println("row1Dynamic = " + row1Dynamic);

        System.out.println("");

        //go to second line
        resultSet.next();
        //create a map that will contain the data of second line
        Map<String, Object> row2Dynamic = new HashMap<>();
        row2Dynamic.put(resultSetMetaData.getColumnName(1),resultSet.getString("firstname"));
        row2Dynamic.put(resultSetMetaData.getColumnName(2), resultSet.getString("lastname"));
        row2Dynamic.put(resultSetMetaData.getColumnName(3), resultSet.getString("salary"));
        row2Dynamic.put(resultSetMetaData.getColumnName(4), resultSet.getString("jobid"));
        System.out.println("row2Dynamic = " + row2Dynamic);

        //add maps into the list of map
        queryData2.add(row1Dynamic);
        queryData2.add(row2Dynamic);

        System.out.println("");

        //get the Alperen's jobid
        System.out.println("queryData2.get(1).get(\"jobid\") = " + queryData2.get(1).get("jobid"));

        resultSet.close();
        statement.close();
        connection.close();
    }
}

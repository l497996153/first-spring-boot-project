package com.example.controller;

import com.example.data.Data;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class MyController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/search")
    String search() {
        return "search";
    }

    @RequestMapping("/insert")
    String insert() {
        return "insert";
    }

    @RequestMapping("/search-results")
    String searchResults(Map<String, Object> model, int caseId) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS case (caseId SERIAL PRIMARY KEY NOT NULL," + 
                                                                "firstname TEXT NOT NULL"+
                                                                "lastname TEXT NOT NULL"+
                                                                "description TEXT NOT NULL)");
            ResultSet rs = stmt.executeQuery("SELECT *  FROM case where caseId = " + caseId );

            ArrayList<String> output = new ArrayList<String>();
            while (rs.next()) {
                output.add("Read from DB: " + rs.getTimestamp("tick"));
            }
            model.put("back", "/search");
            model.put("records", output);
        return "search-results";
        } catch (Exception e) {
            model.put("back", "/search");
            model.put("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/insert-results")
    String insertResults(Map<String, Object> model, Data data) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS case (caseId SERIAL PRIMARY KEY NOT NULL," + 
                                                                "firstname TEXT NOT NULL"+
                                                                "lastname TEXT NOT NULL"+
                                                                "description TEXT NOT NULL)");
            stmt.executeUpdate("INSERT INTO ticks VALUES ("+data.toString()+")");

            Long output = data.getId();
            model.put("back", "/insert");
            model.put("id", output);
        return "insert-results";
        } catch (Exception e) {
            model.put("back", "/insert");
            model.put("message", e.getMessage());
            return "error";
        }
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        }
        else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
}
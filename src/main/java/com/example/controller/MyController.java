package com.example.controller;

import com.example.data.Data;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

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
    private String dbUrl = "postgres://snhfkspyonnaec:c114bb264a12157597ccdb3e1eaf8fc02d767918756aada0185082096c0b00d6@ec2-54-90-13-87.compute-1.amazonaws.com:5432/dfvc4jlk59smbb";

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

    @RequestMapping("/searchResults")
    String searchResults(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

            ArrayList<String> output = new ArrayList<String>();
            while (rs.next()) {
                output.add("Read from DB: " + rs.getTimestamp("tick"));
            }
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cases (caseId SERIAL PRIMARY KEY NOT NULL UNIQUE," + 
                                                                "firstname VARCHAR(15) NOT NULL,"+
                                                                "lastname VARCHAR(15) NOT NULL,"+
                                                                "description VARCHAR(100) NOT NULL)");
            stmt.executeUpdate("INSERT INTO cases(caseId,firstname,lastname,description) VALUES (1,'a','b','c')");
            /*rs = stmt.executeQuery("SELECT *  FROM case where caseId = 1");
            output.add("Read from DB: " + rs.getTimestamp("caseId") + " " + rs.getString("firstname"));*/
            model.put("records", output);
            return "searchResults";
        } catch (Exception e) {
            //model.put("back", "/search");
            model.put("message", e.getMessage());
            return "error";
        }
    }

    /*@GetMapping("/insertResults")
    public String sendForm(Data data) {
        return "insertResults";
    }

    @PostMapping("/insertResults")
    public String insertResults(Data data){
        return "insertResults";
    }*/
    /*@RequestMapping("/results-insert")
    String insertResults(Map<String, Object> model, Data data) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS case (caseId SERIAL PRIMARY KEY NOT NULL UNIQUE," + 
                                                                "firstname TEXT NOT NULL"+
                                                                "lastname TEXT NOT NULL"+
                                                                "description TEXT NOT NULL)");
            stmt.executeUpdate("INSERT INTO ticks VALUES ("+data.toString()+")");

            Integer output = data.getId();
            model.put("back", "/insert");
            model.put("id", output);
            return "/results-insert";
        } catch (Exception e) {
            model.put("back", "/insert");
            model.put("message", e.getMessage());
            return "error";
        }
    }*/

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
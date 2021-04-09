package com.example.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Data {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String description;
    
    public Long getId() {
        return this.id;
    }

    public String getFirst() {
      return this.firstname;
    }
  
    public void setFirst(String firstname) {
      this.firstname = firstname;
    }
  
    public String getLast() {
        return this.lastname;
    }

    public void setLast(String lastname) {
        this.lastname = lastname;
    }

    public String getDes() {
        return this.description;
    }

    public void setDes(String description) {
        this.description = description;
    }

    public String toString(){
        return this.firstname + "," + this.lastname + "," + this.description;
    }
}

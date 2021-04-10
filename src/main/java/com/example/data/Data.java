package com.example.data;

public class Data {
    private String firstname;
    private String lastname;
    private String description;

    public Data() {
    }

    public String getFirstname() {
      return this.firstname;
    }
  
    public void setFirstname(String firstname) {
      this.firstname = firstname;
    }
  
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return "'"+this.getFirstname() + "','" + this.getLastname() + "','" + this.getDescription()+"'";
    }
}

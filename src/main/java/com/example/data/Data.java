package com.example.data;

public class Data {
    private Integer id;
    private String firstname;
    private String lastname;
    private String description;
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return this.getId() + "," + this.getFirst() + "," + this.getLast() + "," + this.getDes();
    }
}

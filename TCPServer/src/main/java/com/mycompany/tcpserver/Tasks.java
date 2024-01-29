/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpserver;

/*
 * Date: 27/10/2021
 * @author Ruby Lennon
 * Student Number: x19128355
 * Class: Tasks.java
 * Project: TCPServer
 * Advanced Programming - Assignment Part 2 - New Hopes Charity Association To-Do List
 * Description - Tasks Object
 */

//Tasks Object Class
public class Tasks {
    //data variable
    private String date;
    private String description;
    
    //constructor 
    public Tasks(){
        date = new String();
        description = new String();
    }
    
    //getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
    
    //override object toString method
    @Override
    public String toString() {
        return "Task{" + "Date = " + date + ", Description = " + description+'}';
    }  
    
}

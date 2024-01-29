/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpserver;

/*
 * Date: 28/10/2021
 * @author Ruby Lennon
 * Student Number: x19128355
 * Class: InvalidDateFormat.java
 * Project: TCPServer
 * Advanced Programming - Assignment Part 2 - New Hopes Charity Association To-Do List
 * Description - Custom exception to be thrown in ClientHandler class when an invalid date value is used by user
 */

//Custom Exception Class
public class InvalidDateFormat extends Exception {
    String msg = "Invalid date format used!";//error message to be returned

    public InvalidDateFormat() {//no args constructor
    }

    public String getErrorMessage() {
        return this.msg;//return the msg string
    }

}

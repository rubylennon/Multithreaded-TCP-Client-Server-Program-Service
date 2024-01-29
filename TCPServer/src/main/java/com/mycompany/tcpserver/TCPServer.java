/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpserver;

//imports
import java.io.*;
import java.net.*;

/*
 * Date: 25/10/2021
 * @author Ruby Lennon
 * Student Number: x19128355
 * Class: TCPServer.java
 * Project: TCPServer
 * Advanced Programming - Assignment Part 2 - New Hopes Charity Association To-Do List
 * Description - TCP Server Class
 */

//@Ref - https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//Tutorial On How to Create a Multithreaded TCP Client-Server Program Service   

//Server class
public class TCPServer {
    public static void main(String[] args) throws IOException {
        // server is listening on port 1234
        ServerSocket serverSocket = new ServerSocket(1234);
        
        //Run an infinite loop to get a client request
        while (true) {
            //set the socket to null
            Socket socket = null;
              
            try {
                //Create Socket object to recieve client request
                socket = serverSocket.accept();
                
                //If a client connects, print the socket
                System.out.println("A new client is connected : " + socket);
                  
                //To obtain input streams
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                //To obtain output streams
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                  
                System.out.println("Assigning new thread for this client");
  
                //Create a new thread 'ClientHandler' object
                Thread thread = new ClientHandler(socket, dataInputStream, dataOutputStream);
  
                //Invoke the thread start method
                thread.start();
                  
            }
            catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}
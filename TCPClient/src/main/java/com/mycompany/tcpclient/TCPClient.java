/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//imports
package com.mycompany.tcpclient;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/*
 * Date: 25/10/2021
 * @author Ruby Lennon
 * Student Number: x19128355
 * Class: TCPClient.java
 * Project: TCPClient
 * Advanced Programming - Assignment Part 2 - New Hopes Charity Association To-Do List
 * Description - TCP Client Class
 */

//@Ref - https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//Tutorial On How to Create a Multithreaded TCP Client-Server Program Service   

// Client class
public class TCPClient {
    //port number
    private static final int PORT = 1234;
    
    //main class
    public static void main(String[] args) throws IOException {
        try {
            //create new Scanner instance
            Scanner scan = new Scanner(System.in);
              
            //Get localhost IP
            InetAddress ia = InetAddress.getLocalHost();
            //Assign local host as IP address
            String ip = ia.getHostAddress();
            
            //Create client socket, establish the connection with server port 1234
            Socket socket = new Socket(ip, PORT);
            
            //Obtain input streams
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //Obtain output streams 
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
      
            //While loop used to perform the exchange of information between client and client handler
            while (true) {
                System.out.println(dataInputStream.readUTF());
                String clientMessage = scan.nextLine();//store user input in a string
                dataOutputStream.writeUTF(clientMessage);          
                  
                //Store the server response in a string
                String serverResponse = dataInputStream.readUTF();
                
                //If the server response equals 'TERMINATE'
                if(serverResponse.equalsIgnoreCase("TERMINATE")){
                    System.out.println("SERVER > " + serverResponse);//print the server response
                    System.out.println("Closing this connection : " + socket);//print which socket is being closed
                    socket.close();//close the socket
                    break;
                } else {//else if the server response does not equal 'TERMINATE'
                    System.out.println("SERVER > " + serverResponse);//Print the server response
                }
                
            }
              
            //Close the resources
            scan.close();
            dataInputStream.close();
            dataOutputStream.close();
        }catch(Exception e){//catch any exceptions
            e.printStackTrace();
        }
    }
}
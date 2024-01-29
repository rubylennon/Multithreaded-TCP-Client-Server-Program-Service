/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpserver;

//imports
import java.util.ArrayList;

/*
 * Date: 07/11/2021
 * @author Ruby Lennon
 * Student Number: x19128355
 * Class: TasksSingleton.java
 * Project: TCPServer
 * Advanced Programming - Assignment Part 2 - New Hopes Charity Association To-Do List
 * Description - Singleton class that initialises only one instance of the ArrayList of Tasks Objects - allows the todo ArrayList resource to be accessed globally by all clients
 */

//@Ref - https://stackoverflow.com/questions/40152454/arraylist-initialized-accessed-using-singleton-class
//Description - How to create a singleton class that initialises only one instance of an ArrayList of Objects and creates a global access point

//Tasks Singleton Design Interface 
public class TasksSingleton  {  
        
        //declare variables
        private static TasksSingleton mInstance;
        private ArrayList <Tasks> todo;
        
        //creates instance
        public static TasksSingleton getInstance() {
            if(mInstance == null)
                mInstance = new TasksSingleton();

            return mInstance;
        }
        
        //initialises todo ArrayList
        private TasksSingleton() {
            todo = new ArrayList <>();
        }
        
        //Retrieve the ArrayList globally from any client connected to the server
        public ArrayList<Tasks> getArray() {
            return this.todo;
        }
        
        //Add elements to the ArrayList
        public void addToArray(Tasks value) {
            todo.add(value);
        }
                
}

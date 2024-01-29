/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tcpserver;

//imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/*
 * Date: 27/10/2021
 * @author Ruby Lennon
 * Student Number: x19128355
 * Class: ClientHandler.java
 * Project: TCPServer
 * Advanced Programming - Assignment Part 2 - New Hopes Charity Association To-Do List
 * Description - TCP Server Client Handling Thread Class
 */

//@Ref - https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
//Tutorial On How to Create a Multithreaded TCP Client-Server Program Service   

//ClientHandler thread class
class ClientHandler extends Thread {
    //declare variables
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;
    private String date, description, formattedDate;
      
    //Overloaded Constructor
    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        date = new String();
        description = new String();
        formattedDate = new String();
    }
  
    //Override the Thread Class run() method
    @Override
    public void run() {
        //declare variable
        String clientMessage;//string to store message recieved from client
        String serverResponse;//string to store response to be sent from server
        boolean addKeywordUsed;//indicates if 'add' action keyword is used
        boolean listKeywordUsed;//indicates if 'list' action keyword is used
        boolean stopKeywordUsed;//indicates if 'stop' action keyword is used
        boolean invalidActionKeywordUsed;//indicates if anvalid action keyword is used
        
        //infinite while loop
        while (true) {
            try {//try to execute the following code
                
                //Ask user what they want to do
                dataOutputStream.writeUTF("\nNew Hopes Charity Association To-Do List\n"
                                            +"Add or list tasks for a specific date...\n"
                                            +"Type Stop to terminate connection.\n");
                
                //Receive and store the answer from the client in a string
                clientMessage = dataInputStream.readUTF();
                
                //Update the boolean values based on the following conditions
                addKeywordUsed = clientMessage.toUpperCase().startsWith("ADD;");//if message starts with 'Add;' then set addKeywordUsed to true, if not set to false
                listKeywordUsed = clientMessage.toUpperCase().startsWith("LIST;");//if message starts with 'List;' then set listKeywordUsed to true, if not set to false
                stopKeywordUsed = clientMessage.equalsIgnoreCase("STOP");//if message equas 'Stop' then set stopKeywordUsed to true, if not set to false
     
                //Set the invalidActionKeywordUsed boolean to false if any of the following booleans equals true
                invalidActionKeywordUsed = !(addKeywordUsed || listKeywordUsed || stopKeywordUsed);
                
                //If the invalid action keyword boolean is true due to an incorrect action being used
                if (invalidActionKeywordUsed == true){
                    dataOutputStream.writeUTF("Invalid action used");//output the following message
                    throw new IncorrectActionException();//throw a new IncorrectActionException exception
                }
                
                //If the message from the client equals stop
                if(clientMessage.equalsIgnoreCase("STOP")){ 
                    dataOutputStream.writeUTF("TERMINATE");//output the server response as 'TERMINATE' 
                    System.out.println("Client " + this.socket + " sends exit...");//print the client socket that sent the exit
                    System.out.println("Closing this connection.");
                    this.socket.close();//close the socket
                    break;
                }
                
                //Create string tokenizer using client message string with semicolon as the delimiter
                StringTokenizer multiTokenizer = new StringTokenizer(clientMessage, ";");

                //Sets tokenCounter count to the total token count in the ClientMessage string
                int tokenCounter = multiTokenizer.countTokens();

                //Declare a boolean that indicates if an invalid task description is used
                boolean invalidTaskDescriptionUsed;
                
                //If the add keyword is used and the tokenCounter count does not equal qty 3 (total required parts of an add action + task description)
                if(addKeywordUsed == true && tokenCounter != 3){
                    invalidTaskDescriptionUsed = true;//then set the invalidActionDescriptionUsed to true
                } else if (listKeywordUsed == true && tokenCounter != 2){ //If the add keyword is used and the tokenCounter count does not equal qty 2
                    invalidTaskDescriptionUsed = true;//then set the invalidActionDescriptionUsed to true
                } else {
                    invalidTaskDescriptionUsed = false;//then set the invalidActionDescriptionUsed to false
                }
                
                //If a valid Add action keyword is used
                if(addKeywordUsed == true) {
                    //If the invalidTaskDescriptionUsed is true (invalid action + action description used)
                    if (invalidTaskDescriptionUsed == true){
                        dataOutputStream.writeUTF("Invalid add request. Please ensure that a task date and description is included in your request");//output that an invalid task description was used
                        throw new InvalidTaskDescription();//throw a new InvalidTaskDescription exception
                    }                    
                    
                    //Split the clientMessage string using a semicolon as a delimiter and store to a string array
                    String s[] = clientMessage.split(";");
                    date = s[1];//set index 1 of the string array as the date variable value
                    description = s[2];//set index 2 of the string array as the description variable value
                    
                    date = (date.trim() );//remove any spaces at the beginning of the date value
                    description = (description.trim() );//remove any spaces at the beginning of the description value
                                        
                    formattedDate = dateFormatter(date);//extracts formatted date using dateFormatter method - also checks if date is in valid format
                    
                    //if the returned formattedDate value equals the following
                    if(formattedDate.equals("Invalid Date Format Used!")){
                        //output the following message
                        dataOutputStream.writeUTF("Invalid date format used. Please check spelling and format. Example of valid date format: '6 October 2021' or '6 oct 21'");
                        throw new InvalidDateFormat();//throw new InvalidDateFormat exception
                    }                   
                    
                    //use the synchronized add method to add a task to the Tasks ArrayList of Object
                    add(formattedDate, description);//add object to arraylist
                    
                    //print the ArrayList toString method in the server console
                    System.out.println("ArrayList toString(): "+TasksSingleton.getInstance().getArray());
                              
                    //run the list method using the formattedDate variable
                    serverResponse = list(formattedDate);//assign the following server response
                    dataOutputStream.writeUTF(serverResponse);//output the serverResponse to the data output stream
                    
                } else if (listKeywordUsed == true){//if a valid List action keyword is used
                    //If the invalidTaskDescriptionUsed is true (invalid action + action description used)
                    if (invalidTaskDescriptionUsed == true){
                        dataOutputStream.writeUTF("Invalid list request. Please ensure that a date is included in your request");//output that an invalid task description was used
                        throw new InvalidTaskDescription();//throw a new InvalidTaskDescription exception
                    }
                    
                    //if the Tasks ArrayList of objects is empty
                    if(TasksSingleton.getInstance().getArray().isEmpty()){
                        serverResponse = "Sorry there are no tasks to list at this time.";//assign the following server response
                        dataOutputStream.writeUTF(serverResponse);//output the serverResponse to the data output stream
                    } else {
                        //Split the clientMessage string using a semicolon as a delimiter and store to a string array
                        String s[] = clientMessage.split(";");
                        date = s[1];//set index 1 of the string array as the date variable value

                        date = (date.trim() );//remove any spaces at the beginning of the date
                                               
                        formattedDate = dateFormatter(date);//extracts formatted date using dateFormatter method - also checks if date is in valid format
                        
                        //if the returned formattedDate value equals the following
                        if(formattedDate.equals("Invalid Date Format Used!")){
                            //output the following message
                            dataOutputStream.writeUTF("Invalid date format used. Please check spelling and format. Example of valid date format: '6 October 2021' or '6 oct 21'");
                            throw new InvalidDateFormat();//throw new InvalidDateFormat exception
                        }                                      
                        
                        //run the synchronised list method using the formattedDate variable
                        serverResponse = list(formattedDate);//assign the following server response
                        dataOutputStream.writeUTF(serverResponse);//output the serverResponse to the data output stream
                        
                    }
                } else {
                    dataOutputStream.writeUTF("Invalid input entered.");//else output invalid input
                }
            
            } catch (IncorrectActionException ex) {//catch IncorrectActionException exception
                System.out.println("Error: " + ex.getErrorMessage());//print errpr message
            } catch (InvalidTaskDescription ex) {//catch InvalidTaskDescription exception
                System.out.println("Error: " + ex.getErrorMessage());//print errpr message
            } catch (InvalidDateFormat ex){//catch InvalidDateFormat exception
                System.out.println("Error: " + ex.getErrorMessage());//print errpr message
            } catch (IOException e) {//catch any remaining exceptions
                e.printStackTrace();
            }
        }
          
        try {
            //Close the resources
            this.dataInputStream.close();
            this.dataOutputStream.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }finally {//finally print the following on the server console to indicate that the 
            System.out.println("Client run method complete.");
        }
    }
    
    //synchronized add method to add a new task to the ToDo List
    //synchronized keyword used to only allow one thread to run this method at a time to ensure concurrency of the application and prevent race conditions
    public synchronized void add(String date, String description){//accepts string date and description
        Tasks a = new Tasks();//create a new instance of the Tasks object
        a.setDate(date);//set the date
        a.setDescription(description);//set the description

        //add object to arraylist
        TasksSingleton.getInstance().getArray().add(a);          
    }
    
    //synchronized list method to list tasks on a specified date in the ToDo list
    //synchronized keyword used to only allow one thread to run this method at a time to ensure concurrency of the application and prevent race conditions
    public synchronized static String list(String dateSearch){//accepts string date
        String s = dateSearch;//string to store the date search term
        int counter = 0;//counter to count tasks with matching date

        //print tasks with match date string
        for(int i = 0;i < TasksSingleton.getInstance().getArray().size();i++){//for loop to loop through Tasks ArrayList
            if(dateSearch.equalsIgnoreCase(TasksSingleton.getInstance().getArray().get(i).getDate())){//if the date search term equals the date value in an element
                s = s + ("; "+TasksSingleton.getInstance().getArray().get(i).getDescription());//append the task description to the string s
                counter ++;//increase the counter if a match is found
            }
        }

        if(counter == 0){//if no matches were found for the date search term
            return "No tasks have been created for this date yet.";//return this statement
        } else {//else
            return s;//return the s string value
        }                   
    } 
 
    //@Ref - https://stackoverflow.com/questions/16871367/java-text-parseexception-unparseable-date
    //Description - How to parse and format a date with a specific locale
    //method to return the date input in a valid date format - also checks that the date input is valid
    public static String dateFormatter(String date){//accepts user input date string
        String formattedDate = "";//string to store the formattedDate value
        
        Locale loc = new Locale("en", "IE");//set the locale to English, Ireland
        
        SimpleDateFormat sdfForParsing = new SimpleDateFormat("d MMMM y", Locale.ENGLISH);//define a SimepleDateFormat for parsing the user input
        SimpleDateFormat sdfForFormatting = new SimpleDateFormat("d MMMM y", loc);//define a SimpleDateFormat for formatting the user input
        
        sdfForParsing.setLenient(false);//set the parsing leniency to false
        
        try {//try to complete the following 
            Date parsedDate = sdfForParsing.parse(date);//parse the input date
            formattedDate = sdfForFormatting.format(parsedDate);//format the parsed date to the predefined format

        } catch (ParseException e) {//if a parsing error occurs this indicates the user did not input a valid date value
            //e.printStackTrace();
            return "Invalid Date Format Used!";//return this statement
        }
        
        //else return the valid formatted date
        return formattedDate;
    }
}
        

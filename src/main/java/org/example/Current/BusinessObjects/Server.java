package org.example.Current.BusinessObjects;

import org.example.Current.DAOs.MySqlCarDao;
import org.example.Current.DTOs.Car;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {

    final int SERVER_PORT_NUMBER = 8888;  // could be any port from 1024 to 49151 (that doesn't clash with other Apps)

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)

            while (true) {
                // System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                // create a new ClientHandler for the requesting client, passing in the socket and client number,
                // pass the handler into a new thread, and start the handler running in the thread.
                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(clientSocket!=null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
}

/**
 * Main Author: Logan Rushe
 */
class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;
    MySqlCarDao ICarDao = new MySqlCarDao();
    JsonConverter jsonHandler = new JsonConverter();

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        try {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * run() method is called by the Thread it is assigned to.
     * This code runs independently of all other threads.
     */
    @Override
    public void run() {
        String request;
        ClientServerCommands commands = new ClientServerCommands();
        DataOutputStream dataOutputStream = null;
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);

                // Implement PROTOCOL
                /**
                 * Main Author: Logan Rushe
                 */
                if (request.startsWith(commands.DisplayCarById))  // so, client wants a Car!
                {
                    String[] requestCommands = request.split(" "); // Split request into parts
                    int id = Integer.parseInt(requestCommands[4]);  // Get id from request
                    Car car = ICarDao.findCarById(id); // Get the requested Car
                    if(car != null) {
                        String carJson = jsonHandler.carObjectToJson(car); // Convert to JSON
                        socketWriter.println(carJson);  // send the car to client (as a JSON string)
                        System.out.printf("Server: JSON of Car sent to client %d\n", clientNumber);
                    } else {
                        socketWriter.println("ERROR: No car found with requested id");
                        System.out.printf("Server: Failed to find the requested id from client %d\n", clientNumber);
                    }
                }
                /**
                 * Main Author: Logan Rushe
                 */
                else if (request.startsWith(commands.GetImagesList)) {
                    String imagesListJson = jsonHandler.stringListToJson(commands.imagesList);
                    socketWriter.println(imagesListJson);
                    System.out.printf("Server: JSON of images List sent to client %d\n", clientNumber);
                }
                /**
                 * Main Author: Logan Rushe
                 */
                else if (request.startsWith(commands.GetImage)) {
                    try {
                        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        System.out.printf("Preparing to send file to Client %d...\n", clientNumber);

                        String[] requestCommands = request.split(" "); // Split request into parts
                        String imageName = "Images/" + requestCommands[2];  // Define image path

                        int bytes = 0;
                        // Open the File at the specified location (path)
                        File file = new File(imageName);
                        FileInputStream fileInputStream = new FileInputStream(file);

                        System.out.printf("Sending the file to Client %d...\n", clientNumber);
                        socketWriter.println("Sending...");

                        // send the length (in bytes) of the file to the server
                        dataOutputStream.writeLong(file.length());

                        // Break file into chunks
                        byte[] buffer = new byte[4 * 1024]; // 4 kilobyte buffer

                        // read bytes from file into the buffer until buffer is full, or we reached end of file
                        while ((bytes = fileInputStream.read(buffer)) != -1) {
                            // Send the buffer contents to Client Socket, along with the count of the number of bytes
                            dataOutputStream.write(buffer, 0, bytes);
                            dataOutputStream.flush();   // force the data into the stream
                        }

                        System.out.printf("File %s sent to Client %d\n", requestCommands[2], clientNumber);

                        // close the file
                        fileInputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        socketWriter.println("ERROR: File not found");
                    }
                }
                /**
                 * Main Author: Logan Rushe
                 */
                else if (request.startsWith("Exit")) {
                    socketWriter.println("Goodbye!");
                    System.out.printf("Server: Client %d is quitting.\n", clientNumber);
                } else {
                    socketWriter.println("ERROR: Unknown Request. Please try again.");
                    System.out.printf("Server message: Invalid request from client %d.\n", clientNumber);
                }
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            this.socketWriter.close();
            try {
                this.socketReader.close();
                this.clientSocket.close();
                if(dataOutputStream != null) { dataOutputStream.close(); }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }
}



package main.java;

import java.io.*;
import java.net.*;

public class SThread extends Thread {
	private Object[][] rTable; //routing table
	private PrintWriter out, outTo; //writers (for writing back to the machine and to destination)
	private BufferedReader in; //reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; //communication strings
	private Socket outSocket; //socket for communicating with a destination
	private int ind; //index in the routing table

	//Constructor
	SThread(Object[][] table, Socket toClient, int index) throws IOException {
		out = new PrintWriter(toClient.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
		addr = toClient.getInetAddress().getHostAddress();
		rTable = table;
		rTable[index][0] = addr; //IP addresses
		rTable[index][1] = toClient; //sockets for communication
		ind = index;
	}
	
	//Run method (will run for each machine that connects to the ServerRouter)
	public void run() {
		try {
			//Initial sends/receives
			destination = in.readLine(); //initial read (the destination for writing)
			System.out.println("Forwarding to " + destination);
			out.println("Connected to the router."); //confirmation of connection
		
			//Waits 10 seconds to let the routing table fill with all machines' information
			try {
				sleep(10000);
			} catch(InterruptedException ie) {
				System.out.println("Thread interrupted");
			}
		
			//Loops through the routing table to find the destination
			for(int i = 0; i < 10; i++) {
				if(destination.equals(rTable[i][0])) {
					outSocket = (Socket) rTable[i][1]; //gets the socket for communication from the table
					System.out.println("Found destination: " + destination);
					outTo = new PrintWriter(outSocket.getOutputStream(), true); //assigns a writer
				}
			}
		
			//Communication loop
			while((inputLine = in.readLine()) != null) {
				System.out.println("Client/Server said: " + inputLine);
				if(inputLine.equals("Bye.")) break; // exit statement

				outputLine = inputLine; //passes the input from the machine to the output string for the destination
				if(outSocket != null) {
					outTo.println(outputLine); //writes to the destination
				}			
			}	 
		} catch (IOException e) {
            System.err.println("Could not listen to socket.");
            System.exit(1);
        }
	}
}
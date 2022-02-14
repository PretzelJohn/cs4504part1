package main.java;

import org.yaml.snakeyaml.Yaml;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServerRouter {
	private long t;

	public TCPServerRouter() {
		this.t = 0;
	}

	public void addTime(long t) {
		this.t += t;
	}

	public long getTime() {
		return this.t;
	}

	public static void main(String[] args) throws IOException {
		Socket clientSocket = null; //socket for the thread
        Object[][] routingTable = new Object[10][2]; //routing table

		//Load IP addresses from config.yml file
		Yaml yaml = new Yaml();	//Create a new YAML instance
		Map<String, Object> config = yaml.load(new FileReader("config.yml")); //Load config as yaml
		int sockNum = (int)config.get("router-port"); //port number
		int ind = 0; //index in the routing table
		boolean running = true; //status

		//Accepting connections
        ServerSocket serverSocket = null; //server socket for accepting connections
        try {
			serverSocket = new ServerSocket(sockNum);
			System.out.println("ServerRouter is Listening on port: "+sockNum+".");
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+sockNum+".");
            System.exit(1);
        }

		//Creating threads with accepted connections
		TCPServerRouter router = new TCPServerRouter();
		long time = 0;
		while(running) {
			try {
				clientSocket = serverSocket.accept();
				SThread t = new SThread(router, routingTable, clientSocket, ind); //creates a thread with a random port
				time += t.lookup();
				t.start(); //starts the thread
				ind++; //increments the index
				System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
				System.out.println("Avg lookup time: "+((double)time/((double)ind*1000000.0))+" ms");
			} catch (IOException e) {
				System.err.println("Client/Server failed to connect.");
				System.exit(1);
			}
		}

		//closing connections
		clientSocket.close();
        serverSocket.close();
    }
}
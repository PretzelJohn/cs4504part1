package main.java;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.*;
import java.util.Map;

public class TCPClient {
    public static void main(String[] args) throws IOException {
		//Variables for setting up connection and communication
		Socket socket = null; //socket to connect with ServerRouter
		PrintWriter out = null; //for writing to ServerRouter
		BufferedReader in = null; //for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); //Client machine's IP

		//Load IP addresses from config.yml file
		Yaml yaml = new Yaml();	//Create a new YAML instance
		Map<String, Object> config = yaml.load(new FileReader("config.yml")); //load config as yaml
		String routerName = (String)config.get("router-ip"); //ServerRouter host name
		int sockNum = (int)config.get("router-port"); //port number
		String destination = (String)config.get("destination"); //destination IP (Client)

		//Tries to connect to the ServerRouter
		try {
			socket = new Socket(routerName, sockNum);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about router: " + routerName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + routerName);
			System.exit(1);
		}

		//Read files from files directory
		File fileDir = new File("files");
		File[] fileList = fileDir.listFiles();
		if(fileList == null) {
			System.err.println("Couldn't get files folder!");
			System.exit(1);
		}

		long size = 0;
		for(File file : fileList) {
			//TODO: Add up the size of each file and take avg
			System.out.println(file.getName());
		}

		//TODO: Add each file to a list
		Reader reader = new FileReader("files/file.txt");
		BufferedReader fromFile = new BufferedReader(reader); //reader for the string file

		//Variables for message passing
		String fromServer; //messages received from router
		String fromUser; //messages sent to ServerRouter
		long t0, t1, t; //timer variables

		//Communication process (initial sends/receives)
		out.println(destination); //initial send (IP of the destination Server)
		fromServer = in.readLine(); //initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromServer);
		out.println(host); //client sends the IP of its machine as initial send
		t0 = System.currentTimeMillis();

		//Communication while loop
		long totalTime = 0;
		int numCycles = 0;
		while((fromServer = in.readLine()) != null) {
			//Receives responses from server
			System.out.println("Server: " + fromServer);
			t1 = System.currentTimeMillis();
			if(fromServer.equals("Bye.")) break; //exit statement
			t = t1 - t0; //calculates cycle time between messages
			totalTime += t;
			numCycles++;
			System.out.println("Cycle time: " + t);

			//Send messages from files to server
			fromUser = fromFile.readLine(); //reading strings from a file
			if(fromUser != null) {
				System.out.println("Client: " + fromUser);
				out.println(fromUser); //sending the strings to the Server via ServerRouter
				t0 = System.currentTimeMillis();
			} else {
				//Go to next file
				//If there is no next file, do nothing
			}

		}

		System.out.println("Average Cycle Time: "+totalTime/(double)numCycles);

		//Closing connections
		out.close();
		in.close();
		socket.close();
    }
}

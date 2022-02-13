package main.java;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.*;
import java.util.Map;

public class TCPServer {
    public static void main(String[] args) throws IOException {
      	//Variables for setting up connection and communication
        Socket socket = null; //socket to connect with ServerRouter
        PrintWriter out = null; //for writing to ServerRouter
        BufferedReader in = null; //for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); //Server machine's IP

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

      	//Variables for message passing
        String fromServer; //messages sent to ServerRouter
        String fromClient; //messages received from ServerRouter

		//Communication process (initial sends/receives)
		out.println(destination); //initial send (IP of the destination Client)
		fromClient = in.readLine(); //initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromClient);

		//Communication while loop
      	while((fromClient = in.readLine()) != null) {
            System.out.println("Client said: " + fromClient);
            if(fromClient.equals("Bye.")) break; //exit statement

			fromServer = fromClient.toUpperCase(); //converting received message to upper case
			System.out.println("Server said: " + fromServer);
            out.println(fromServer); //sending the converted message back to the Client via ServerRouter
        }

        //Closing connections
        out.close();
        in.close();
        socket.close();
    }
}

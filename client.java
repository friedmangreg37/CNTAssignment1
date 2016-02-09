import java.io.*;
import java.net.*;

class client {
	public static void main(String argv[]) throws Exception {
		String input;
		String response;
		//input stream from user:
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		//create client socket and connect to server:
		Socket clientSocket = new Socket("localhost", 4893);
		//create output stream attacked to socket:
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		//create input stream attacked to socket:
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		response = inFromServer.readLine(); 	//read line from server (should be "Hello!")
		System.out.println("receive: " + response);		//print the message

		//loop until user says to quit:
		//while(true) {

		//}

		clientSocket.close();	//close the socket
 	}	
}
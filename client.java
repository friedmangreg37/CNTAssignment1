import java.io.*;
import java.net.*;

class client {
	public static void main(String argv[]) throws Exception {
		String input;		//String input from the user
		String response;	//String response from the server
		int answer;		//integer answer to the command

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
		while(true) {
			input = inFromUser.readLine();		//read line from user
			outToServer.writeBytes(input + '\n');	//send this input to the server
			response = inFromServer.readLine();		//get the response from the server
			answer = Integer.parseInt(response);	//convert to an int
			if(answer >= 0) {		//if >= 0 then not an error so just print result
				System.out.println("receive: " + answer);
			}else if(answer == -1) {
				System.out.println("receive: incorrect operation command");
			}else if(answer == -2) {
				System.out.println("receive: number of inputs is less than two");
			}else if(answer == -3) {
				System.out.println("receive: number of inputs is more than four");
			}else if(answer == -4) {
				System.out.println("receive: one or more of the inputs contain(s) non-number(s)");
			}else if(answer == -5) {
				//error code for exit, for print this, then close the socket:
				System.out.println("receive: exit");
				break;
			}else {
				System.out.println("receive: invalid error code");	//should never happen
			}
		}
		clientSocket.close();
 	}	
}
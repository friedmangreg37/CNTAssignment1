import java.io.*;
import java.net.*;

class client {
	public static void main(String argv[]) throws Exception {
		String input;		//String input from the user
		String response;	//String response from the server
		int answer;		//integer answer to the command
		String url;		//the server url
		int portNumber;		//the port number

		if(argv.length < 2) {
			//requires user the supply url and port number at runtime
			throw new Exception("Please supply IP address and port number");
		}
		url = argv[0];		//get the url from the first argument supplied
		portNumber = Integer.parseInt(argv[1]);	//get the port number from the second argument

		try {
			//input stream from user:
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			//create client socket and connect to server:
			Socket clientSocket = new Socket(url, portNumber);
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
				}
				//error codes:
				else if(answer == -1) {
					System.out.println("receive: incorrect operation command");
				}else if(answer == -2) {
					System.out.println("receive: number of inputs is less than two");
				}else if(answer == -3) {
					System.out.println("receive: number of inputs is more than four");
				}else if(answer == -4) {
					System.out.println("receive: one or more of the inputs contain(s) non-number(s)");
				}else if(answer == -5) {
					//error code for exit, so print this, then close the socket:
					System.out.println("receive: exit");
					break;
				}else {
					System.out.println("receive: invalid error code");	//should never happen
				}
			}
			clientSocket.close();	//disconnect the client when we break from the loop
		}catch(SocketException e) {
			//when an exception comes because program terminated, do nothing, but let client code exit
		}
 	}	
}
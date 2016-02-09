import java.io.*;
import java.net.*;

class server {
	public static void main(String argv[]) throws Exception {
     	String clientMessage;
        String response;
        ServerSocket welcomeSocket = new ServerSocket(4893);	//create socket at port 4893

        while(true) {
            //welcome socket waits for contact from client:
         	Socket connectionSocket = welcomeSocket.accept();
            //get IP address of connecting client:
            String clientIP = connectionSocket.getInetAddress().getHostAddress();
            //print message stating connection made:
            System.out.println("get connection from " + clientIP);
            //create input stream attached to socket:
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            //create output stream attached to socket:
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            //send initial "Hello" response to confirm connection:
            response = "Hello!\n";
            outToClient.writeBytes(response);

            //loop until client exits:
            while(true) {
                clientMessage = inFromClient.readLine();    //get the command from the client
                /*System.out.println()
                clientMessage = clientMessage.trim();
                if(clientMessage == "bye") {
                    response = "-5\n";
                    outToClient.writeBytes(response);
                    break;
                }
                String words[] = clientMessage.split(" ");
                if(words[0] == "add") {
                    int answer = Integer.parseInt(words[1]);
                    for(int i = 2; i < words.length; i++) {
                        answer += Integer.parseInt(words[i]);
                    }
                    response = Integer.toString(answer);
                }
                else {*/
                   response = "-5\n";
                //}
                outToClient.writeBytes(response);
            }
        }
    }
}
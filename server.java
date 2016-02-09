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

            response = "Hello!\n";
            outToClient.writeBytes(response);


        }
    }
}
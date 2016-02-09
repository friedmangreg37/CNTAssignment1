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
            response = "Hello!";
            outToClient.writeBytes(response + '\n');

            //loop until client exits:
            while(true) {
                clientMessage = inFromClient.readLine();    //get the command from the client
                System.out.print("get: " + clientMessage + ", return: ");
                if(clientMessage.equals("bye")) {   //client wants to exit
                    //so set response accordingly, print it and send to client
                    response = "-5";
                    System.out.println(response);
                    outToClient.writeBytes(response + '\n');
                    break;      //then exit loop since client will disconnect
                }
                //get an array of the words send from client:
                String words[] = clientMessage.split(" ");
                //add command:
                if(words[0].equals("add")) {
                    try {
                        //try converting each operand to int:
                        int answer = Integer.parseInt(words[1]);
                        for(int i = 2; i < words.length; i++) {
                            //and add it to the running total:
                            answer += Integer.parseInt(words[i]);
                        }
                        //convert answer back to string:
                        response = Integer.toString(answer);
                    }catch(NumberFormatException e) {
                        //one or more operands wasn't a number, so send corresponding message:
                        response = "-4";
                    }
                }
                //subtract command:
                else if(words[0].equals("subtract")) {
                    try {
                        //try converting each operand to int:
                        int answer = Integer.parseInt(words[1]);
                        for(int i = 2; i < words.length; i++) {
                            //and subtract it from the running total:
                            answer -= Integer.parseInt(words[i]);
                        }
                        //convert answer back to string:
                        response = Integer.toString(answer);
                    }catch(NumberFormatException e) {
                        //one or more operands wasn't a number, so send corresponding message:
                        response = "-4";
                    }
                }
                //multiply command:
                else if(words[0].equals("multiply")) {
                    try {
                        //try convert each operand to int:
                        int answer = Integer.parseInt(words[1]);
                        for(int i = 2; i < words.length; i++) {
                            //and multiply to running total:
                            answer *= Integer.parseInt(words[i]);
                        }
                        //convert answer back to string:
                        response = Integer.toString(answer);
                    }catch(NumberFormatException e) {
                        //one or more operands wasn't a number, so send corresponding message:
                        response = "-4";
                    }
                }
                else {
                    //if we're here, the first word wasn't a valid operation command
                   response = "-1";
                }
                System.out.println(response);   //print the result to return
                outToClient.writeBytes(response + '\n');    //and send it to the client
            }
        }
    }
}
import java.io.*;
import java.net.*;

class server {
	public static void main(String argv[]) throws Exception {
     	String clientMessage;     //message to be received from client
        String response;        //response to be sent to client
        int portNumber;         //the port number

        if(argv.length < 1) {
            //requires the user to supply the port number at runtime
            throw new Exception("Please supply the port number");
        }
        portNumber = Integer.parseInt(argv[0]);     //get the port number from the arguments

        ServerSocket socket = new ServerSocket(portNumber);	//create socket at port 4893

        while(true) {
            //socket waits for contact from client:
         	Socket clientSocket = socket.accept();
            //get IP address of connecting client:
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            //print message stating connection made:
            System.out.println("get connection from " + clientIP);
            //create input stream attached to socket:
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //create output stream attached to socket:
            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

            //send initial "Hello" response to confirm connection:
            response = "Hello!";
            outToClient.writeBytes(response + '\n');

            //loop until client exits:
            while(true) {
                clientMessage = inFromClient.readLine();    //get the command from the client
                clientMessage = clientMessage.trim();       //trim off whitespace at beginning or end
                System.out.print("get: " + clientMessage + ", return: ");
                if(clientMessage.equals("bye")) {   //client wants to exit
                    //so set response accordingly, print it and send to client
                    response = "-5";
                    System.out.println(response);
                    outToClient.writeBytes(response + '\n');
                    break;      //then exit loop since client will disconnect
                }else if(clientMessage.equals("terminate")) {   //client wants to terminate all
                    //so set the response accordingly, print it and send to client
                    response = "-5";
                    System.out.println(response);
                    outToClient.writeBytes(response + '\n');
                    socket.close();     //close the server socket
                    System.exit(0);     //end all processes
                }
                //get an array of the words send from client:
                String words[] = clientMessage.split(" ");
                if(words.length < 3) {
                    //less than two inputs:
                    response = "-2";
                }else if(words.length > 5) {
                    //more than four inputs:
                    response = "-3";
                }else {
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
                }
                //if the first word isn't a valid command, set this error message (supercedes other errors):
                if((!words[0].equals("add")) && (!words[0].equals("subtract")) && (!words[0].equals("multiply"))) {
                    response = "-1";
                }
                System.out.println(response);   //print the result to return
                outToClient.writeBytes(response + '\n');    //and send it to the client
            }
        }
    }
}
package SCChallengeNetworking;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
  static ServerSocket serverSocket;
  static ArrayList<Server> connections = new ArrayList<>();
  Socket socket;
  DataOutputStream stream;

  public Server() throws IOException {
    this.socket = this.serverSocket.accept();
  }

  public static void main(String[] args) throws IOException {
    InetSocketAddress address;

    // If port provided in args, use it, otherwise default to 3000
    if (args.length > 0) {
      address = new InetSocketAddress(Integer.parseInt(args[0]));
    } else {
      address = new InetSocketAddress(3000);
    }

    serverSocket = new ServerSocket();
    serverSocket.bind(address);

    while (true) {
      Server s = new Server();
      Thread t = new Thread(s);
      connections.add(s);
      t.start();
    }
  }

  public void run() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      stream = new DataOutputStream(socket.getOutputStream());

      String username = reader.readLine();

      String welcomeMessage = "[SYSTEM] " + username + " has joined the chat";

      // We want the welcome message to appear in the server logs and for every client
      broadcast(welcomeMessage);
      System.out.println(welcomeMessage);

      while (true) {
        int buff = reader.read();

        // If the reader returns -1, the connection has been closed so we stop the loop
        if (buff == -1) break;

        // .readLine() won't give us the whole line because we called .read() earlier so we join the two
        String message = (char) buff + reader.readLine();

        String displayMsg = "[" + username + "] " + message;

        // We want the message to appear in the server logs and for every client
        broadcast(displayMsg);
        System.out.println(displayMsg);
      }

      // Remove the current connection from the current connections list because it just closed
      connections.remove(this);

      String leaveMessage = "[SYSTEM] " + username + " has left the chat";

      // We want the leave message to appear in the server logs and for every client
      broadcast(leaveMessage);
      System.out.println(leaveMessage);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  static void broadcast(String message) throws IOException {
    // To broadcast a message we loop through every connection and write the message to their output stream
    for (Server s : connections) {
      s.stream.writeBytes(message + "\n");
      s.stream.flush();
    }
  }
}

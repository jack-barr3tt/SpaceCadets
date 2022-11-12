package SCChallengeNetworking;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
  private ArrayList<String> messages = new ArrayList<>();
  private InetSocketAddress address;

  public Client() throws IOException {
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Enter an address to connect to: ");
    String serverAddress = consoleReader.readLine();

    if (serverAddress.length() > 0) {
      // If an address is entered, split the hostname and port and use them
      String hostname = serverAddress.split(":")[0];
      Integer port = Integer.parseInt(serverAddress.split(":")[1]);
      address = new InetSocketAddress(hostname, port);
    } else {
      // If no address is entered, default to localhost:3000
      address = new InetSocketAddress(3000);
    }
    Socket socket = new Socket();
    socket.connect(address);

    DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    System.out.print("Please choose a username: ");

    String username = consoleReader.readLine();

    stream.writeBytes(username + "\n");
    stream.flush();

    clear();
    System.out.println("======== " + username + " ========");

    // Run the message input handler in a separate thread so new messages can still appear
    Thread inputThread = new Thread(new InputHandler(stream));
    inputThread.start();

    while (true) {
      int buff = reader.read();

      // If the reader returns -1, the connection has been closed so we stop the loop
      if (buff == -1) break;

      // .readLine() won't give us the whole line because we called .read() earlier so we join the two
      String message = (char) buff + reader.readLine();

      messages.add(message);

      clear();
      System.out.println("======== " + username + " ========");

      // Render every message
      for (String msg : messages) {
        System.out.println(msg);
      }
      // The input reader will still be open in the other thread so we just display the prompt again
      System.out.print("Enter a message: ");
    }

    System.out.println("Lost connection from server");

    System.exit(0);
  }

  private static class InputHandler implements Runnable {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private DataOutputStream stream;

    public InputHandler(DataOutputStream stream) {
      this.stream = stream;
    }

    public void run() {
      while (true) {
        try {
          System.out.print("Enter a message: ");
          String msg = reader.readLine();

          if (Objects.equals(msg, "quit")) System.exit(0);

          stream.writeBytes(msg + "\n");
          stream.flush();
        } catch (IOException e) {
          System.err.println(e);
        }
      }
    }
  }

  private static void clear() {
    // Clears the console when used in the default Linux terminal
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void main(String[] args) throws IOException {
    new Client();
  }
}

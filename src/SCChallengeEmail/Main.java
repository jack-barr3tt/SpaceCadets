package SCChallengeEmail;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static String GetPerson(String id) throws Exception {
        URL url = new URL("https://www.ecs.soton.ac.uk/people/" + id);

        BufferedReader dataReader = new BufferedReader(new InputStreamReader(url.openStream()));

        Optional<String> line = dataReader.lines().filter(l -> l.contains("og:title")).findFirst();

        Matcher matcher = Pattern.compile("(?<=content=\\\").+(?=\\\")").matcher(line + "");

        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new Exception("Not found");
        }
    }

    public static String GetInput() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.print("Enter the email ID of the person (or \"QUIT\" to quit): ");

            try {
                String input = GetInput();

                if(input == "QUIT") {
                    break;
                }

                try {
                    System.out.println(GetPerson(input));
                } catch (Exception f) {
                    System.out.println("Person not found");
                }
            } catch (IOException e) {
                break;
            }

        }
    }
}
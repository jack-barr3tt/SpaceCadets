package SCChallengeBareBones;

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Reader {
  private FileReader fileReader;
  private List<String> lines = new ArrayList<>();
  private int nextIndex = 0;

  public void getFile(String baseName) throws FileNotFoundException {
    Pattern fileCheck = Pattern.compile("\\w\\.\\w");

    String filename = baseName;
    if (!fileCheck.matcher(filename).find()) {
      filename += ".txt";
    }

    this.fileReader = new FileReader(filename);
  }

  private String readFromFile() throws IOException {
    String temp = "";
    while (true) {
      int next = this.fileReader.read();

      if (next == -1) {
        if (temp == "") {
          throw new IOException("End of file");
        } else {
          break;
        }
      }

      if ((char) next == ';' || temp.endsWith("do")) {
        break;
      }

      if ((char) next == '\n') {
        continue;
      }

      if (temp == "" && (char) next == ' ') {
        continue;
      }

      temp += (char) next;
    }
    return temp;
  }

  public String[] nextLine() throws IOException {
    String line;
    Boolean add = false;
    if (lines.size() > nextIndex) {
      line = lines.get(nextIndex);
    } else {
      line = readFromFile();
      lines.add(line);
    }
    nextIndex++;
    String[] parts = line.split(" ");
    System.out.println("Instruction: " + line);
    return parts;
  }

  public void rewind(int lineNo) {
    this.nextIndex = lineNo;
  }

  public int getCurrentLine() {
    if (lines.size() == 0) {
      return 0;
    } else {
      return this.nextIndex;
    }
  }
}

package SCChallengeBareBonesExtended;

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Reader {
  private FileReader fileReader;
  private List<String> lines = new ArrayList<>();
  private int nextIndex = 0;

  private enum CommentState {
    None,
    Line,
    Fragment
  }

  private CommentState currentCommentState = CommentState.None;

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
    char next = 0, prev = 0;
    while (true) {
      int nextInt = this.fileReader.read();

      if (nextInt == -1) {
        if (temp == "") {
          throw new IOException("End of file");
        } else {
          break;
        }
      }
      prev = next;
      next = (char) nextInt;

      System.out.println(prev + " " + next);

      // Start comments
      if(prev == '/') {
        if(next == '/') {
          currentCommentState = CommentState.Line;
        }

        if(next == '*') {
          currentCommentState = CommentState.Fragment;
        }

        if(next == '/' || next == '*') {
          temp = temp.substring(0, temp.length() - 1);
        }
      }

      // End fragment comments
      if(prev == '*' && next == '/') {
        currentCommentState = CommentState.None;
        continue;
      }

      // If we hit a newline we need to check if a line comment is being made
      if (next == '\n') {
        if (currentCommentState == CommentState.Line) currentCommentState = CommentState.None;
        continue;
      }

      // If a comment is being made we don't need to go further
      if (currentCommentState != CommentState.None) {
        continue;
      }

      // Ignore space
      if (temp == "" && next == ' ') {
        continue;
      }

      // When a semicolon is reached we can stop looking for more characters
      if (next == ';') {
        break;
      }

      temp += next;
    }
    System.out.println(temp.length());
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

  public void endBlock() throws IOException {
    String op = nextLine()[0];
    while (!Objects.equals(op, "end") && !Objects.equals(op, "else")) {
      op = nextLine()[0];
    }
  }
}

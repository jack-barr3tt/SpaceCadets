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
    while (true) {
      int next = this.fileReader.read();

      if (next == -1) {
        if (temp == "") {
          throw new IOException("End of file");
        } else {
          break;
        }
      }

      if (temp.endsWith("/")) {
        if ((char) next == '/') {
          currentCommentState = CommentState.Line;
        }

        if ((char) next == '*') {
          if (currentCommentState == CommentState.None) {
            currentCommentState = CommentState.Fragment;
          }
        }

        temp = temp.substring(0, temp.length() - 1);
      }

      if ((char) next == '/' && temp.endsWith("*")) {
        if (currentCommentState == CommentState.Fragment) {
          currentCommentState = CommentState.None;
          temp = temp.substring(0, temp.length() - 1);
          continue;
        }
      }

      if ((char) next == ';' || temp.endsWith("do") || temp.endsWith("then")) {
        break;
      }

      if ((char) next == '\n') {
        if (currentCommentState == CommentState.Line) currentCommentState = CommentState.None;
        continue;
      }

      if (temp == "" && (char) next == ' ') {
        continue;
      }

      if (currentCommentState != CommentState.None && ((char) next) != '*') {
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

  public void endBlock() throws IOException {
    String op = nextLine()[0];
    while (!Objects.equals(op, "end") && !Objects.equals(op, "else")) {
      op = nextLine()[0];
    }
  }
}

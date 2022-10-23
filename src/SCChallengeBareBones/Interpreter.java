package SCChallengeBareBones;

import java.util.*;

public class Interpreter {
  private Reader reader;
  private Memory memory;
  private int startLine;

  public Interpreter(Reader reader, Memory memory) {
    this.memory = memory;

    this.reader = reader;

    this.startLine = reader.getCurrentLine();
  }

  public void run() throws Exception {
    while (true) {
      String[] parts = reader.nextLine();

      switch (parts[0]) {
        case "clear":
          memory.clearValue(parts[1]);
          break;
        case "incr":
          memory.incValue(parts[1]);
          break;
        case "decr":
          memory.decValue(parts[1]);
          break;
        case "while":
          Interpreter subBlock = new Interpreter(reader, memory);
          while (!Objects.equals(memory.get(parts[1]), Integer.parseInt(parts[3]))) {
            subBlock.rewind();
          }
          break;
        case "end":
          memory.printMemoryState();
          return;
        default:
          throw new Exception("Not implemented yet.");
      }
      memory.printMemoryState();
    }
  }

  public void rewind() throws Exception {
    reader.rewind(startLine);
    run();
  }
}

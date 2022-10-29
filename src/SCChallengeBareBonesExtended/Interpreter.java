package SCChallengeBareBonesExtended;

import java.util.*;

import static java.lang.Integer.parseInt;

public class Interpreter {
  private Reader reader;
  private Memory memory;
  private int startLine;

  public Interpreter(Reader reader, Memory memory) {
    this.memory = memory;

    this.reader = reader;

    this.startLine = reader.getCurrentLine();
  }

  int address(String key) {
    if (memory.hasKey(key)) {
      return memory.get(key);
    }else{
      return parseInt(key);
    }
  }

  boolean condition(String v1, String op, String v2) {
    switch(op) {
      case "not":
        return address(v1) != address(v2);
      case "eq":
        return address(v1) == address(v2);
      case "gt":
        return address(v1) > address(v2);
      case "lt":
        return address(v1) < address(v2);
      default:
        return false;
    }
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
        case "set":
          memory.setValue(parts[1], address(parts[2]));
          break;
        case "add":
          memory.setValue(parts[1], address(parts[2]) + address(parts[3]));
          break;
        case "subtract":
          memory.setValue(parts[1], address(parts[2]) - address(parts[3]));
          break;
        case "multiply":
          memory.setValue(parts[1], address(parts[2]) * address(parts[3]));
          break;
        case "divide":
          memory.setValue(parts[1], address(parts[2]) / address(parts[3]));
          break;
        case "while":
          Interpreter whileBlock = new Interpreter(reader, memory);
          while (condition(parts[1],parts[2],parts[3])) {
            whileBlock.rewind();
          }
          break;
        case "if":
          if (!condition(parts[1],parts[2],parts[3])) {
            reader.endBlock();
          }
          Interpreter ifBlock = new Interpreter(reader,memory);
          break;
        case "else":
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

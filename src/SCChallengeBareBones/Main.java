package SCChallengeBareBones;

public class Main {
  public static void main(String[] args) throws Exception {
    if (args.length < 1 || args[0] == "") {
      System.out.println("No filename provided");
      return;
    }

    Reader reader = new Reader();
    reader.getFile(args[0]);

    Memory memory = new Memory();

    Interpreter interpreter = new Interpreter(reader, memory);

    try {
      interpreter.run();
    }catch(Exception e) {
      if(e.getMessage() != "End of file") throw e;
    }
  }
}

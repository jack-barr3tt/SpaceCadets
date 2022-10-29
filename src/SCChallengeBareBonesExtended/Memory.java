package SCChallengeBareBonesExtended;

import java.util.*;

public class Memory {
  private HashMap<String, Integer> mem = new HashMap<String, Integer>();

  void clearValue(String key) {
    mem.put(key, 0);
  }

  void incValue(String key) throws Exception {
    Integer temp = mem.get(key);
    if (temp != null) {
      mem.put(key, temp + 1);
    } else {
      throw new Exception("Cannot find variable \"" + key + "\"");
    }
  }

  Integer get(String key) {
    return mem.get(key);
  }

  boolean hasKey(String key) {
    return mem.containsKey(key);
  }

  void decValue(String key) throws Exception {
    if (mem.containsKey(key)) {
      int val = mem.get(key);
      if (val == 0) {
        throw new Exception("Value cannot be negative");
      }
      mem.put(key, val - 1);
    } else {
      throw new Exception("Cannot find variable \"" + key + "\"");
    }
  }

  void setValue(String key, int value) throws Exception {
    if (mem.containsKey(key)) {
      if (value < 0) {
        throw new Exception("Value cannot be negative");
      }
      mem.put(key, value);
    } else {
      throw new Exception("Cannot find variable \"" + key + "\"");
    }
  }

  void printMemoryState() {
    if (mem.keySet().size() == 0) {
      System.out.println("Memory empty");
      return;
    }

    Optional<Integer> kw = mem.keySet().stream().map(k -> k.length()).max(Comparator.comparingInt(o -> o));
    Optional<Integer> vw = mem.values().stream().map(v -> ("" + v).length()).max(Comparator.comparing(o -> o));
    Integer keyWidth = Math.max(6, kw.orElse(0) + 2);
    Integer valueWidth = Math.max(8, vw.orElse(0) + 3);

    System.out.println("Name" + " ".repeat(keyWidth - 4) + "┃ Value");
    System.out.println("━".repeat(keyWidth) + "╋" + "━".repeat(valueWidth));
    for (String k : mem.keySet()) {
      System.out.println(k + " ".repeat(keyWidth - k.length()) + "┃ " + mem.get(k));
    }
    System.out.println();
  }
}

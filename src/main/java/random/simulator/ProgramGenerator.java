package random.simulator;

import java.util.Random;

public class ProgramGenerator {

  public void generateRandomProgram(int[] program, Random random, int maxByteValue) {
    for (int i = 0; i < program.length; i++) {
      program[i] = random.nextInt(maxByteValue);
    }
  }
}

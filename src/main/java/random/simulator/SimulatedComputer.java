package random.simulator;

import java.util.Random;

public class SimulatedComputer {

  private static final int NUM_REGISTERS = 4;
  private static final int PROGRAM_SIZE = 256;
  private static final int MAX_BYTE_VALUE = 256;

  private int[] registers = new int[NUM_REGISTERS];
  private int[] program = new int[PROGRAM_SIZE];
  private Random random = new Random();

  public static void main(String[] args) {
    SimulatedComputer simulatedComputer = new SimulatedComputer();
    simulatedComputer.runSimulation(5);
  }

  private void runSimulation(int numSimulations) {
    for (int i = 0; i < numSimulations; i++) {
      ProgramGenerator programGenerator = new ProgramGenerator();
      programGenerator.generateRandomProgram(program, random, MAX_BYTE_VALUE);

      InstructionExecutor instructionExecutor = new InstructionExecutor(program, registers);
      instructionExecutor.runProgram();

      System.out.println("--------------------------");
      clearRegisters();
    }
  }

  private void clearRegisters() {
    for (int i = 0; i < NUM_REGISTERS; i++) {
      registers[i] = 0;
    }
  }
}

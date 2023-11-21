import java.util.Random;

public class SimulatedComputer {

  // Number of registers
  private static final int NUM_REGISTERS = 4;

  // Size of the program
  private static final int PROGRAM_SIZE = 256;

  // Maximum byte value
  private static final int MAX_BYTE_VALUE = 256;

  // Registers
  private int[] registers = new int[NUM_REGISTERS];

  // Program memory
  private int[] program = new int[PROGRAM_SIZE];

  // Random number generator
  private Random random = new Random();

  public static void main(String[] args) {
    SimulatedComputer simulatedComputer = new SimulatedComputer();
    simulatedComputer.runSimulation(5);
  }

  // Generate a random program
  private void generateRandomProgram() {
    for (int i = 0; i < PROGRAM_SIZE; i++) {
      program[i] = random.nextInt(MAX_BYTE_VALUE);
    }
  }

  // Execute a single instruction
  private void executeInstruction(int instructionAddress) {
    int opcode = program[instructionAddress] & 0b111; // Extract lower 3 bits

    int destRegOrAddr = program[(instructionAddress + 1) % PROGRAM_SIZE];
    int srcReg1OrConst = program[(instructionAddress + 2) % PROGRAM_SIZE];
    int srcReg2 = program[(instructionAddress + 3) % PROGRAM_SIZE];

    int destReg = destRegOrAddr & 0b11; // Extract lower 2 bits
    int instructionAddr = destRegOrAddr >> 2;

    int value1 = registers[srcReg1OrConst & 0b11]; // Extract lower 2 bits
    int value2 = registers[srcReg2 & 0b11]; // Extract lower 2 bits
    int constant = srcReg1OrConst >> 2;

    switch (opcode) {
      case 0: // NOP
        break;
      case 1: // ADD
        registers[destReg] = (value1 + value2) % MAX_BYTE_VALUE;
        break;
      case 2: // LC
        registers[destReg] = constant;
        break;
      case 3: // BNE
        if (value1 != value2) {
          instructionAddress = instructionAddr;
        }
        break;
      case 4: // EMC
        System.out.print((byte) constant);
        break;
      case 5: // EMR
        System.out.print((byte) registers[destReg]);
        break;
      case 6: // ADDC
        registers[destReg] = (value1 + constant) % MAX_BYTE_VALUE;
        break;
      case 7: // XOR
        registers[destReg] = value1 ^ value2;
        break;
    }
  }

  // Run the simulated program
  // Run the simulated program
  private void runProgram() {
    int instructionAddress = 0;
    int instructionsWithoutOutput = 0;

    for (int i = 0; i < PROGRAM_SIZE; i++) {
      executeInstruction(instructionAddress);

      // Check if the instruction produces output
      if (program[instructionAddress] == 4 || program[instructionAddress] == 5) {
        instructionsWithoutOutput = 0; // Reset the counter
      } else {
        instructionsWithoutOutput++;
      }

      // Check if the program should stop
      if (instructionsWithoutOutput >= 9999) {
        System.out.println("Program halted: No output produced after 9999 instructions.");
        break;
      }

      instructionAddress = (instructionAddress + 4) % PROGRAM_SIZE;
    }
  }

  // Clear registers
  private void clearRegisters() {
    for (int i = 0; i < NUM_REGISTERS; i++) {
      registers[i] = 0;
    }
  }

  // Run the simulation for a given number of times
  private void runSimulation(int numSimulations) {
    for (int i = 0; i < numSimulations; i++) {
      generateRandomProgram();
      runProgram();
      System.out.println(); // New line between simulations
      clearRegisters();
    }
  }
}

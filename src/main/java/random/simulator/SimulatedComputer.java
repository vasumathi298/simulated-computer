package random.simulator;

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

    // Extract lower 3 bits hence BITWISE AND of 7 (0000111) to get lower three bits
    int opcode = program[instructionAddress] & 0b111;

    // Destination address
    int destRegOrAddr = program[(instructionAddress + 1) % PROGRAM_SIZE];
    //Source Register 1
    int srcReg1OrConst = program[(instructionAddress + 2) % PROGRAM_SIZE];
    // Source Register 2
    int srcReg2 = program[(instructionAddress + 3) % PROGRAM_SIZE];

    // Extract lower 2 bits
    int destReg = destRegOrAddr & 0b11;
    int instructionAddr = destRegOrAddr >> 2;

    // Extract lower 2 bits bitwise AND operation with the binary value 0b11 (decimal value 3),
    // extracting the lower 2 bits of srcReg1OrConst bitwise AND operation with the binary value 0b11 (decimal value 3),
    int value1 = registers[srcReg1OrConst & 0b11];
    // Extract lower 2 bits
    // extracts the lower 2 bits of srcReg2 using a bitwise AND operation.
    int value2 = registers[srcReg2 & 0b11];
    // This line shifts the bits of the value in srcReg1OrConst two positions to the right.
    // This operation effectively divides the value by 2^2 (which is 4) and discards the remainder.
    // It's a way of extracting the higher bits of srcReg1OrConst after the lower 2 bits have been extracted in a previous operation.
    int constant = srcReg1OrConst >> 2;

    switch (opcode) {
      case 0: // NOP (No Operation)
        System.out.println("NOP");
        break;

      case 1: // ADD (Addition)
        // Perform addition of value1 and value2, store result in destReg
        registers[destReg] = (value1 + value2) % MAX_BYTE_VALUE;
        System.out.println("ADD " + value1 + " " + value2 + " " + destReg);
        break;

      case 2: // LC (Load Constant)
        // Load constant into destReg
        registers[destReg] = constant;
        System.out.println("LC " + constant + " " + destReg);
        break;

      case 3: // BNE (Branch if Not Equal)
        // If value1 is not equal to value2, set instructionAddress to instructionAddr
        if (value1 != value2) {
          instructionAddress = instructionAddr;
        }
        System.out.println("BNE " + value1 + " " + value2);
        break;

      case 4: // EMC (Emit Constant)
        // This case does not perform any operation, just prints a message
        System.out.println("EMC " + " " + constant);
        break;

      case 5: // EMR (Emit Register)
        // Print the value stored in the register at destReg as a byte
        System.out.println("EMR " + " " + (byte) registers[destReg]);
        break;

      case 6: // ADDC (Add Constant)
        // Perform addition of value1 and constant, store result in destReg
        registers[destReg] = (value1 + constant) % MAX_BYTE_VALUE;
        System.out.println("ADDC " + constant + " " + value1 + " " + destReg);
        break;

      case 7: // XOR (Exclusive OR)
        // Perform bitwise XOR of value1 and value2, store result in destReg
        registers[destReg] = value1 ^ value2;
        System.out.println("XOR " + " " + value1 + " " + value2);
        break;
    }

  }

  // Run the simulated program
  private void runProgram() {
    int instructionAddress = 0;
    int instructionsWithoutOutput = 0;

    try {
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
    } catch (ArrayIndexOutOfBoundsException e) {
      // Handle the case where the program attempts to access an index beyond the array bounds
      System.err.println("Array index out of bounds: " + e.getMessage());
    } catch (Exception e) {
      // Handle other exceptions that might occur during program execution
      System.err.println("An unexpected error occurred: " + e.getMessage());
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
      System.out.println("--------------------------"); // New line between simulations
      clearRegisters();
    }
  }
}

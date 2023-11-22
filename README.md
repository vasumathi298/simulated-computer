# Simulated Computer

This Java program simulates the execution of a simple computer with a specified number of registers and program instructions.

## Overview

The simulated computer has the following components:

- **Registers:** There are 4 registers in the computer.

- **Program Size:** The program consists of 256 instructions.

- **Maximum Byte Value:** The maximum byte value used in the program is 256.

- [Register Management](#register-management)
    - [Opcode Extraction](#opcode-extraction)
    - [Destination Register and Address Extraction](#destination-register-and-address-extraction)
    - [Source Registers and Constants Extraction](#source-registers-and-constants-extraction)
    - [Constant Extraction](#constant-extraction)


## Instructions

The program supports the following instructions:

1. **NOP (No Operation):** Does nothing.

2. **ADD (Addition):** Adds two values and stores the result in a register.

3. **LC (Load Constant):** Loads a constant value into a register.

4. **BNE (Branch if Not Equal):** Branches to a specified address if two values are not equal.

5. **EMC (Emit Constant):** Prints a constant value.

6. **EMR (Emit Register):** Prints the value stored in a register as a byte.

7. **ADDC (Add Constant):** Adds a constant to a value in a register and stores the result in the register.

8. **XOR (Exclusive OR):** Performs bitwise XOR on two values and stores the result in a register.

## Register Management

In the simulated computer, the program manipulates registers during the execution of instructions. Here's how the registers are obtained and managed:

### Opcode Extraction

```java
// Extract lower 3 bits hence BITWISE AND of 7 (0000111) to get lower three bits
int opcode = program[instructionAddress] & 0b111;
```
The first step involves extracting the opcode from the current instruction. This is achieved by performing a bitwise AND operation with the binary value 0b111 (decimal value 7). The result is the lower three bits of the instruction, which represent the opcode.

### Destination Register and Address Extraction


```java
// Destination address
int destRegOrAddr = program[(instructionAddress + 1) % PROGRAM_SIZE];
// Extract lower 2 bits
int destReg = destRegOrAddr & 0b11;
int instructionAddr = destRegOrAddr >> 2;
```
The destination register (destReg) and instruction address are obtained from the next two parts of the instruction. The destination register is extracted by performing a bitwise AND operation with 0b11, and the instruction address is obtained by right-shifting the value by 2 bits.

### Source Registers and Constants Extraction

```java
// Source Register 1
int srcReg1OrConst = program[(instructionAddress + 2) % PROGRAM_SIZE];
// Source Register 2
int srcReg2 = program[(instructionAddress + 3) % PROGRAM_SIZE];

// Extract lower 2 bits
int value1 = registers[srcReg1OrConst & 0b11];
int value2 = registers[srcReg2 & 0b11];
```

The source registers or constants are obtained similarly by extracting the lower 2 bits of the values. These values are then used in subsequent operations based on the instruction.

### Constant Extraction

```java
int constant = srcReg1OrConst >> 2;
```

This operation effectively divides the value by 2^2 (which is 4) and discards the remainder. 
It's a way of extracting the higher bits of srcReg1OrConst after the lower 2 bits have been extracted in a previous operation.

## Running the Simulation

To run the simulation, execute the `main` method in the `SimulatedComputer` class. The program will generate random instructions, execute them, and print the output.

```java
public static void main(String[] args) {
    SimulatedComputer simulatedComputer = new SimulatedComputer();
    simulatedComputer.runSimulation(5);
}
```

## Running the jar

- mvn clean install - Compiles the source code, runs tests, and packages the application into a JAR (or other specified format).
- java -jar /out/artifacts/simulator_jar/simulator.jar - Instructing the JVM to execute the Java application contained in the specified JAR file


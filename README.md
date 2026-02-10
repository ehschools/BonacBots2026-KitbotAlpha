# BonacBots2026-KitbotAlpha

FRC 2026 Robot Code for Team 35 - BonacBots

## Project Overview

This repository contains the robot code for our 2026 FRC season robot, based on the WPILib KitBot chassis and equipped with a Limelight 4 camera for vision processing.

## Robot Configuration

### Hardware
- **Chassis**: WPILib KitBot (4-wheel tank drive)
- **Motors**: 4x NEO Brushless motors (2 per side)
- **Motor Controllers**: 4x REV SparkMax
- **Vision**: Limelight 4 camera with AprilTag detection
- **Control System**: RoboRIO 2.0
- **Driver Interface**: Xbox controller

### Software Stack
- **Framework**: WPILib Command-Based (Java)
- **Vision Processing**: PhotonVision (Limelight 4)
- **Build System**: Gradle 8.5
- **Language**: Java 17

## Project Structure

```
src/main/java/frc/robot/
├── Main.java              # Program entry point
├── Robot.java             # Main robot class
├── Constants.java         # Robot configuration constants
├── RobotContainer.java    # Command bindings and subsystems
├── commands/              # Command classes
│   └── DriveCommand.java
└── subsystems/            # Subsystem classes
    ├── DriveSubsystem.java
    └── VisionSubsystem.java
```

## Building and Deploying

### Prerequisites
- WPILib 2026 installed
- Java Development Kit (JDK) 17
- Robot connected via USB or WiFi
- Internet access to download dependencies:
  - `frcmaven.wpi.edu` - WPILib artifacts
  - `maven.revrobotics.com` - REVLib (SparkMax) artifacts
  - `maven.pkg.github.com` - Additional FRC vendor libraries

### Build Commands
```bash
# Build the project
./gradlew build

# Deploy to robot
./gradlew deploy

# Run tests
./gradlew test

# Simulate robot
./gradlew simulateJava
```

**Note:** First build will download all required dependencies (~200MB). Subsequent builds will be faster.

## Features

### Drive System
- Arcade drive control (default)
- Tank drive support
- Configurable speed and turn rate limits
- Current limiting for motor protection

### Vision System
- Limelight 4 integration via NetworkTables
- AprilTag detection and tracking
- Real-time target information (yaw, pitch, area, AprilTag ID)
- Distance calculation based on target height
- LED control (toggle with A button)
- Driver/Vision camera mode switching
- Pipeline latency monitoring

### Controls
- **Left Stick Y**: Forward/Backward
- **Right Stick X**: Rotation
- **A Button**: Toggle Limelight LEDs
- **B Button**: Switch to driver camera mode
- **X Button**: Switch to vision processing mode

## Configuration

All robot configuration is centralized in `Constants.java`:

- **CAN IDs**: Motor controller IDs (default: 1-4)
- **Current Limits**: Motor current limits (default: 40A)
- **Speed Limits**: Maximum drive speeds
- **Vision Settings**: Limelight configuration and thresholds

## Vendor Dependencies

This project includes:
- REVLib (2026.1.0) - for SparkMax motor controllers

**Note:** The Limelight 4 camera uses NetworkTables for communication, which is built into WPILib. No additional vendor dependency is required for vision processing.

## Development

### Adding Commands
Create new command classes in `src/main/java/frc/robot/commands/` and bind them in `RobotContainer.java`.

### Adding Subsystems
Create new subsystem classes in `src/main/java/frc/robot/subsystems/` and instantiate them in `RobotContainer.java`.

## Team Information

- **Team Number**: 35
- **Team Name**: BonacBots
- **Season**: 2026

## License

See LICENSE file for details.
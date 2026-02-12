// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.CANDriveSubsystem;

public final class Autos {
  // Example autonomous command which drives forward for 1 second.
  public static final Command exampleAuto(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem) {
    return new SequentialCommandGroup(
        // Drive backwards for .25 seconds. The driveArcadeAuto command factory
        // creates a command which does not end which allows us to control
        // the timing using the withTimeout decorator
        driveSubsystem.driveArcade(() -> 0.5, () -> 0).withTimeout(.25),
        // Stop driving. This line uses the regular driveArcade command factory so it
        // ends immediately after commanding the motors to stop
        driveSubsystem.driveArcade(() -> 0, () -> 0),
        // Spin up the launcher for 1 second and then launch balls for 9 seconds, for a
        // total of 10 seconds
        ballSubsystem.spinUpCommand().withTimeout(1),
        ballSubsystem.launchCommand().withTimeout(9),
        // Stop running the launcher
        ballSubsystem.runOnce(() -> ballSubsystem.stop()));
  }

  public static final Command blueOne(CANDriveSubsystem drive, CANFuelSubsystem fuel){
    double TX = LimelightHelpers.getTX("limelight-alpha");
    double TY = LimelightHelpers.getTY("limelight-alpha");
    boolean TV = LimelightHelpers.getTV("limelight");
    SequentialCommandGroup group = new SequentialCommandGroup();

      while (TV){ // aprilTag is in camera view frame
        if ((TY > 15 && TY < 24) && (TX > -7 && TX < 7)){
          // stop and shoot
          group.addCommands(
            drive.driveArcade(() -> 0, () -> 0), // stop
            fuel.spinUpCommand().withTimeout(1), // clear feeder
            fuel.launchCommand().withTimeout(10)); // shoot
        } else if ((TY > 15 && TY < 24) && !(TX > -7 && TX < 7)){ // if this is true, the aprilTag is too much to one side in the camera view frame
          // if aprilTag is too much to either side -> turn to the opposite side
          if (TX < 7){
            group.addCommands(drive.driveArcade(() -> 0, () -> .5)); // turn right
          } else {
            group.addCommands(drive.driveArcade(() -> 0, () -> -.5)); // turn left
          }
        } else if (!(TY > 15 && TY < 24) &&(TX > -7 && TX < 7)){ // if this is true, the aprilTag is too high or too low in the camera view frame
          // if aprilTag is too high or low -> move back or forward respectively
          if (TY < 24){
            group.addCommands(drive.driveArcade(() -> .5, () -> 0)); // move forward
          } else {
            group.addCommands(drive.driveArcade(() -> -.5, () -> 0)); // move backward
          }
        }
      }
      
      while (!TV) { // no aprilTag in camera view frame
        group.addCommands(drive.driveArcade(() -> 0, () -> 0.5)); // turn until aprilTag is in camera view frame
      }
      return group;
  }

  public static final Command blueTwo(CANDriveSubsystem drive, CANFuelSubsystem fuel){
    return new SequentialCommandGroup(
      drive.driveArcade(() -> -0.5, () -> 0).withTimeout(2), 
      drive.driveArcade(() -> 0.5, () -> 0).withTimeout(2));
  }

  public static final Command blueThree(CANDriveSubsystem drive, CANFuelSubsystem fuel){
    return new SequentialCommandGroup(
      drive.driveArcade(() -> -0.5, () -> 0).withTimeout(2), 
      drive.driveArcade(() -> 0.5, () -> 0).withTimeout(2));
  }
  
  public static final Command redOne(CANDriveSubsystem drive, CANFuelSubsystem fuel){
    return new SequentialCommandGroup(
      drive.driveArcade(() -> -0.5, () -> 0).withTimeout(2), 
      drive.driveArcade(() -> 0.5, () -> 0).withTimeout(2));
  }

  public static final Command redTwo(CANDriveSubsystem drive, CANFuelSubsystem fuel){
    return new SequentialCommandGroup(
      drive.driveArcade(() -> -0.5, () -> 0).withTimeout(2), 
      drive.driveArcade(() -> 0.5, () -> 0).withTimeout(2));
  }

  public static final Command redThree(CANDriveSubsystem drive, CANFuelSubsystem fuel){
    return new SequentialCommandGroup(
      drive.driveArcade(() -> -0.5, () -> 0).withTimeout(2), 
      drive.driveArcade(() -> 0.5, () -> 0).withTimeout(2));
  }
}

package frc.robot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.Constants.OperatorConstants;

/**
 * Unit tests for the Constants class to verify configuration values.
 */
public class ConstantsTest {

  @Test
  public void testDriveConstants() {
    // Verify drive constants are within expected ranges
    assertTrue(DriveConstants.kLeftMotor1CanId > 0, "Left motor 1 CAN ID should be positive");
    assertTrue(DriveConstants.kLeftMotor2CanId > 0, "Left motor 2 CAN ID should be positive");
    assertTrue(DriveConstants.kRightMotor1CanId > 0, "Right motor 1 CAN ID should be positive");
    assertTrue(DriveConstants.kRightMotor2CanId > 0, "Right motor 2 CAN ID should be positive");
    
    assertTrue(DriveConstants.kMaxSpeed > 0 && DriveConstants.kMaxSpeed <= 1.0, 
        "Max speed should be between 0 and 1");
    assertTrue(DriveConstants.kMaxTurnRate > 0 && DriveConstants.kMaxTurnRate <= 1.0, 
        "Max turn rate should be between 0 and 1");
    assertTrue(DriveConstants.kCurrentLimit > 0, "Current limit should be positive");
  }

  @Test
  public void testVisionConstants() {
    // Verify vision constants are valid
    assertNotNull(VisionConstants.kLimelightName, "Limelight name should not be null");
    assertFalse(VisionConstants.kLimelightName.isEmpty(), "Limelight name should not be empty");
    
    assertTrue(VisionConstants.kLedModeDefault >= 0 && VisionConstants.kLedModeDefault <= 3,
        "LED mode should be 0-3");
    assertTrue(VisionConstants.kCamModeVision == 0, "Vision mode should be 0");
    assertTrue(VisionConstants.kCamModeDriver == 1, "Driver mode should be 1");
  }

  @Test
  public void testOperatorConstants() {
    // Verify operator constants are valid
    assertTrue(OperatorConstants.kDriverControllerPort >= 0, 
        "Driver controller port should be non-negative");
    assertTrue(OperatorConstants.kJoystickDeadband >= 0 && 
        OperatorConstants.kJoystickDeadband < 0.5,
        "Joystick deadband should be between 0 and 0.5");
  }

  @Test
  public void testUniqueCanIds() {
    // Verify all CAN IDs are unique
    int[] canIds = {
        DriveConstants.kLeftMotor1CanId,
        DriveConstants.kLeftMotor2CanId,
        DriveConstants.kRightMotor1CanId,
        DriveConstants.kRightMotor2CanId
    };
    
    for (int i = 0; i < canIds.length; i++) {
      for (int j = i + 1; j < canIds.length; j++) {
        assertNotEquals(canIds[i], canIds[j], 
            String.format("CAN IDs must be unique: found duplicate ID %d", canIds[i]));
      }
    }
  }
}

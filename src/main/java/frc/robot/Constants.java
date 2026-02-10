package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    
    // Joystick deadband
    public static final double kJoystickDeadband = 0.05;
  }

  public static class DriveConstants {
    // CAN IDs for motor controllers
    public static final int kLeftMotor1CanId = 1;
    public static final int kLeftMotor2CanId = 2;
    public static final int kRightMotor1CanId = 3;
    public static final int kRightMotor2CanId = 4;

    // Drive speed limits
    public static final double kMaxSpeed = 1.0;
    public static final double kMaxTurnRate = 1.0;

    // Current limits for SparkMax (Amps)
    public static final int kCurrentLimit = 40;
  }

  public static class VisionConstants {
    // Limelight 4 configuration
    public static final String kLimelightName = "limelight";
    
    // Limelight LED modes
    public static final int kLedModeDefault = 0;
    public static final int kLedModeOff = 1;
    public static final int kLedModeBlink = 2;
    public static final int kLedModeOn = 3;

    // Camera modes
    public static final int kCamModeVision = 0;
    public static final int kCamModeDriver = 1;

    // AprilTag detection thresholds
    public static final double kValidTargetThreshold = 0.5;
  }
}

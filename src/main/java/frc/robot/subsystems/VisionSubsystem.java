package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;

/**
 * The VisionSubsystem handles Limelight 4 camera integration for AprilTag detection
 * and target tracking. The Limelight 4 provides advanced vision processing with
 * high-speed AprilTag detection and pose estimation.
 * 
 * This implementation uses NetworkTables to communicate with the Limelight.
 */
public class VisionSubsystem extends SubsystemBase {
  private final NetworkTable m_limelightTable;
  
  private boolean m_ledOn = true;

  // NetworkTable entries for Limelight data
  private final NetworkTableEntry m_tv;  // Valid target (0 or 1)
  private final NetworkTableEntry m_tx;  // Horizontal offset (-29.8 to 29.8 degrees)
  private final NetworkTableEntry m_ty;  // Vertical offset (-24.85 to 24.85 degrees)
  private final NetworkTableEntry m_ta;  // Target area (0% to 100%)
  private final NetworkTableEntry m_tid; // AprilTag ID
  private final NetworkTableEntry m_tl;  // Latency (ms)

  /** Creates a new VisionSubsystem. */
  public VisionSubsystem() {
    // Get NetworkTables for Limelight
    m_limelightTable = NetworkTableInstance.getDefault().getTable(VisionConstants.kLimelightName);
    
    // Get NetworkTable entries
    m_tv = m_limelightTable.getEntry("tv");
    m_tx = m_limelightTable.getEntry("tx");
    m_ty = m_limelightTable.getEntry("ty");
    m_ta = m_limelightTable.getEntry("ta");
    m_tid = m_limelightTable.getEntry("tid");
    m_tl = m_limelightTable.getEntry("tl");
    
    // Set default LED state
    setLEDMode(VisionConstants.kLedModeOn);
  }

  /**
   * Sets the Limelight LED mode.
   * 
   * @param mode LED mode (0=pipeline default, 1=off, 2=blink, 3=on)
   */
  public void setLEDMode(int mode) {
    m_limelightTable.getEntry("ledMode").setNumber(mode);
  }

  /**
   * Toggles the Limelight LEDs on/off.
   */
  public void toggleLEDs() {
    m_ledOn = !m_ledOn;
    setLEDMode(m_ledOn ? VisionConstants.kLedModeOn : VisionConstants.kLedModeOff);
  }

  /**
   * Sets the Limelight camera mode.
   * 
   * @param driverMode true for driver camera (increased exposure), false for vision processing
   */
  public void setDriverMode(boolean driverMode) {
    m_limelightTable.getEntry("camMode").setNumber(driverMode ? VisionConstants.kCamModeDriver : VisionConstants.kCamModeVision);
  }

  /**
   * Sets the Limelight pipeline.
   * 
   * @param pipeline Pipeline index (0-9)
   */
  public void setPipeline(int pipeline) {
    m_limelightTable.getEntry("pipeline").setNumber(pipeline);
  }

  /**
   * Checks if the Limelight has a valid target.
   * 
   * @return true if a valid target is detected
   */
  public boolean hasTarget() {
    return m_tv.getDouble(0) == 1.0;
  }

  /**
   * Gets the horizontal angle to the target (yaw).
   * 
   * @return Horizontal angle in degrees, or 0 if no target
   */
  public double getTargetYaw() {
    return m_tx.getDouble(0.0);
  }

  /**
   * Gets the vertical angle to the target (pitch).
   * 
   * @return Vertical angle in degrees, or 0 if no target
   */
  public double getTargetPitch() {
    return m_ty.getDouble(0.0);
  }

  /**
   * Gets the target area as a percentage of the image.
   * 
   * @return Target area (0-100), or 0 if no target
   */
  public double getTargetArea() {
    return m_ta.getDouble(0.0);
  }

  /**
   * Gets the AprilTag ID of the best target.
   * 
   * @return AprilTag ID, or -1 if no target
   */
  public int getAprilTagID() {
    return (int) m_tid.getDouble(-1.0);
  }

  /**
   * Gets the pipeline latency in milliseconds.
   * 
   * @return Pipeline latency in ms
   */
  public double getLatencyMs() {
    return m_tl.getDouble(0.0);
  }

  /**
   * Gets the distance to the target based on known target height.
   * This is a simplified calculation using pitch angle.
   * 
   * @param targetHeightMeters Height of the target in meters
   * @param cameraHeightMeters Height of the camera in meters
   * @param cameraPitchDegrees Pitch angle of the camera mounting in degrees
   * @return Distance to target in meters, or 0 if no target
   */
  public double getDistanceToTarget(double targetHeightMeters, double cameraHeightMeters, double cameraPitchDegrees) {
    if (!hasTarget()) {
      return 0.0;
    }
    
    double targetPitch = getTargetPitch();
    double angleToTarget = cameraPitchDegrees + targetPitch;
    
    // Distance = (height difference) / tan(angle)
    double heightDifference = targetHeightMeters - cameraHeightMeters;
    return heightDifference / Math.tan(Math.toRadians(angleToTarget));
  }

  @Override
  public void periodic() {
    // Publish telemetry to SmartDashboard
    SmartDashboard.putBoolean("Vision/HasTarget", hasTarget());
    SmartDashboard.putNumber("Vision/TargetYaw", getTargetYaw());
    SmartDashboard.putNumber("Vision/TargetPitch", getTargetPitch());
    SmartDashboard.putNumber("Vision/TargetArea", getTargetArea());
    SmartDashboard.putNumber("Vision/AprilTagID", getAprilTagID());
    SmartDashboard.putNumber("Vision/LatencyMs", getLatencyMs());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

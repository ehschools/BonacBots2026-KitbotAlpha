package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;

import java.util.Optional;

/**
 * The VisionSubsystem handles Limelight 4 camera integration for AprilTag detection
 * and target tracking. The Limelight 4 provides advanced vision processing with
 * high-speed AprilTag detection and pose estimation.
 */
public class VisionSubsystem extends SubsystemBase {
  private final PhotonCamera m_camera;
  private final NetworkTable m_limelightTable;
  
  private boolean m_ledOn = true;
  private PhotonPipelineResult m_latestResult;

  /** Creates a new VisionSubsystem. */
  public VisionSubsystem() {
    // Initialize PhotonVision camera (Limelight 4 runs PhotonVision)
    m_camera = new PhotonCamera(VisionConstants.kLimelightName);
    
    // Get NetworkTables for additional Limelight control
    m_limelightTable = NetworkTableInstance.getDefault().getTable(VisionConstants.kLimelightName);
    
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
   * Gets the latest pipeline result from PhotonVision.
   * 
   * @return The latest pipeline result
   */
  public PhotonPipelineResult getLatestResult() {
    return m_latestResult;
  }

  /**
   * Checks if the Limelight has a valid target.
   * 
   * @return true if a valid target is detected
   */
  public boolean hasTarget() {
    return m_latestResult != null && m_latestResult.hasTargets();
  }

  /**
   * Gets the best target from the latest result.
   * 
   * @return Optional containing the best target, or empty if no targets
   */
  public Optional<PhotonTrackedTarget> getBestTarget() {
    if (hasTarget()) {
      return Optional.of(m_latestResult.getBestTarget());
    }
    return Optional.empty();
  }

  /**
   * Gets the horizontal angle to the target (yaw).
   * 
   * @return Horizontal angle in degrees, or 0 if no target
   */
  public double getTargetYaw() {
    return getBestTarget().map(PhotonTrackedTarget::getYaw).orElse(0.0);
  }

  /**
   * Gets the vertical angle to the target (pitch).
   * 
   * @return Vertical angle in degrees, or 0 if no target
   */
  public double getTargetPitch() {
    return getBestTarget().map(PhotonTrackedTarget::getPitch).orElse(0.0);
  }

  /**
   * Gets the target area as a percentage of the image.
   * 
   * @return Target area (0-100), or 0 if no target
   */
  public double getTargetArea() {
    return getBestTarget().map(PhotonTrackedTarget::getArea).orElse(0.0);
  }

  /**
   * Gets the AprilTag ID of the best target.
   * 
   * @return AprilTag ID, or -1 if no target
   */
  public int getAprilTagID() {
    return getBestTarget().map(PhotonTrackedTarget::getFiducialId).orElse(-1);
  }

  /**
   * Gets the robot pose from AprilTag detection.
   * 
   * @return Optional containing the estimated robot pose, or empty if unavailable
   */
  public Optional<Pose2d> getRobotPose() {
    if (m_latestResult != null && m_latestResult.hasTargets()) {
      // Get the best target's transform
      PhotonTrackedTarget target = m_latestResult.getBestTarget();
      Transform3d cameraToTarget = target.getBestCameraToTarget();
      
      // Note: Full pose estimation requires field layout and camera calibration
      // This is a simplified example
      return Optional.of(new Pose2d(
          cameraToTarget.getX(),
          cameraToTarget.getY(),
          new Rotation2d(Units.degreesToRadians(target.getYaw()))
      ));
    }
    return Optional.empty();
  }

  @Override
  public void periodic() {
    // Update latest result from camera
    m_latestResult = m_camera.getLatestResult();
    
    // Publish telemetry to SmartDashboard
    SmartDashboard.putBoolean("Vision/HasTarget", hasTarget());
    SmartDashboard.putNumber("Vision/TargetYaw", getTargetYaw());
    SmartDashboard.putNumber("Vision/TargetPitch", getTargetPitch());
    SmartDashboard.putNumber("Vision/TargetArea", getTargetArea());
    SmartDashboard.putNumber("Vision/AprilTagID", getAprilTagID());
    
    // Publish latency
    if (m_latestResult != null) {
      SmartDashboard.putNumber("Vision/LatencyMs", m_latestResult.getLatencyMillis());
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

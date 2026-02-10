package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

/**
 * The DriveSubsystem controls the robot's drivetrain.
 * This is configured for a KitBot with 4 NEO motors (2 per side) using SparkMax controllers.
 */
public class DriveSubsystem extends SubsystemBase {
  // Motor controllers
  private final SparkMax m_leftMotor1;
  private final SparkMax m_leftMotor2;
  private final SparkMax m_rightMotor1;
  private final SparkMax m_rightMotor2;

  // Differential drive
  private final DifferentialDrive m_drive;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // Initialize motor controllers
    m_leftMotor1 = new SparkMax(DriveConstants.kLeftMotor1CanId, MotorType.kBrushless);
    m_leftMotor2 = new SparkMax(DriveConstants.kLeftMotor2CanId, MotorType.kBrushless);
    m_rightMotor1 = new SparkMax(DriveConstants.kRightMotor1CanId, MotorType.kBrushless);
    m_rightMotor2 = new SparkMax(DriveConstants.kRightMotor2CanId, MotorType.kBrushless);

    // Configure motor controllers
    configureMotors();

    // Create differential drive with leader motors
    m_drive = new DifferentialDrive(m_leftMotor1, m_rightMotor1);
    m_drive.setSafetyEnabled(true);
    m_drive.setExpiration(0.1);
    m_drive.setMaxOutput(DriveConstants.kMaxSpeed);
  }

  /**
   * Configure all motor controllers with appropriate settings.
   */
  private void configureMotors() {
    // Create configuration for motors
    SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig.idleMode(IdleMode.kBrake);
    leftConfig.smartCurrentLimit(DriveConstants.kCurrentLimit);
    leftConfig.inverted(false);

    SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.idleMode(IdleMode.kBrake);
    rightConfig.smartCurrentLimit(DriveConstants.kCurrentLimit);
    rightConfig.inverted(true);  // Right side is inverted

    // Apply configuration to left motors
    m_leftMotor1.configure(leftConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
    m_leftMotor2.configure(leftConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);

    // Apply configuration to right motors
    m_rightMotor1.configure(rightConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
    m_rightMotor2.configure(rightConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);

    // Set follower motors
    m_leftMotor2.follow(m_leftMotor1);
    m_rightMotor2.follow(m_rightMotor1);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param forward the commanded forward movement
   * @param rotation the commanded rotation
   */
  public void arcadeDrive(double forward, double rotation) {
    m_drive.arcadeDrive(forward, rotation);
  }

  /**
   * Drives the robot using tank drive controls.
   *
   * @param leftSpeed the commanded left side speed
   * @param rightSpeed the commanded right side speed
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * Stops the drivetrain.
   */
  public void stop() {
    m_drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // You can add telemetry or other periodic updates here
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

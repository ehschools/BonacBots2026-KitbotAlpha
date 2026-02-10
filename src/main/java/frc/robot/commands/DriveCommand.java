package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command that drives the robot with arcade drive controls.
 * This command reads joystick inputs and applies them to the drivetrain.
 */
public class DriveCommand extends Command {
  private final DriveSubsystem m_driveSubsystem;
  private final DoubleSupplier m_forwardSupplier;
  private final DoubleSupplier m_rotationSupplier;

  /**
   * Creates a new DriveCommand.
   *
   * @param driveSubsystem The drive subsystem to use
   * @param forwardSupplier Supplier for forward/backward speed
   * @param rotationSupplier Supplier for rotation speed
   */
  public DriveCommand(
      DriveSubsystem driveSubsystem,
      DoubleSupplier forwardSupplier,
      DoubleSupplier rotationSupplier) {
    m_driveSubsystem = driveSubsystem;
    m_forwardSupplier = forwardSupplier;
    m_rotationSupplier = rotationSupplier;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Get joystick inputs
    double forward = m_forwardSupplier.getAsDouble();
    double rotation = m_rotationSupplier.getAsDouble();

    // Apply deadband to prevent drift
    forward = MathUtil.applyDeadband(forward, OperatorConstants.kJoystickDeadband);
    rotation = MathUtil.applyDeadband(rotation, OperatorConstants.kJoystickDeadband);

    // Drive the robot
    m_driveSubsystem.arcadeDrive(forward, rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // This command should run continuously
  }
}

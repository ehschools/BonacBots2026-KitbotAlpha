package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();

  // The driver's controller
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // Configure default commands
    // Set the default drive command to arcade drive
    m_driveSubsystem.setDefaultCommand(
        new DriveCommand(
            m_driveSubsystem,
            () -> -m_driverController.getLeftY(),  // Forward/backward (inverted)
            () -> -m_driverController.getRightX()  // Rotation (inverted)
        ));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID#button(int)} method.
   */
  private void configureBindings() {
    // Example: Toggle Limelight LEDs with A button
    m_driverController.a().onTrue(Commands.runOnce(() -> m_visionSubsystem.toggleLEDs()));
    
    // Example: Switch to driver camera mode with B button
    m_driverController.b().onTrue(Commands.runOnce(() -> m_visionSubsystem.setDriverMode(true)));
    
    // Example: Switch to vision processing mode with X button
    m_driverController.x().onTrue(Commands.runOnce(() -> m_visionSubsystem.setDriverMode(false)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example autonomous command that drives forward for 2 seconds
    return Commands.run(() -> m_driveSubsystem.arcadeDrive(0.5, 0.0), m_driveSubsystem)
        .withTimeout(2.0);
  }
}

# Contributing to BonacBots2026-KitbotAlpha

Thank you for contributing to our FRC robot code! This document provides guidelines for development.

## Development Environment Setup

### Required Software
1. **WPILib 2026** - Download from [WPILib Releases](https://github.com/wpilibsuite/allwpilib/releases)
2. **VS Code** with WPILib extension (included in WPILib installer)
3. **Java JDK 17** (included in WPILib installer)
4. **Git** for version control

### Initial Setup
```bash
# Clone the repository
git clone https://github.com/ehschools/BonacBots2026-KitbotAlpha.git
cd BonacBots2026-KitbotAlpha

# Build the project
./gradlew build
```

## Code Style Guidelines

### Java Style
- Follow WPILib Java style conventions
- Use 2 spaces for indentation (not tabs)
- Maximum line length: 100 characters
- Use descriptive variable names
- Add Javadoc comments for all public methods and classes

### Naming Conventions
- Classes: `PascalCase` (e.g., `DriveSubsystem`)
- Methods: `camelCase` (e.g., `arcadeDrive`)
- Constants: `kPascalCase` or `ALL_CAPS` for static finals (e.g., `kMaxSpeed`)
- Private members: `m_camelCase` (e.g., `m_leftMotor`)

### Example Class Structure
```java
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Brief description of the subsystem.
 */
public class ExampleSubsystem extends SubsystemBase {
  // Constants
  private static final double kDefaultValue = 1.0;
  
  // Private members
  private final Motor m_motor;
  private double m_currentValue;

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    m_motor = new Motor();
    m_currentValue = kDefaultValue;
  }

  /**
   * Sets the value.
   * 
   * @param value The new value to set
   */
  public void setValue(double value) {
    m_currentValue = value;
  }

  @Override
  public void periodic() {
    // Called every robot loop
  }
}
```

## Making Changes

### Before You Start
1. Create a new branch for your feature/fix
2. Pull the latest changes from main
3. Ensure your environment builds successfully

### Development Workflow
```bash
# Create a feature branch
git checkout -b feature/your-feature-name

# Make your changes
# Test your changes

# Add and commit
git add .
git commit -m "Brief description of changes"

# Push to GitHub
git push origin feature/your-feature-name

# Create a Pull Request on GitHub
```

### Commit Message Guidelines
- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- Keep first line under 50 characters
- Reference issues and pull requests when relevant

Examples:
```
Add arcade drive deadband filtering
Fix motor controller CAN ID conflict
Update vision subsystem for AprilTag detection
```

## Testing

### Running Tests
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests ConstantsTest
```

### Writing Tests
- Write unit tests for all new functionality
- Place tests in `src/test/java/frc/robot/`
- Use JUnit 5 for testing
- Aim for good test coverage

### Testing on Robot
1. Connect to robot via USB or WiFi
2. Build and deploy: `./gradlew deploy`
3. Enable robot in Driver Station
4. Test all functionality
5. Check for console errors

## Configuration Management

### Constants
All robot configuration should be in `Constants.java`:
- CAN IDs
- Controller ports
- Speed limits
- Vision parameters

### Vendor Dependencies
Add vendor dependencies via WPILib VS Code:
1. Open Command Palette (Ctrl+Shift+P)
2. Select "WPILib: Manage Vendor Libraries"
3. Choose "Install new libraries (online)" or "Install new libraries (offline)"

## Subsystems and Commands

### Creating a New Subsystem
1. Create class in `src/main/java/frc/robot/subsystems/`
2. Extend `SubsystemBase`
3. Add hardware initialization in constructor
4. Implement `periodic()` for regular updates
5. Add to `RobotContainer`

### Creating a New Command
1. Create class in `src/main/java/frc/robot/commands/`
2. Extend `Command`
3. Declare subsystem requirements in constructor
4. Implement `initialize()`, `execute()`, `end()`, `isFinished()`
5. Bind to button in `RobotContainer.configureBindings()`

## Documentation

### Code Documentation
- Add Javadoc comments to all public classes and methods
- Explain non-obvious code with inline comments
- Update README.md when adding major features

### User Documentation
- Update LIMELIGHT_SETUP.md for vision changes
- Document any special setup or configuration requirements
- Include troubleshooting tips for common issues

## Pull Request Process

1. **Update Documentation**: Ensure README and other docs are updated
2. **Test Thoroughly**: Test on actual robot hardware when possible
3. **Code Review**: At least one team member must review
4. **Passing Tests**: All tests must pass
5. **Clean Commits**: Squash commits if needed for clean history

## Questions?

If you have questions or need help:
- Ask in team Slack/Discord channel
- Consult team mentors
- Check FRC documentation at https://docs.wpilib.org/

## Resources

- [WPILib Documentation](https://docs.wpilib.org/)
- [FRC Game Manual](https://www.firstinspires.org/resource-library/frc/competition-manual-qa-system)
- [Chief Delphi Forums](https://www.chiefdelphi.com/)
- [REVLib Documentation](https://docs.revrobotics.com/)
- [Limelight Documentation](https://docs.limelightvision.io/)

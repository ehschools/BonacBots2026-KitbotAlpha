# Limelight 4 Setup Guide

## Overview
The Limelight 4 is an advanced vision camera designed for FRC robotics. It features:
- 240 FPS processing at 320x240 resolution
- Integrated LED ring for target illumination
- AprilTag detection and tracking
- MegaTag2 multi-target fusion
- Network-based communication via NetworkTables

## Hardware Setup

### Mounting
1. Mount the Limelight 4 at a known height on your robot
2. Record the camera height from the ground
3. Record the camera's pitch angle (tilt)
4. Ensure the camera has a clear field of view

### Network Configuration
1. Connect the Limelight 4 to your robot's network switch
2. The Limelight will auto-configure on the robot network
3. Default hostname: `limelight.local`
4. Access web interface at: `http://limelight.local:5801`

## Software Configuration

### Pipeline Setup
1. Access the Limelight web interface
2. Navigate to the "Pipeline" tab
3. Configure pipeline 0 for AprilTag detection:
   - Set detector to "AprilTag (3D)"
   - Enable MegaTag2 if using multiple tags
   - Adjust exposure and gain for your lighting conditions

### NetworkTables Integration
The VisionSubsystem in this project uses NetworkTables to communicate with the Limelight:

```java
// NetworkTable entries
tv  - Valid target (0 or 1)
tx  - Horizontal offset from crosshair (-29.8 to 29.8 degrees)
ty  - Vertical offset from crosshair (-24.85 to 24.85 degrees)
ta  - Target area (0% to 100%)
tid - AprilTag ID
tl  - Pipeline latency (ms)

// Control entries
ledMode   - LED mode (0=pipeline, 1=off, 2=blink, 3=on)
camMode   - Camera mode (0=vision, 1=driver)
pipeline  - Active pipeline (0-9)
```

## Usage in Code

### Reading Target Information
```java
VisionSubsystem vision = new VisionSubsystem();

// Check if target is visible
if (vision.hasTarget()) {
    double yaw = vision.getTargetYaw();        // Horizontal angle
    double pitch = vision.getTargetPitch();    // Vertical angle
    double area = vision.getTargetArea();      // Target size
    int aprilTagId = vision.getAprilTagID();   // Tag identifier
}
```

### Calculating Distance
```java
// Calculate distance to target
double targetHeight = 1.5;  // meters (height of AprilTag)
double cameraHeight = 0.5;  // meters (height of camera)
double cameraPitch = 20.0;  // degrees (camera angle)

double distance = vision.getDistanceToTarget(
    targetHeight, 
    cameraHeight, 
    cameraPitch
);
```

### Controlling LEDs
```java
// Toggle LEDs
vision.toggleLEDs();

// Set specific mode
vision.setLEDMode(VisionConstants.kLedModeOn);
```

### Camera Modes
```java
// Switch to driver camera (increased exposure)
vision.setDriverMode(true);

// Switch to vision processing
vision.setDriverMode(false);
```

## Calibration

### Camera Calibration
1. Access the Limelight web interface
2. Navigate to "Settings" > "Camera Calibration"
3. Follow the calibration wizard with a checkerboard pattern
4. Save calibration data

### Field Calibration
1. Measure exact AprilTag heights for your field
2. Update the target height constants in your code
3. Measure and update your camera mounting height
4. Measure and update your camera pitch angle
5. Test distance calculations and adjust as needed

## Troubleshooting

### No Target Detected
- Check LED mode (should be ON for vision processing)
- Verify camera mode (should be 0 for vision processing)
- Check pipeline configuration in web interface
- Ensure AprilTags are within camera field of view
- Check lighting conditions

### Inaccurate Distance
- Verify camera height measurement
- Verify camera pitch angle
- Check target height configuration
- Ensure AprilTag is properly mounted and flat

### Network Issues
- Verify Limelight is powered on
- Check network switch connections
- Ping limelight.local to verify network connectivity
- Check firewall settings on driver station

## Additional Resources
- Limelight Documentation: https://docs.limelightvision.io/
- FRC AprilTag Specifications: https://firstfrc.blob.core.windows.net/frc2024/FieldAssets/AprilTags.pdf
- NetworkTables Documentation: https://docs.wpilib.org/en/stable/docs/software/networktables/

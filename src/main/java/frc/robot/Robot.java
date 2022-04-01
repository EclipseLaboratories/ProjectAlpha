// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.KilloughDrive;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  XboxController control;
  
  // Talon SRX motor controllers
  WPI_TalonSRX m_Side1Lead;
  WPI_TalonSRX m_Side1Follow;
  WPI_TalonSRX m_Side2Lead;
  WPI_TalonSRX m_Side2Follow;
  WPI_TalonSRX m_Side3Lead;
  WPI_TalonSRX m_Side3Follow;

  MotorController m_Left;
  MotorController m_Right;
  MotorController m_Back;
  
  KilloughDrive m_drivetrain;

  PowerDistribution pdp;

  AddressableLED leftEye;
  AddressableLEDBuffer leftBuffer;
  
  AddressableLED rightEye;
  AddressableLEDBuffer rightBuffer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    control = new XboxController(0);

    // Talon SRX motor controllers
    m_Side1Lead = new WPI_TalonSRX(0);
    m_Side1Follow = new WPI_TalonSRX(1);
    m_Side2Lead = new WPI_TalonSRX(2);
    m_Side2Follow = new WPI_TalonSRX(3);
    m_Side3Lead = new WPI_TalonSRX(4);
    m_Side3Follow = new WPI_TalonSRX(5);

    // Set up the motor controllers to follow each other
    m_Side1Follow.follow(m_Side1Lead);
    m_Side2Follow.follow(m_Side2Lead);
    m_Side3Follow.follow(m_Side3Lead);

    m_drivetrain = new KilloughDrive(m_Side1Lead, m_Side2Lead, m_Side3Lead);

    pdp = new PowerDistribution(0, ModuleType.kCTRE);

    // LED Code!
    leftEye = new AddressableLED(8);
    leftBuffer = new AddressableLEDBuffer(19);
    leftEye.setData(leftBuffer);
    leftEye.setLength(leftBuffer.getLength());
    leftEye.start();
    
    rightEye = new AddressableLED(9);
    rightBuffer = new AddressableLEDBuffer(19);
    rightEye.setData(rightBuffer);
    rightEye.setLength(rightBuffer.getLength());
    rightEye.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("PDP Voltage", pdp.getVoltage());
    SmartDashboard.putNumber("PDP Current", pdp.getCurrent(0));
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_drivetrain.driveCartesian(control.getLeftX(), control.getLeftY(), control.getRightX());
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

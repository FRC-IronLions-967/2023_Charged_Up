// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.*;
import frc.robot.auto.choices.CubeLeavingAuto;
import frc.robot.auto.choices.DoNothingAuto;
import frc.robot.auto.choices.SingleCubeAuto;
import frc.robot.subsystems.*;
import frc.robot.commands.*;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kSingleCubeAuto = "Single Piece Auto";
  private static final String kCubeLeavingAuto = "Cube and Exit Community Auto";
  private static final String kEngagedAuto = "Auto Balancing Auto";
  private static final String kDoNothingAuto = "Does Nothing Auto";
  private String autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private SubsystemsInstance subsystemsInst;
  private CubeLeavingAuto cubeLeavingAuto;
  private SingleCubeAuto singleCubeAuto;
  private DoNothingAuto doNothingAuto;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.addOption(kSingleCubeAuto, kSingleCubeAuto);
    m_chooser.setDefaultOption(kCubeLeavingAuto, kCubeLeavingAuto);
    m_chooser.addOption(kEngagedAuto, kEngagedAuto);
    m_chooser.addOption(kDoNothingAuto, kDoNothingAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    SmartDashboard.putNumber("maxAccel", 0.02d);
    SmartDashboard.putNumber("scale", 0.5d);
    SmartDashboard.putNumber("zeroTurn", 0.5d);



    subsystemsInst = SubsystemsInstance.getInstance();
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
    CommandScheduler.getInstance().run();
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
    autoSelected = m_chooser.getSelected();
    autoSelected = SmartDashboard.getString("Auto Selector", kCubeLeavingAuto);
    System.out.println("Auto selected: " + autoSelected);
    if(m_chooser){
      
    }
    
    
    
    
    
    // auto = new Autonomous();
    // auto.init();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // auto.periodic();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    IO.getInstance().teleopInt();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
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
}

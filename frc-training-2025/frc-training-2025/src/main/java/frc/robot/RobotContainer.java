// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.MoveForDistance;
import frc.robot.commands.MoveForTime;
import frc.robot.commands.MoveWithPID;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private Drivetrain m_drivetrain = new Drivetrain();
  private Joystick m_joystick = new Joystick(IOConstants.kJoystickPort);
  private ArcadeDrive m_arcadeDrive = new ArcadeDrive(m_joystick, m_drivetrain);

  private MoveForTime m_moveForTime = new MoveForTime(m_drivetrain, DrivetrainConstants.kTargetTimeSeconds, DrivetrainConstants.kMoveForTimeSpeedOne);
  private MoveForDistance m_moveForDistance = new MoveForDistance(m_drivetrain, DrivetrainConstants.kMoveForDistanceTargetFeet, DrivetrainConstants.kMoveForDistanceSpeed);
  private MoveWithPID m_moveWithPID = new MoveWithPID(m_drivetrain, DrivetrainConstants.kMoveWithPIDDistanceTargetFeet);

  public RobotContainer() {
    m_drivetrain.setDefaultCommand(m_arcadeDrive);
    SendableRegistry.add(m_moveWithPID.getMoveForDistanceSendable(), "MoveWithPID");
    Shuffleboard.getTab("SmartDashboard").add(m_moveWithPID.getMoveForDistanceSendable()).withWidget("MoveWithPID");
    SendableRegistry.add(m_moveForDistance.getMoveForDistanceSendable(), "MoveForDistance");
    Shuffleboard.getTab("SmartDashboard").add(m_moveForDistance.getMoveForDistanceSendable()).withWidget("MoveForDistance");
    // SmartDashboard.putData(m_moveForDistance.getMoveForDistanceSendable());
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return m_moveWithPID;
    //return m_moveForDistance;
    // return m_moveForTime;
  }
}

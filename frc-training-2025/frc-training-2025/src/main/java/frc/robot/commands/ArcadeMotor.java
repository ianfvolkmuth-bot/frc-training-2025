// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IOConstants;
import frc.robot.constants.MotorConstants;
import frc.robot.subsystems.Motor;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ArcadeMotor extends Command {
  /** Creates a new ArcadeMotor. */
  private Motor m_motor;
  private Joystick m_joystick;
  private double m_speed = 0;
  public ArcadeMotor(Joystick joystick, Motor motor) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_motor);
    // double m_speed = 0;
    

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_speed = m_joystick.getRawAxis(IOConstants.kMotorControlAxis);
    m_speed *= MotorConstants.kMotorMultiplier;
    m_motor.setSpeed(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_motor.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

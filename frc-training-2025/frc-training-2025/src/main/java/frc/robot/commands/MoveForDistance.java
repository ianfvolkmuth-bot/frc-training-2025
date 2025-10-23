// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveForDistance extends Command {
  /** Creates a new MoveForDistance. */
  private Drivetrain m_drivetrain;
  private double m_speed;
  private double m_targetDistanceTicks;
  private double m_currentTicks;
  private double m_errorTicks;
  
  public MoveForDistance(Drivetrain drivetrain, double targetDistanceFeet, double speed) {
    m_drivetrain = drivetrain;
    m_speed = speed;
    m_targetDistanceTicks = targetDistanceFeet * DrivetrainConstants.kTicksPerRotation / (2 * Math.PI * DrivetrainConstants.kWheelRadiusFeet);
    addRequirements(m_drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_currentTicks = m_drivetrain.getTicks();
    m_targetDistanceTicks += m_currentTicks;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_currentTicks = m_drivetrain.getTicks();
    m_errorTicks = m_targetDistanceTicks - m_currentTicks;
    m_drivetrain.setLeftSpeed(m_speed);
    m_drivetrain.setRightSpeed(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_errorTicks == 0);
  }
}

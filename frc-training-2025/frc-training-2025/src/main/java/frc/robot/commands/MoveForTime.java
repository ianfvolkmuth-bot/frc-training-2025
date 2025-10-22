// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveForTime extends Command {
  /** Creates a new MoveForTime. */
  private Timer m_timer = new Timer();
  private Drivetrain m_drivetrain;
  private double m_targetTimeSeconds;
  private double m_speed;

  public MoveForTime(Drivetrain drivetrain, double targetTimeSeconds, double speed) {
    m_drivetrain = drivetrain;
    m_targetTimeSeconds = targetTimeSeconds;
    m_speed = speed;
    addRequirements(m_drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.reset();
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.setLeftSpeed(m_speed);
    m_drivetrain.setRightSpeed(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.setLeftSpeed(0);
    m_drivetrain.setRightSpeed(0);
    m_timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_timer.hasElapsed(m_targetTimeSeconds);
  }
}

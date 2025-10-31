// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveWithPID extends Command {
  /** Creates a new MoveWithPID. */
  private Drivetrain m_drivetrain;
  private double m_setpoint;
  private PIDController m_pidController = new PIDController(DrivetrainConstants.kPMoveWithPID, DrivetrainConstants.kIMoveWithPID, DrivetrainConstants.kDMoveWithPID);

  public MoveWithPID(Drivetrain drivetrain, double setpointFeet) {
    m_drivetrain = drivetrain;
    m_setpoint = setpointFeet * DrivetrainConstants.kTicksPerRotation / (2 * Math.PI * DrivetrainConstants.kWheelRadiusFeet);
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_setpoint += m_drivetrain.getLeftPrimaryMotorTicks();
    m_pidController.setTolerance(0.5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.setLeftSpeed(m_pidController.calculate(m_drivetrain.getLeftPrimaryMotorTicks(), m_setpoint));
    m_drivetrain.setRightSpeed(m_pidController.calculate(m_drivetrain.getLeftPrimaryMotorTicks(), m_setpoint));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.setLeftSpeed(0);
    m_drivetrain.setRightSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pidController.atSetpoint();
  }
  private class MoveWithPIDSendable implements Sendable {
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("MoveForDistance");
        builder.addDoubleProperty("Speed", () -> m_pidController.calculate(m_drivetrain.getLeftPrimaryMotorTicks(), m_setpoint), null);
        builder.addDoubleProperty("Current In Ticks", () -> m_drivetrain.getLeftPrimaryMotorTicks(), null);
        builder.addDoubleProperty("Target In Ticks", () -> m_setpoint, null);
        builder.addDoubleProperty("Error In Ticks", () -> m_setpoint - m_drivetrain.getLeftPrimaryMotorTicks(), null);
    }
  }

  MoveWithPIDSendable m_moveForDistanceSendable = new MoveWithPIDSendable();

  public MoveWithPIDSendable getMoveForDistanceSendable() {
    return m_moveForDistanceSendable;
  }
}

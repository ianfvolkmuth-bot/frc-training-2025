// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Motor. */
  private TalonSRX m_leftPrimaryMotor = new TalonSRX(DrivetrainConstants.kLeftPrimaryMotorID);
  private TalonSRX m_rightPrimaryMotor = new TalonSRX(DrivetrainConstants.kRightPrimaryMotorID);

  private VictorSPX m_leftSecondaryMotor = new VictorSPX(DrivetrainConstants.kLeftSecondaryMotorID);
  private VictorSPX m_rightSecondaryMotor = new VictorSPX(DrivetrainConstants.kRightSecondaryMotorID);

  public Drivetrain() {
    m_leftPrimaryMotor.setNeutralMode(NeutralMode.Brake);
    m_rightPrimaryMotor.setNeutralMode(NeutralMode.Brake);
    m_leftSecondaryMotor.follow(m_leftPrimaryMotor);
    m_rightSecondaryMotor.follow(m_rightPrimaryMotor);
    m_rightPrimaryMotor.setInverted(true);
    // Would we only need to invert the primary motor since the secondary motor is following it?
    m_rightSecondaryMotor.setInverted(true);
  }
  public void setLeftSpeed(double speed) {
    m_leftPrimaryMotor.set(TalonSRXControlMode.PercentOutput, speed);
    // The secondary motor should automatically be set to the same speed because its following, right?
  }

  public void setRightSpeed(double speed) {
    m_rightPrimaryMotor.set(TalonSRXControlMode.PercentOutput, speed);
  }


  public double getLeftSpeed() {
    return m_rightPrimaryMotor.getMotorOutputPercent();
  }

  public double getRightSpeed() {
    return m_leftPrimaryMotor.getMotorOutputPercent();
  }

  public double getTicks() {
    return m_leftPrimaryMotor.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Percent Output", getLeftSpeed());
    SmartDashboard.putNumber("Right Percent Output", getRightSpeed());
  }
}

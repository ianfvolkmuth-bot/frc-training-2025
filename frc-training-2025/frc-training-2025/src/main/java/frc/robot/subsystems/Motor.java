// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.MotorConstants;

public class Motor extends SubsystemBase {
  /** Creates a new Motor. */
  private SparkMax m_motor = new SparkMax(MotorConstants.kMotorID, MotorType.kBrushless);
  private SparkMaxConfig m_config = new SparkMaxConfig();
  public Motor() {
    m_config.idleMode(IdleMode.kBrake);
    m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void setSpeed(double speed) {
    m_motor.set(speed);
  }

  public double getSpeed() {
    return(m_motor.get());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Motor Speed", getSpeed());
  }
}

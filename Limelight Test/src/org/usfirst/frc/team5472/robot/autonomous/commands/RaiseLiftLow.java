package org.usfirst.frc.team5472.robot.autonomous.commands;

import org.usfirst.frc.team5472.robot.Robot;
import org.usfirst.frc.team5472.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseLiftLow extends Command{
	
	private LiftSubsystem lift;
	private boolean finished;

	@Override
	public void initialize() {
		lift = Robot.lift;
	}
	
	@Override
	public void execute() {
		lift.setSetpoint(17000);
		finished = lift.onTarget();
	}
	
	
	@Override
	protected boolean isFinished() {
		return finished;
	}
	
}

package org.usfirst.frc.team5472.robot.autonomous.commands.paths;

import org.usfirst.frc.team5472.robot.autonomous.commands.ApproachBox;
import org.usfirst.frc.team5472.robot.autonomous.commands.Delay;
import org.usfirst.frc.team5472.robot.autonomous.commands.Forward;
import org.usfirst.frc.team5472.robot.autonomous.commands.LiftZero;
import org.usfirst.frc.team5472.robot.autonomous.commands.RaiseLiftHalf;
import org.usfirst.frc.team5472.robot.autonomous.commands.RaiseLiftHigh;
import org.usfirst.frc.team5472.robot.autonomous.commands.Turn;
import org.usfirst.frc.team5472.robot.commands.BoxPipeline;
import org.usfirst.frc.team5472.robot.commands.EnableVision;
import org.usfirst.frc.team5472.robot.commands.GripClose;
import org.usfirst.frc.team5472.robot.commands.HighGear;
import org.usfirst.frc.team5472.robot.commands.IntakePull;
import org.usfirst.frc.team5472.robot.commands.IntakePushAuto;
import org.usfirst.frc.team5472.robot.commands.IntakeStop;
import org.usfirst.frc.team5472.robot.commands.LowGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RSCXL extends CommandGroup {

	public RSCXL() {
		addParallel(new GripClose());

		addParallel(new RaiseLiftHalf(), 3);
		addSequential(new Forward(4.80), 6);
		addSequential(new Turn(90), 2);
		
		addSequential(new Forward(4.2), 6); // From 4.6
		addSequential(new Turn(0), 2);
		
		addParallel(new LowGear());
		addSequential(new RaiseLiftHigh(), 4);
		addSequential(new Forward(1.05), 2); // From 0.8
		addSequential(new IntakePushAuto());
		addSequential(new Delay(1));
		addSequential(new IntakeStop());
		
		addSequential(new Forward(-0.7));
		addSequential(new LiftZero(), 3);
		addSequential(new Turn(160), 3);
		addSequential(new HighGear());
		
		addParallel(new IntakePull());
		addSequential(new EnableVision());
		addSequential(new BoxPipeline());
		addSequential(new ApproachBox(), 3);
		addSequential(new IntakeStop());
		addSequential(new GripClose());
		addSequential(new Forward(-0.600));
	}
}

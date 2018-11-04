package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;
import frc.team3683.burd.utils.Point;

public class OnePlusOneRightRight extends CommandGroup {

    public OnePlusOneRightRight(){
        addParallel(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));

        addSequential(new WaitCommand(0.1));

        addSequential(new DrivePathElevator(
                new Point(0, 0),
                new Point(0, 75),
                new Point(48, 38),
                new Point(45, 102),
                2.0,
                0.65,
                100,
                Constants.INSTANCE.getSwitchHeight(),
                800,
                Constants.INSTANCE.getPotMid(),
                800));
        //addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        //addParallel(new WristCommand(130));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getSwitchHeight()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()));
        addParallel(new DriveStraight(2, 0.25, 0.25));
        addSequential(new IntakeCommand(0.3, 0.5));
        addSequential(new WaitCommand(0.1));
        addParallel(new DelayedElevatorCommand(Constants.INSTANCE.getBottom(), 0.5));
        addSequential(new DriveStraightAbsolute(-82, 1.0, 1.8, 0, 1.0, true, 12.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown() - 3));
        addSequential(new DriveAngleAbsolute(-37, 0.6, 0.6));
        addParallel(new DriveStraightAbsolute(76, 0.8, 2.5, -37, 1.0, true, 24.0));
        addSequential(new CurrentIntakeCommand(0.8));
        addSequential(new WaitCommand(0.1));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));
        addParallel(new IntakeCommand(-0.5, 2.5));
        addSequential(new DriveStraightAbsoluteDec(-20, 1.0, 1.0, -37, 2.0, true, 0.5, 0.8));
        addSequential(new DriveAngleAbsolute(60, 0.8, 0.75));
        addSequential(new DriveStraightAbsoluteDec(99, 1.0, 2.0, 60, 2.0, true, 0.8, 0.9));
        //end of which way switch auto sequence



        //addSequential(new DriveAngleAbsolute(0, 1.0, 1.0));
        //approach scale, changed to addParallel
        addSequential(new DriveStraightAbsoluteDec(159, 1.0, 6.0, 0.0, 8.0, true, 0.6, 0.8));
        //addParallel(new DriveAngleAbsolute(-60, 0.6, 2.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));

        addParallel(new AutoGearShift(false));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 25));
        addSequential(new DriveStraightAbsoluteDec(30, 0.6, 6.0, -60, 6.0, true, 0.6, 0.75));

        //turn into scale and prepare to shoot
        addSequential(new DriveAngleAbsoluteElevator(-60,
                0.7,
                1.5,
                Constants.INSTANCE.getHighScale(),
                -30,
                Constants.INSTANCE.getPotMid() + 25,
                -30));

        //shoot

        addSequential(new IntakeCommand(0.8, 0.5));

        addSequential(new DriveStraight(-12, 0.6, 1.0));

        //Lower the elevator
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        //addSequential(new WaitCommand(0.1));

        //Drop the intake
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown() ));
        addSequential(new WaitCommand(0.25));


        }
}

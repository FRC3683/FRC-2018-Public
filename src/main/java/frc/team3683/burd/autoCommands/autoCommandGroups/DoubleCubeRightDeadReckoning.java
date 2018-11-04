package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

public class DoubleCubeRightDeadReckoning extends CommandGroup {

    public DoubleCubeRightDeadReckoning(){
        addSequential(new AutoGearShift(true));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));

        //approach scale
        addSequential(new DriveStraightAbsoluteDec(272, 1.0, 6.0, 2.0, 1.0, true, 0.5, 0.75));

        //turn into scale and prepare to shoot
        addParallel(new AutoGearShift(false));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveAngleAbsoluteElevator(-60,
                0.5,
                1.5,
                Constants.INSTANCE.getHighScale(),
                -30,
                Constants.INSTANCE.getPotMid() + 20,
                -30));

        //shoot
        addSequential(new IntakeCommand(0.8, 0.5));

        //Lower the elevator
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.1));

        //Drop the intake
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new WaitCommand(0.25));

        //Turn towards the cube. Start to nom the cube
        addSequential(new DriveAngleAbsolute(-150, 0.55, 1.3));
        addParallel(new DriveStraightAbsoluteDec(88, 1.0, 1.5, -150, 1.0, true, 0.4, 0.5));
        addSequential(new CurrentIntakeCommand(0.8));

        //Lift up the second secured second cube
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));

        //drive back to scale
        addParallel(new IntakeCommand(-0.4, 2.0));
        addSequential(new DriveStraightAbsoluteDec(-88, 1.0, 2.0, -150, 1.0, true, 0.5, 0.75));

        //Turn back into shooting position
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addSequential(new DriveAngleAbsoluteElevator(-60,
                0.5,
                2.0,
                Constants.INSTANCE.getHighScale(),
                -30,
                Constants.INSTANCE.getPotUp(),
                -30));

        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new DriveStraightAbsoluteDec(20, 0.5, 1.0, -60, 1.0, true, 0.2, .5));
        //shoot again, and back off to face the opposing alliacne
        addSequential(new IntakeCommand(0.8, 0.5));
        addSequential(new DriveStraight(-8, 0.3, 0.5));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new DriveStraight(-20, 0.5, 1.0));
        addSequential(new DriveAngleAbsolute(0, 0.6, 1.5));

    }
}

package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

public class PranitTwoCubeRightFarSideDR extends CommandGroup {

    public PranitTwoCubeRightFarSideDR(){

        //Initial conditions
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        //addSequential(new WaitCommand(0.1));
        addParallel(new AutoGearShift(true));

        //Drive forward
        addSequential(new DriveStraightAbsoluteDec(206, 1.0, 6.0, 0.0, 6.0, true, 0.4, 0.9));

        //turn 90
        addSequential(new DriveAngleAbsolute(-90, 0.8, 3.0));

        //Drive towards the far side auto
        addSequential(new DriveStraightAbsoluteDec(230, 1.0, 6.0, -90.0, 12.0, true, 0.5, 0.9));

        //Turn back towards the scale
        addSequential(new DriveAngleAbsolute(38, 0.8, 1.0));

        //Drive towards the scale and raise the elevator
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 20));
        addSequential(new DriveStraightAbsoluteDec(32, 1.0, 6.0, 45.0, 12.0, true, 0.75, 0.75));



        //Shooting stuff goes up and shoot, pew pew pew
        addSequential(new WaitCommand(0.4));
        addSequential(new IntakeCommand(0.45, 0.35));

        //Bring the elevator and stuff down
        addParallel(new AutoGearShift(false));
        addSequential(new DriveStraightAbsoluteDec(-18, 0.8, 1.0, 30.0, 3.0, true, 0.6, 0.5));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        //addSequential(new WaitCommand(0.1));

        //Turn towards the cube. Start to nom the cube
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown()));
        addSequential(new DriveAngleAbsolute(130, 0.5, 1.5));
        addParallel(new DriveStraightAbsoluteDec(84, 1.0, 2.5, 145, 1.0, true, 0.5, 0.3));
        addSequential(new CurrentIntakeCommand(0.8));

        //Go back towards the scale
        addParallel(new IntakeCommand(-0.4, 2.0));
        addParallel(new AutoGearShift(true));
        addSequential(new DriveStraightAbsoluteDec(-76, 1.0, 1.5, 145, 12.0, true, 0.4, 0.75));

        //Turn back, and shoot
        addSequential(new DriveAngleAbsoluteElevator(60,
                0.6,
                1.5,
                Constants.INSTANCE.getHighScale(),
                30,
                Constants.INSTANCE.getPotMid() + 25,
                30));

        addSequential(new IntakeCommand(0.55, 0.45));

        //Go back to a dan friendly position
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new WaitCommand(0.1));
        addSequential(new DriveAngleAbsolute(-90, 0.6, 1.5));
    }
}

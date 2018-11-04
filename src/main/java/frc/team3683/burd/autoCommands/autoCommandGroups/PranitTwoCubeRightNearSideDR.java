package frc.team3683.burd.autoCommands.autoCommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3683.burd.autoCommands.*;
import frc.team3683.burd.configs.Constants;

public class PranitTwoCubeRightNearSideDR extends CommandGroup {

    public PranitTwoCubeRightNearSideDR(){
        addSequential(new AutoGearShift(true));
        addSequential(new WaitCommand(0.1));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));

        //approach scale, changed to addParallel
        addSequential(new DriveStraightAbsoluteDec(246, 1.0, 6.0, 2.5, 8.0, true, 0.6, 0.8));
        //addParallel(new DriveAngleAbsolute(-60, 0.6, 2.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid() + 20));

        addParallel(new AutoGearShift(false));
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addSequential(new DriveStraightAbsoluteDec(30, 0.6, 6.0, -50, 6.0, true, 0.6, 0.75));

        //turn into scale and prepare to shoot
        addSequential(new DriveAngleAbsoluteElevator(-50,
                0.7,
                1.5,
                Constants.INSTANCE.getHighScale(),
                -30,
                Constants.INSTANCE.getPotMid() + 20,
                -30));

        //shoot
        //addSequential(new WaitCommand(0.1));
        addSequential(new IntakeCommand(0.8, 0.5));

        //Lower the elevator
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
       //addSequential(new WaitCommand(0.1));

        //Drop the intake
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp() ));
        addSequential(new WaitCommand(0.25));

        //Turn towards the cube. Start to nom the cube
        addParallel(new WristCommand(Constants.INSTANCE.getPotDown() ));

        addSequential(new DriveAngleAbsolute(-135, 0.55, 1.3)); //changed from addSequential
        addParallel(new DriveStraightAbsoluteDec(88, 1.0, 2.5, -150, 1.0, true, 0.3, 0.3));
        addSequential(new CurrentIntakeCommand(0.8));

        //Lift up the second secured second cube
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addSequential(new WaitCommand(0.1));

        //drive back to scale
        addParallel(new IntakeCommand(-0.4, 2.0));
        addSequential(new DriveStraightAbsoluteDec(-94, 1.0, 2.0, -145, 12.0, false, 0.7, 0.75)); //changed boolean


        //Turn back into shooting position
        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()+ 25));
        addSequential(new DriveAngleAbsoluteElevator(-65,
                0.6,
                3.0,
                Constants.INSTANCE.getHighScale(),
                -30,
                Constants.INSTANCE.getPotMid()+ 25 ,
                -30));

        addParallel(new ElevatorCommand(Constants.INSTANCE.getHighScale()));
        addParallel(new WristCommand(Constants.INSTANCE.getPotMid()+ 25));
        addSequential(new DriveStraightAbsoluteDec(20, 0.5, 2.0, -65, 10.0, true, 0.2, 0.5));
        //shoot again, and back off to face the opposing alliacne
        addSequential(new IntakeCommand(0.5, 0.5));
        addSequential(new DriveStraight(-16, 0.45, 1.0));
        addParallel(new WristCommand(Constants.INSTANCE.getPotUp()));
        addParallel(new ElevatorCommand(1.0));
        addSequential(new DriveStraight(-20, 0.5, 1.0));
        addSequential(new DriveAngleAbsolute(0, 0.6, 1.5));

    }
}

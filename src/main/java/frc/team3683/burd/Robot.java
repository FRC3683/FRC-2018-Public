/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3683.burd;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3683.burd.autoCommands.autoCommandGroups.*;
import frc.team3683.burd.commands.ExampleCommand;
import frc.team3683.burd.commands.IntakeControl;
import frc.team3683.burd.configs.Comp;
import frc.team3683.burd.configs.Config;
import frc.team3683.burd.configs.Practice;
import frc.team3683.burd.subsystems.DriveBase;
import frc.team3683.burd.subsystems.Elevator;
import frc.team3683.burd.subsystems.ExampleSubsystem;
import frc.team3683.burd.subsystems.Intake;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */

/**
 *
 */
public class Robot extends TimedRobot {
	public static boolean compBot;
	private static OI oi;
	private static Config mRobotConfig;
	private static DriveBase mDriveBase;
	private static Intake mIntake;
	private static Elevator mElevator;
	private static ExampleSubsystem mExampleSubsystem;
	private static PowerDistributionPanel mPDP;
	private Command m_autonomousCommand;
	private List<String> autoModes;
	private boolean locked;
	private SendableChooser<Command> m_chooser = new SendableChooser<>();
	private int selectedMode;
	private boolean isBlue;





	/**
	 * This function is run when the burd is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		LiveWindow.disableAllTelemetry();
		locked = false;
		selectedMode = 0;
		mRobotConfig = getConfigType();
		oi = OI.getInstantce();
		mDriveBase = new DriveBase(mRobotConfig);
		mIntake = new Intake(mRobotConfig);
		mElevator = new Elevator(mRobotConfig);
		mExampleSubsystem = new ExampleSubsystem();
		mPDP = new PowerDistributionPanel(0);
		CameraServer.getInstance().startAutomaticCapture();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		mRobotConfig.getDriveEncoderRight().setDistancePerPulse(1/mRobotConfig.getDriveTicksPerInchRight());
		mRobotConfig.getDriveEncoderLeft().setDistancePerPulse(1/mRobotConfig.getDriveTicksPerInchLeft());
	}

	/**
	 * This function is called once each time the burd enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the burd is disabled.
	 */
	@Override
	public void disabledInit() {
		autoModes = new ArrayList<>();
		autoModes.add("do nothing");
		autoModes.add("2 scale right");//2 cube scale, near or far, right side
		autoModes.add("Which way?");//2 cube switch middle
		autoModes.add("one plus one");//one plus one all four
		autoModes.add("right side switch");//right side switch
		autoModes.add("hardcode right scale");
		getmDriveBase().coastMode();
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putString("DB/String 7", Double.valueOf(getmDriveBase().getAbsoluteHeading()).toString());
		getmIntake().outputLight();
		Scheduler.getInstance().run();
		String autoMode;
		//SmartDashboard.putString(String.format("DB/String 9"), String.valueOf(Robot.getmDriveBase().getHeading()));
		//SmartDashboard.putString(String.format("DB/String 8"), "red: " + String.valueOf(Robot.getmDriveBase().getmColourSensor().getRawData().get(0)));
		if(!locked) {
			if (oi.getDPadRight()) {
				if(selectedMode < autoModes.size() - 1){
					selectedMode++;
				} else
					selectedMode = 0;
			}
			if (oi.getDPadLeft()) {
				if(selectedMode > 0){
					selectedMode--;
				} else {
					selectedMode = autoModes.size()-1;
				}
			}
		}
		autoMode = autoModes.get(selectedMode);
		if(oi.getStartButtonDown()){
			locked = !locked;
		}
		if(locked) {
			SmartDashboard.putString(String.format("DB/String 8"), "BURD IS LOCKED");
		} else {
			SmartDashboard.putString(String.format("DB/String 8"), "...");
		}


		SmartDashboard.putString(String.format("DB/String 9"), autoMode);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		mPDP.clearStickyFaults();
		mDriveBase.clearSticky();
		String gameData = null;
		while(gameData == null || gameData.isEmpty()) {
			gameData = DriverStation.getInstance().getGameSpecificMessage();
		}
		boolean switchRight;
		boolean scaleRight;
		switchRight = Objects.equals(String.valueOf(gameData.charAt(0)), "R");
		scaleRight = Objects.equals(String.valueOf(gameData.charAt(1)), "R");

		if(selectedMode == 0){ //do nothing
			m_autonomousCommand = (new DoNothingAuto());
		} else if (selectedMode == 1){ // 2 scale right
			if(scaleRight){
				m_autonomousCommand = (new PranitTwoCubeRightNearSideDR());
			} else {
				m_autonomousCommand = (new PranitTwoCubeRightFarSideDR());
			}
		} else if (selectedMode == 2){ // which way
			if(switchRight){
				m_autonomousCommand = (new WhichWayRight());
			} else {
				m_autonomousCommand = (new WhichWayLeft());
			}
		} else if (selectedMode == 3){ //one plus one
			if (switchRight) {
				if(scaleRight) {
					m_autonomousCommand = (new OnePlusOneRightRight());
				} else {
					m_autonomousCommand = (new OnePlusOneRightLeft());
				}
			} else {
				if(scaleRight) {
					m_autonomousCommand = (new OnePlusOneLeftRight());
				} else {
					m_autonomousCommand = (new OnePlusOneLeftLeft());
				}
			}

		} else if (selectedMode == 4){ //right side switch
			if(switchRight){
				m_autonomousCommand = (new PranitTwoCubeRightRightSideSwitchDR());
			} else {
				m_autonomousCommand = (new PranitTwoCubeRightLeftSideSwitchDR());
			}
		} else if(selectedMode == 5){
			m_autonomousCommand = (new PranitTwoCubeRightNearSideDR());
			/*else if(selectedMode == 5){ //one plus one
			if (switchRight) {
				if(scaleRight) {
					m_autonomousCommand = (new OnePlusOneRightRight());
				} else {
					m_autonomousCommand = (new OnePlusOneRightLeft());
				}
			} else {
				if(scaleRight) {
					m_autonomousCommand = (new OnePlusOneLeftRight());
				} else {
					m_autonomousCommand = (new OnePlusOneLeftLeft());
				}
			}
		} else if(selectedMode == 6){
			m_autonomousCommand = (new DriveStraightDelayedAuto());
		} else if(selectedMode == 7){ //single cube
			if(scaleRight){
				m_autonomousCommand = (new PranitTwoCubeRightNearSideDR());
			} else {
				m_autonomousCommand = (new PranitTwoCubeRightFarSideDR());
			}
		} else if(selectedMode == 8){
			if(scaleRight){
				m_autonomousCommand = (new DoubleCubeRightDeadReckoning());
			} else {
				m_autonomousCommand = (new FarScaleAutoDeadReckoning());
			}
		}
*/
		}
		if(m_autonomousCommand  == null){
			m_autonomousCommand = (new DriveStraightAuto());
		}


		mDriveBase.setAbsoluteOffset(mDriveBase.getAbsoluteHeading());
		//m_autonomousCommand = m_chooser.getSelected();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		getmDriveBase().brakeMode();
//		m_autonomousCommand = (new shittySquare());
		//m_autonomousCommand = (new PranitTwoCubeRightNearSideDR());
		//m_autonomousCommand = (new DriveTest());


		System.out.println("starting auto");
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		//getmDriveBase().brakeMode();

		//System.out.println("running auto");
		Scheduler.getInstance().run();
		SmartDashboard.putString(String.format("DB/String 5"),String.valueOf(getmDriveBase().getAbsoluteHeading()));
		List<String> encoders = new ArrayList<>();
		DecimalFormat twoDecimals = new DecimalFormat(".##");
		encoders.add(String.format("EncoderLeft %s", mRobotConfig.getDriveEncoderLeft().get()));
		encoders.add(String.format("EncoderRight %s", mRobotConfig.getDriveEncoderRight().get()));
		encoders.add(String.format(
				"DistanceLeft %s",
				twoDecimals.format(mRobotConfig.getDriveEncoderLeft().getDistance())));
		encoders.add(String.format(
				"DistanceRight %s",
				twoDecimals.format(mRobotConfig.getDriveEncoderRight().getDistance())));

		for(int i = 0; i < encoders.size(); i++){
			SmartDashboard.putString(String.format("DB/String %s", i), encoders.get(i));
		}

	}


	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		getmDriveBase().coastMode();
		new IntakeControl().start();
		mRobotConfig.getDriveEncoderLeft().reset();
		mRobotConfig.getDriveEncoderRight().reset();
		Robot.getmElevator().Pickup();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();
		oi.UpdateButtons();

		getmDriveBase().coastMode();
		List<String> encoders = new ArrayList<>();
		DecimalFormat twoDecimals = new DecimalFormat(".##");
		encoders.add(String.format("EncoderLeft %s", mRobotConfig.getDriveEncoderLeft().get()));
		encoders.add(String.format("EncoderRight %s", mRobotConfig.getDriveEncoderRight().get()));
		encoders.add(String.format(
				"DistanceLeft %s",
				twoDecimals.format(mRobotConfig.getDriveEncoderLeft().getDistance())));
		encoders.add(String.format(
				"DistanceRight %s",
				twoDecimals.format(mRobotConfig.getDriveEncoderRight().getDistance())));
		encoders.add(String.format(
				"ElevatorHeight %s",
				twoDecimals.format(Robot.getmElevator().getHeight())));
		for(int i = 0; i < encoders.size(); i++){
			SmartDashboard.putString(String.format("DB/String %s", i), encoders.get(i));
		}
		SmartDashboard.putBoolean("DB/LED 0", Robot.getmElevator().getDown());
		SmartDashboard.putBoolean("DB/LED 1", Robot.getmElevator().getUp());

		SmartDashboard.putString(String.format("DB/String 9"), String.valueOf(Robot.getmDriveBase().getHeading()));

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	//Helper functions
	private Config getConfigType() {

		boolean isComp;
		Config config;
		FileInputStream fileIn = null;

		try {
			fileIn = new FileInputStream("/robot.properties");
			Properties prop = new Properties();
			prop.load(fileIn);
			String isCompString = prop.getProperty("isComp");
			if(isCompString == null){
				throw new IOException("isComp property is null");
			}
			isComp = isCompString.equals("true");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error finding property file. Assuming Comp bot!!!");
			isComp = true;
		} finally {
			if(fileIn != null){
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("----------------------------------");
		if(isComp){
			System.out.println("Competition Robot Selected");
			config = Comp.INSTANCE;
			compBot = true;
		}
		else{
			System.out.println("Practice Robot Selected");
			config = Practice.INSTANCE;
			compBot = false;
		}
		System.out.println("----------------------------------");
		return config;
	}

	//Getters for subsystems

    public static DriveBase getmDriveBase() {
        return mDriveBase;
    }

    public static Config getmConfig(){
		return mRobotConfig;
	}

    public static Intake getmIntake() {
        return mIntake;
    }

    public static Elevator getmElevator() {
        return mElevator;
    }

	public static ExampleSubsystem getmExampleSubsystem() {
		return mExampleSubsystem;
	}

	public static PowerDistributionPanel getmPDP() { return mPDP; }
}

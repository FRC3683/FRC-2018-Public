package frc.team3683.burd;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the burd.
 */
public class OI {

    Joystick mPad;
    private boolean[] bButtonLastState = new boolean[15];
    private static OI instance;

    private OI(){
        mPad = new Joystick(0);
    }

    public static OI getInstantce(){
        if (instance == null){
            instance = new OI();
        }
        return instance;
    }

    private static final double DEADBAND_RADIUS = 0.20;
    private static double deadband(double jsValue) {
        if (Math.abs(jsValue) < DEADBAND_RADIUS) {
            return 0;
        }
        // re-linearize outside of the deadband
       // jsValue -= DEADBAND_RADIUS * DaveUtils.sign(jsValue);
        jsValue *= 1.0 / (1.0 - DEADBAND_RADIUS);
        // TODO: should this value be squared for a shallower curve?
        return jsValue;
    }

    public double getXLeft() {
        return deadband(mPad.getRawAxis(0));

    }
    public double getYLeft() {
        return deadband(mPad.getRawAxis(1));
    }
    /**
     * More positive is more left
     * @return
     */
    public double getTriggerDifference() {
        return deadband(mPad.getRawAxis(3));
    }

    public double getXRight() {
        return deadband(mPad.getRawAxis(4));
    }
    public double getYRight() {
        return deadband(mPad.getRawAxis(5));
    }

    public boolean getAButton() {
        return mPad.getRawButton(1);
    }
    public boolean getBButton() {
        return mPad.getRawButton(2);
    }
    public boolean getXButton() {
        return mPad.getRawButton(3);
    }
    public boolean getYButton() {
        return mPad.getRawButton(4);
    }
    public boolean getLeftBumper() {
        return mPad.getRawButton(5);
    }

    public boolean getRightBumper() {
        return mPad.getRawButton(6);
    }

    public boolean getBackButton() {
        return mPad.getRawButton(7);
    }
    public boolean getStartButton() {
        return mPad.getRawButton(8);
    }
    public boolean getLeftStickButton() {
        return mPad.getRawButton(9);
    }
    public boolean getRightStickButton() {
        return mPad.getRawButton(10);
    }

    public double getLeftTrigger(){
        return mPad.getRawAxis(2);
    }

    public double getRightTrigger(){
        return mPad.getRawAxis(3);
    }

    public boolean getDPadLeft() {
        return mPad.getPOV() == 270 && !bButtonLastState[12];

    }
    public boolean getDPadRight() {
        return mPad.getPOV() == 90 && !bButtonLastState[11];
    }

    public boolean getDPadUp(){
        return mPad.getPOV() == 0;
    }

    public boolean getDPadDown() {
        return mPad.getPOV() == 180;
    }

    //get button down commnads
    public boolean getAButtonDown() {
        return (!bButtonLastState[1] && mPad.getRawButton(1));

    }
    public boolean getBButtonDown() {
        return (!bButtonLastState[2] && mPad.getRawButton(2));

    }
    public boolean getXButtonDown() {
        return  (!bButtonLastState[3] && mPad.getRawButton(3));
    }
    public boolean getYButtonDown() {
        return (!bButtonLastState[4] && mPad.getRawButton(4));
    }
    public boolean getLeftBumperDown() {
        return(!bButtonLastState[5] && mPad.getRawButton(5));
    }

    public boolean getRightBumperDown() {
        return (!bButtonLastState[6] && mPad.getRawButton(6));
    }

    public boolean getBackButtonDown() {
        return(!bButtonLastState[7] && mPad.getRawButton(7));
    }
    public boolean getStartButtonDown() {
        return (!bButtonLastState[8] && mPad.getRawButton(8));
    }
    public boolean getLeftStickButtonDown() {
        return (!bButtonLastState[9] && mPad.getRawButton(9));
    }
    public boolean getRightStickButtonDown() {
        return (!bButtonLastState[10] && mPad.getRawButton(10));
    }

    public void UpdateButtons(){
        for (int i = 1; i < bButtonLastState.length; i++){
            if (i <= 10)bButtonLastState[i] = mPad.getRawButton(i);
            else if(i == 11)bButtonLastState[i] =  mPad.getPOV() == 90;
            else if (i == 12)bButtonLastState[i] = mPad.getPOV() == 270;
        }


    }

    public void rumble(double power){
        mPad.setRumble(Joystick.RumbleType.kLeftRumble, power);
        mPad.setRumble(Joystick.RumbleType.kRightRumble, power);
    }
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
package frc.robot;

import frc.robot.Utils.controls.XBoxController;
import frc.robot.commands.*;

public class IO { 
    private static IO instance; 
    private XBoxController driverController;
    private XBoxController manipulatorController;

    private boolean ARM_IN = false;
    private boolean ARM_OUT = true;
    private double PICKUP_POS = 11.5;
    private double STORAGE_POS = 3.0;
    private double PLACE_HIGH_POS = 14.3;
    private double PLACE_MIDDLE_POS = 13.5;
    private double PLACE_LOW_POS = 7.0;
    private double START_POS = 1;

    private IO() {
        driverController = new XBoxController(0);
        manipulatorController = new XBoxController(1);
    }
public static IO getInstance() {
    if(instance == null) instance = new IO();

    return instance;
}
public void teleopInt(){

    manipulatorController.whenButtonPressed("A", new MoveClawCommand(true));
    manipulatorController.whenButtonReleased("A", new MoveClawCommand(false));

    manipulatorController.whenButtonPressed("Y", new MoveArmToPositionCommand(ARM_IN, PICKUP_POS));
    manipulatorController.whenButtonPressed("B", new MoveArmToPositionCommand(ARM_IN, STORAGE_POS));
    manipulatorController.whenPOVButtonPressed("N", new MoveArmToPositionCommand(ARM_OUT, PLACE_HIGH_POS));
    manipulatorController.whenPOVButtonPressed("E", new MoveArmToPositionCommand(ARM_IN, START_POS));
    manipulatorController.whenPOVButtonPressed("S", new MoveArmToPositionCommand(ARM_OUT, PLACE_LOW_POS));
    manipulatorController.whenPOVButtonPressed("W", new MoveArmToPositionCommand(ARM_OUT, PLACE_MIDDLE_POS));

}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}
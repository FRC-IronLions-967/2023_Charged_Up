package frc.robot;

import frc.robot.Utils.controls.XBoxController;
import frc.robot.commands.*;

public class IO { 
    private static IO instance; 
    private XBoxController driverController;
    private XBoxController manipulatorController;

    private boolean ARM_IN = false;
    private boolean ARM_OUT = true;
    private double PICKUP_POS = 10.5;
    private double STORAGE_POS = 3.0;
    private double PLACE_HIGH_POS = 14.25;
    private double PLACE_MIDDLE_POS = 12.9;
    private double PLACE_LOW_POS = 6.0;
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

    driverController.whenButtonPressed("A", new MoveClawCommand(true));
    driverController.whenButtonReleased("A", new MoveClawCommand(false));

    driverController.whenButtonPressed("X", new MoveClawCommand(true));

    driverController.whenButtonPressed("Y", new MoveArmToPositionCommand(ARM_IN, PICKUP_POS));
    driverController.whenButtonPressed("B", new MoveArmToPositionCommand(ARM_IN, STORAGE_POS));
    driverController.whenPOVButtonPressed("N", new MoveArmToPositionCommand(ARM_OUT, PLACE_HIGH_POS));
    driverController.whenPOVButtonPressed("E", new MoveArmToPositionCommand(ARM_IN, START_POS));
    driverController.whenPOVButtonPressed("S", new MoveArmToPositionCommand(ARM_IN, PLACE_LOW_POS));
    driverController.whenPOVButtonPressed("W", new MoveArmToPositionCommand(ARM_OUT, PLACE_MIDDLE_POS));
    driverController.whenButtonPressed("RBUMP", new LeadScrewAdjustCommand(0.5));
    driverController.whenButtonPressed("LBUMP", new LeadScrewAdjustCommand(-0.5));

    driverController.whenButtonPressed("SELECT", new LeadScrewInitializeCommand());



    //driverController.whenButtonPressed("B", new DriveBrakeCommand(true));
    //driverController.whenButtonPressed("A", new DriveBrakeCommand(false));

}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}
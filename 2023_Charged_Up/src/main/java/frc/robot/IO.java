package frc.robot;


import frc.robot.Utils.controls.XBoxController;
import frc.robot.commands.*;

public class IO { 
    private static IO instance; 
    private XBoxController driverController;
    private XBoxController manipulatorController;

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

    manipulatorController.whenButtonPressed("Y", new MoveArmToPickupCommand());
    manipulatorController.whenButtonPressed("B", new MoveArmToStorageCommand());
    manipulatorController.whenPOVButtonPressed("N", new MoveArmToPlaceHighCommand());
    manipulatorController.whenPOVButtonPressed("E", new MoveArmToPlaceMiddleCommand());
    manipulatorController.whenPOVButtonPressed("S", new MoveArmToPlaceLowCommand());

    if (manipulatorController.getLeftTrigger() > 0.1) {
        new LeadScrewAdjustCommand(manipulatorController.getLeftTrigger());
    } else if (manipulatorController.getRightTrigger() > 0.1) {
        new LeadScrewAdjustCommand(-manipulatorController.getRightTrigger()); 
    } 

}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}
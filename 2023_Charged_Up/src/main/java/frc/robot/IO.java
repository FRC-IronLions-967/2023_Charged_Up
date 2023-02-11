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
    manipulatorController.whenButtonPressed("Y", new MoveArmCommand(true));
    manipulatorController.whenButtonPressed("B", new MoveArmCommand(false));
    manipulatorController.whenButtonPressed("X", new MoveClawCommand(true));
    manipulatorController.whenButtonReleased("X", new MoveClawCommand(false));
}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}
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

    manipulatorController.whenButtonPressed("A", new LeadScrewCommand(1.0));
    manipulatorController.whenButtonReleased("A", new LeadScrewCommand(0.0));
    manipulatorController.whenButtonPressed("X", new LeadScrewCommand(-1.0));
    manipulatorController.whenButtonReleased("X", new LeadScrewCommand(0.0));

}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}
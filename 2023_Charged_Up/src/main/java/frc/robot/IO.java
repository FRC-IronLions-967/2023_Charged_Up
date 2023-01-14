package frc.robot;


import frc.robot.Utils.controls.XBoxController;
import frc.robot.commands.*;

public class IO { 
    private static IO instance; 
    private XBoxController driverController;
    private XBoxController manipulatorController;

    private IO() {
        driverController = new XBoxController(0);
        manipulatorController = new XBoxController(0);
    }
public static IO getInstance() {
    if(instance == null) instance = new IO();

    return instance;
}
public void teleopInt(){

    manipulatorController.getBu()
}
public XBoxController getDriverController(){
    return driverController;
}
public XBoxController getManipulatorController(){
    return manipulatorController;

}
}
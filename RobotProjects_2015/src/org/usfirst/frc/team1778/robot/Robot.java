
package org.usfirst.frc.team1778.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/* Team 1778 robot has three major subclasses - Drivetrain, alignment and elevator */

public class Robot extends IterativeRobot {

	
	// Chill Out 1778 major subassemblies
	
	DriveAssembly drivetrain;
	//AlignmentAssembly aligner;
	ElevatorAssembly elevator;
	//PneumaticsTester pneumTest;
	
	PowerDistributionPanel pdp;
	
	
    public void robotInit() {
    	
    	pdp = new PowerDistributionPanel();
    	pdp.clearStickyFaults();
    	
    	drivetrain = new DriveAssembly();
    	//aligner = new AlignmentAssembly();
    	elevator = new ElevatorAssembly();
    	//pneumTest = new PneumaticsTester();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
		//System.out.println("Chill out autonomous call!");
    	
    	drivetrain.autoPeriodic();
    	//aligner.autoPeriodic();
    	elevator.autoPeriodic();
    	//pneumTest.autoPeriodic();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

		//System.out.println("Chill out teleopPeriodic call!");

    	drivetrain.teleopPeriodic();
        //aligner.teleopPeriodic();
        elevator.teleopPeriodic();
        //pneumTest.teleopPeriodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
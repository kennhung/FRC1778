package StateMachine;

import Systems.PWMDriveAssembly;

public class DriveForwardState extends AutoState {
	
	public DriveForwardState()
	{
		this.name = "<Drive Forward State>";
		
		PWMDriveAssembly.initialize();
	}
	
	public DriveForwardState(String name)
	{
		this.name =  name;
		
		PWMDriveAssembly.initialize();
	}
	
	// state entry
	public void enter() {
		// do some drivey initialization
		
		PWMDriveAssembly.autoInit();
		
		super.enter();
	}
	
	// called periodically
	public AutoState process()  {
		
		// do some drivey stuff
		
		PWMDriveAssembly.autoPeriodicStraight();
		
		return super.process();
	}
	
	// state cleanup and exit
	public void exit() {
		// do some drivey cleanup
		PWMDriveAssembly.autoStop();
		
		// cleanup base class
		super.exit();
	}
}
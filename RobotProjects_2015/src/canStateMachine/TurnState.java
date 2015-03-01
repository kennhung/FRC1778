package canStateMachine;

import Systems.PWMDriveAssembly;

public class TurnState extends AutoState {
	
	private double angleToTurn = 0.0;
	private double speedToTurn = 0.3;
	
	public TurnState(double angleToTurn, double speed)
	{
		this.name = "<Turn State>";
		this.angleToTurn = angleToTurn;
		this.speedToTurn = speed;
		
		PWMDriveAssembly.initialize();
	}
	
	public TurnState(String name, double angleToTurn, double speed)
	{
		this.name =  name;
		this.angleToTurn = angleToTurn;
		this.speedToTurn = speed;
		
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
		
		PWMDriveAssembly.turnToDirection(angleToTurn, speedToTurn);
		
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

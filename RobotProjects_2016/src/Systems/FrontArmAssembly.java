package Systems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Utility;

public class FrontArmAssembly {
	private static boolean initialized = false;
	
    // minimum increment (for joystick dead zone)
	private static final double ARM_DEADZONE = 0.2;
	private static final double ROLLER_DEADZONE = 0.1;
	
    private static final long CYCLE_USEC = 250000;
    
    // limits
    private static final double FORWARD_SOFT_ENCODER_LIMIT = (4096.0*4.0);
    private static final double REVERSE_SOFT_ENCODER_LIMIT = 0.0;
    private static final double ARM_MOTION_MULTIPLIER = 1.0;
    
	// controller gamepad ID - assumes no other controllers connected
	private static final int GAMEPAD_ID = 0;
	
    // control objects
    private static Joystick gamepad;
           
    // motor ids
    private static final int FRONT_ARM_MOTOR_ID = 5;
    private static final int FRONT_ARM_ROLLER_ID = 6;
    
    private static CANTalon frontArmMotor, frontArmRollerMotor;
    
    private static long initTime;

	// static initializer
	public static void initialize()
	{
		if (!initialized) {

	        gamepad = new Joystick(GAMEPAD_ID);
	        	                	        
	        initialized = true;
	        
	        // create and initialize arm motor
	        frontArmMotor = new CANTalon(FRONT_ARM_MOTOR_ID);
	        if (frontArmMotor != null) {
	        	
		        System.out.println("Initializing front arm motor (position control)...");
	        	
	        	// set up motor for position control mode
		        frontArmMotor.disableControl();
		        frontArmMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		        frontArmMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	        	
	        	// P and D should be at a 1:4 ratio;  I should be ZERO
	        	// higher numbers equate to higher gain/current draw
	        	//frontArmMotor.setPID(8.0, 0, 32.0);  // DO NOT USE - FUN STUFF HAPPENS
	        	//frontArmMotor.setPID(3.0, 0, 12.0);
		        frontArmMotor.setPID(2.0, 0, 18.0);     // works pretty well
	        	//frontArmMotor.setPID(0.5, 0, 2.0);
	        	//frontArmMotor.setPID(0.1, 0, 0.5);    // good but weak
	        		        	
		        frontArmMotor.enableBrakeMode(true);
		        
		        // set soft limits on arm motion
	        	frontArmMotor.setForwardSoftLimit(FORWARD_SOFT_ENCODER_LIMIT);    	
	        	frontArmMotor.enableForwardSoftLimit(true);
	        	frontArmMotor.setReverseSoftLimit(REVERSE_SOFT_ENCODER_LIMIT);
	        	frontArmMotor.enableReverseSoftLimit(true);
	        	
		        frontArmMotor.set(frontArmMotor.getPosition());
		        frontArmMotor.enableControl();
	        	
	        	// initializes encoder to zero
		        frontArmMotor.setPosition(0);        	
	        }
	        else
	        	System.out.println("ERROR: Front Arm motor not initialized!");
		  
	        // create and initialize roller motor
	        frontArmRollerMotor = new CANTalon(FRONT_ARM_ROLLER_ID);
	        if (frontArmRollerMotor != null) {
	        	
		        System.out.println("Initializing front arm roller motor (speed control, no encoder)...");
	        	
	        	// set up roller motor for percent Vbus control mode
		        frontArmRollerMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        		        	
		        // no brake mode, no limits on rollers
		        frontArmRollerMotor.enableBrakeMode(false);
		        frontArmRollerMotor.enableForwardSoftLimit(false);
		        frontArmRollerMotor.enableReverseSoftLimit(false);
	        	
	        	// initializes speed of rollers to zero
		        frontArmRollerMotor.set(0);
	        	
	        }
	        else
	        	System.out.println("ERROR: Front Arm roller motor not initialized!");
		}
	}
	
	public static void autoInit() {
        initTime = Utility.getFPGATime();
	}
	
	public static void autoPeriodic(boolean liftCommand)
	{
		long currentTime = Utility.getFPGATime();

		// if not long enough, just return
		if ((currentTime - initTime) < CYCLE_USEC)
			return;

	}
	
	public static void autoStop()
	{
		// nothing to clean up here
	}
		
	public static void teleopInit() {
        initTime = Utility.getFPGATime();
		
	}
	
	public static void teleopPeriodic()
	{		
		long currentTime = Utility.getFPGATime();
		
		// if not long enough, just return
		if ((currentTime - initTime) < CYCLE_USEC)
			return;
		
		// check for arm motion
		double incrementalArmPos = gamepad.getRawAxis(2);
		if(Math.abs(incrementalArmPos) <= ARM_DEADZONE) {
			incrementalArmPos = 0.0;
		}
		
		double newArmTarget = frontArmMotor.getPosition() + (incrementalArmPos * ARM_MOTION_MULTIPLIER);
		if ((newArmTarget >= REVERSE_SOFT_ENCODER_LIMIT) && (newArmTarget <= FORWARD_SOFT_ENCODER_LIMIT))
			frontArmMotor.set(newArmTarget);
		
		// check for roller motion
		double rollerSpeed = gamepad.getRawAxis(5);
		if (Math.abs(rollerSpeed) < ROLLER_DEADZONE) {
			rollerSpeed = 0.0f;
		}
					
		frontArmRollerMotor.set(rollerSpeed);

		// reset input timer;
		initTime = Utility.getFPGATime();
	}

}

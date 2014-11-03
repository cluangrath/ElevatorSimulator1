import java.util.*;


public class Person implements Runnable {
	
	public String name;
	public LinkedList<Integer> desiredFloors;
	public int startingFloor;
	public long floorIdleTime;
	public boolean isSleeping=false;
	public boolean atFinalDest = false;
	
	public Person(String name, LinkedList<Integer> desiredFloors, int startFloor, int floorIdleTime)
	{
		this.name = name;
		this.desiredFloors = desiredFloors;
		this.startingFloor = startFloor;
		this.floorIdleTime = new Long(floorIdleTime * 1000); // create the floorIdleTime as a long int in milliseconds
	}

	public void run() {
//		System.out.println("in the person run method!");
//		
//		System.out.println(this.name + " is starting on floor " + this.startingFloor);
//		
//		try {
//			//Thread.sleep(this.floorIdleTime); //start the person off by sleeping or "spending time on the floor"
//			//System.out.println("Person thread slept for: " + this.floorIdleTime + " milliseconds");
//			
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

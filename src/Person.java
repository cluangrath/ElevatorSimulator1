import java.util.*;


public class Person implements Runnable {
	
	public String name;
	public ArrayList<Integer> desiredFloors;
	public int startingFloor;
	public int floorIdleTime;
	
	public Person(String name, ArrayList<Integer> desiredFloors, int startFloor, int floorIdleTime)
	{
		this.name = name;
		this.desiredFloors = desiredFloors;
		this.startingFloor = startFloor;
		this.floorIdleTime = floorIdleTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("in the person run method!");
		
		System.out.println(this.name + " is starting on floor " + this.startingFloor);
	}
}

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator implements Runnable {

	public int currentFloor;
	public int destinationFloor;
	public int direction; //1 for up, -1 for down
	public Boolean isRunning;
	public ArrayList<Person> riders;

	private Lock elevatorLock;
	private Condition elevatorCondition;

	public Elevator() {
		this.riders = new ArrayList<Person>();
		this.currentFloor = 15; //start all elevators created at floor 0
		this.elevatorLock = new ReentrantLock();
		this.elevatorCondition = this.elevatorLock.newCondition();
		this.direction = -1;
	}

	@Override
	public void run() {
		System.out.println("Locking the resource");
		this.elevatorLock.lock();
		try
		{
			System.out.println("In the run method from elevator class");
			System.out.println("next destination floor for elevator 1: " + findNextDestinationFloor());
		}
		finally
		{
			System.out.println("Releasing the lock");
			this.elevatorLock.unlock();
		}
	}
	
	/*
	 * Used to find the next destination floor for this elevator based on the value of the direction member-level variable.
	 */
	private int findNextDestinationFloor()
	{				
		int closestFloor = -1;
		LinkedList<Integer> upperFloors = new LinkedList<Integer>();
		LinkedList<Integer> lowerFloors = new LinkedList<Integer>();
		
		//populate my upper and lower floors
		for(Person p : this.riders)
		{
			for(int floor : p.desiredFloors)
			{
				if (floor > this.currentFloor)
				{
					upperFloors.add(floor);
				}
				else
				{
					lowerFloors.add(floor);
				}
			}
		}
		
		if (this.direction == 1)
		{
			//the elevator is going up
			closestFloor = upperFloors.peekFirst();
			for(int i : upperFloors)
			{
				if (i < closestFloor)
				{
					closestFloor = i;
				}
			}
		}
		else
		{
			//the elevator is going down
			closestFloor = lowerFloors.peekFirst();
			for(int i : lowerFloors)
			{
				if (i > closestFloor)
				{
					closestFloor = i;
				}
			}
		}
		
		return closestFloor;
	}
}
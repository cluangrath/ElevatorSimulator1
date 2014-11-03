import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator implements Runnable {

	//RYAN: was this what you had in mind?
	public static LinkedList<LinkedList<Person>> floorQueues = new LinkedList<LinkedList<Person>>(); //the index of the linkedlist is the floor in which it will represent
	
	public String elevatorName;
	public static final int CAPACITY = 3;
	public int currentFloor;
	public int destinationFloor;
	public int direction; //1 for up, -1 for down
	public Boolean isRunning;
	public ArrayList<Person> riders;

	private Lock elevatorLock;
	private Condition elevatorCondition;

	public Elevator(String name) {
		this.elevatorName = name;
		this.riders = new ArrayList<Person>();
		this.currentFloor = 0; //start all elevators created at floor 0
		this.elevatorLock = new ReentrantLock();
		this.elevatorCondition = this.elevatorLock.newCondition();
		this.direction = 1;
	}
	/*
	public void run() {
		int nextFloor = findNextDestinationFloor();
		while (nextFloor != -1) {
			//RYAN: continue this elevator until the riders are completely gone... 
				//probably not a good idea because there might be a period when the elevator has no riders at a given moment, meaning the elevator will then shut down and not start up again
			System.out.println(this.elevatorName + " serviced floor " + nextFloor);
			this.currentFloor = nextFloor;
			nextFloor = findNextDestinationFloor();
			
			try {
				Thread.sleep(1000); //the elevator sleeps for 1 second at every floor to allow people to enter/exit...
				//RYAN: how does the elevator know who it needs to pick up? - look at the queue for this floor
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	public void run(){
		int nextFloor = findNextDestinationFloor(true);
		while (nextFloor != -1)
		{
			//System.out.println(this.riders.size());
			
			if(nextFloor>this.currentFloor)
			{

				for(int i = this.currentFloor;i<=nextFloor;i++) //Need to service every floor. Oh shit this only goes up.
				{
					System.out.println("Elevator "+ this.elevatorName+" arrived at floor "+i);
					for(int j = 0;j<this.riders.size();j++)//For every rider...
					{
						
						Person p = this.riders.get(j);
						if(p.desiredFloors.get(0)==i)//If this is their floor, then pop off their desired floor, and push them off.
						{
							p.desiredFloors.pop();//Got to their floor.
							if(p.desiredFloors.isEmpty()){p.atFinalDest = true;} //They are where they want to be.
							Elevator.floorQueues.get(j).add(p); //Should be passed by reference
							this.riders.remove(j); //No longer in elevator
						}
					}
					
					for(int j=0;j<Elevator.floorQueues.get(i).size();j++)
					{
						if(this.riders.size()==Elevator.CAPACITY)
						{
							break;
						}
						
						Person p = Elevator.floorQueues.get(i).get(j);
						
						if(!p.isSleeping) //If our person isn't doing shit, put him on the elevator. Currently will always let him on.
						{
							System.out.println(p.name);
							this.riders.add(p);
							Elevator.floorQueues.get(i).pop();
							j--;//Need to correct for error of removing an element. Somewhat trippy.
						}
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Elevator "+ this.elevatorName+" serviced floor "+i);
					currentFloor = nextFloor;
					nextFloor = findNextDestinationFloor(true);
				}
			}else{ //Copy paste for minus
				for(int i = this.currentFloor;i>=nextFloor;i--) //Need to service every floor. Oh shit this only goes up.
				{
					System.out.println("Elevator "+ this.elevatorName+" arrived at floor "+i);
					for(int j = 0;j<this.riders.size();j++)//For every rider...
					{
						
						Person p = this.riders.get(j);
						if(p.desiredFloors.get(0)==i)//If this is their floor, then pop off their desired floor, and push them off.
						{
							p.desiredFloors.pop();//Got to their floor.
							if(p.desiredFloors.isEmpty()){p.atFinalDest = true;} //They are where they want to be.
							Elevator.floorQueues.get(j).add(p); //Should be passed by reference
							this.riders.remove(j); //No longer in elevator
						}
					}
					
					for(int j=0;j<Elevator.floorQueues.get(i).size();j++)
					{
						if(this.riders.size()==Elevator.CAPACITY)
						{
							break;
						}
						
						Person p = Elevator.floorQueues.get(i).get(j);
						
						if(!p.isSleeping) //If our person isn't doing shit, put him on the elevator. Currently will always let him on.
						{
							System.out.println(p.name);
							this.riders.add(p);
							Elevator.floorQueues.get(i).pop();
							j--;//Need to correct for error of removing an element. Somewhat trippy.
						}
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Elevator "+ this.elevatorName+" serviced floor "+i);
					currentFloor = nextFloor;
					nextFloor = findNextDestinationFloor(true);
				}
			}
		}
	}
	
	private int findNextDestinationFloor(boolean useThis)
	{
		if(!riders.isEmpty()) //if there is someone already on the elevator, go wherever they want
		{
			//System.out.println(riders.get(0).desiredFloors.get(0));
			return riders.get(0).desiredFloors.get(0);
		}
		
		
		for(int i =0;i<Elevator.floorQueues.size();i++) //Check all floors. If one has people waiting on it, go to that one.
		{
			if(Elevator.floorQueues.get(i).isEmpty())
			{
				return i;
			}
		}
		
		return -1; //If there is no one on the elevator and no one on any floor, signal -1
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
					//System.out.println("Added floor " + floor);
				}
				else
				{
					lowerFloors.add(floor);
					//System.out.println("Added floor " + floor);
				}
			}
		}
		
		if (this.direction == 1)
		{
			//the elevator is going up
			if (upperFloors.peekFirst() != null) {
				closestFloor = upperFloors.peekFirst();
				for(int i : upperFloors)
				{
					if (i < closestFloor)
					{
						closestFloor = i;
					}
				}	
			}
		}
		else
		{
			//the elevator is going down
			if (lowerFloors.peekFirst() != null) {
				closestFloor = lowerFloors.peekFirst();
				for(int i : lowerFloors)
				{
					if (i > closestFloor)
					{
						closestFloor = i;
					}
				}
			}
		}
				
		//RYAN: this loop will cycle through all of the riders in the elevator and remove the 'next' floor from all of the people that are going to that floor.
			//in second thoughs - this isn't going to work because it's going to remove the floor period even if person A is going there now, and person B is going there as their last floor
		for(Person p : this.riders) {
			for(int i = 0; i < p.desiredFloors.size(); i++) {
				int nextLocation = p.desiredFloors.indexOf(closestFloor);
				if (nextLocation > -1) {
					//System.out.println("Removed floor " + p.desiredFloors.remove(nextLocation));
					p.desiredFloors.remove(nextLocation);
				}
			}
		}
		
		return closestFloor;
	}
	
}
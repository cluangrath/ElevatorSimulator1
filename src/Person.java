import java.util.*;


public class Person {
	
	public String name;
	public Queue<Integer> desiredFloors;
	public int startingFloor;
	public int floorIdleTime;
	
	public Person(String name, Queue<Integer> desiredFloors, int startFloor, int floorIdleTime)
	{
		this.name = name;
		this.desiredFloors = desiredFloors;
		this.startingFloor = startFloor;
		this.floorIdleTime = floorIdleTime;
	}
	
	public void sayHello()
	{
		System.out.println("My person says hello!");
	}
}

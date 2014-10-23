import java.util.ArrayList;


public class Elevator implements Runnable{
	
	public int currentFloor;
	public ArrayList<Person> riders;
	
	public Elevator()
	{
		this.riders = new ArrayList<Person>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("In the run method from elevator class");
	}
}

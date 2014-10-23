import java.util.*;
import java.io.*;

/**
 * 
 */

/**
 * @author Luangrath
 *
 */
public class Driver {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		ArrayList<Person> people = new ArrayList<Person>();
		
		System.out.println("---Program Start---");
		
		String fileName = "../src/test.txt";
		
		Scanner reader = new Scanner(System.in);
		System.out.println("How many elevators would you like for this simulator?"); 
		int numElevators = reader.nextInt();
		
		System.out.println("How many floors would you like for this simulator?");
		int numFloors = reader.nextInt();
		
		//There are going to be numElevators number of threads...
		System.out.println("The user wants " + numElevators + " elevators for this simulator run.");
		System.out.println("The user wants " + numFloors + " floors for this simulator run.");
		
		File file = new File("src/test.txt");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		String fileLine = null;
		int numPeople = 0;
		while ((fileLine = bufferedReader.readLine()) != null) {
			
			numPeople++;
			//Create new people objects here. fileLine contains information about the person
			String name = fileLine.substring(0, fileLine.indexOf("[") - 1); //get everything between the beginning and the space before the array bracket
			
			String desiredFloors = fileLine.substring(fileLine.indexOf("[") + 1, fileLine.indexOf("]"));
			String[] desiredFloorsPartial = desiredFloors.split(";");
			
			Queue<Integer> desiredFloorsQueue = new LinkedList<Integer>(); //prep the queue to send to the person object
			
			for(String partial : desiredFloorsPartial)
			{
				partial = removeWhiteSpace(partial); //get rid of any whitespace
				desiredFloorsQueue.add(Integer.parseInt(partial)); //parse string as int and add to the queue
			}
			
			String idleAndFloorStart = removeWhiteSpace(fileLine.substring(fileLine.indexOf("]") + 1));
			
			int idleFloorStay = Integer.parseInt(idleAndFloorStart.substring(0, 1));
			int startFloor = Integer.parseInt(idleAndFloorStart.substring(1));
			
			Person newPerson = new Person(name, desiredFloorsQueue, idleFloorStay, startFloor);
		}
		
		System.out.println("There are " + numPeople + " people for this simulator run.");
		
		for(int i = 0; i < numElevators; i++)
		{
			Elevator elevator = new Elevator();
			Thread thread = new Thread(elevator);
			thread.start();
		}
	}//end main
	
	private static String removeWhiteSpace(String stringToCleanse)
	{
		return stringToCleanse.replaceAll("\\s+", "");
	}

}

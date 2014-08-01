package managers;

import java.util.ArrayList;
import java.util.Random;

import tasks.TransportationTask;
import beans.Passenger;
import beans.Storey;

public class BuildingManager {
	private final static ArrayList<Storey> stories = new ArrayList<Storey>();
	private ElevationManager elevationManager;

	public BuildingManager(int storiesNumber, int passengerNumber, int elevatorCapacity){
		createStories(storiesNumber);
		this.elevationManager = new ElevationManager(elevatorCapacity, passengerNumber);
		this.createPassengers(passengerNumber);
	}
	
	public static void createStories(int storiesNumber){
		System.out.println("CREATION OF BUILDING WITH "+storiesNumber+" STORIES" );
		while(storiesNumber > 0){
			stories.add(new Storey());
			storiesNumber--;
		}
	}
	
	public void createPassengers(int passengerNumber){
		Passenger passenger = null;
		Random rand = new Random();
		int dispatchStoreyNum = 0;
		int destinationStoreyNum = 0;
		for(int i=0;i<passengerNumber;i++){
			dispatchStoreyNum = rand.nextInt(stories.size())+1;
			do {
				destinationStoreyNum = rand.nextInt(stories.size())+1;
			} while(destinationStoreyNum == dispatchStoreyNum);
			passenger = new Passenger(dispatchStoreyNum, destinationStoreyNum);
			this.elevationManager.addPassengerTask(
					new TransportationTask(
							passenger, 
							elevationManager.getController())
					);
			stories.get(passenger.getDispatchStorey()-1).putToStorey(passenger);
		}
		
	}
	public void startElevation(){
		this.elevationManager.startAll();
	}
	public synchronized static Storey get(int index){
		return stories.get(index-1);
	}
	
	public synchronized static int getLastStorey(){
		return stories.size();
	}
}

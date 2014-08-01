package runner;

import managers.BuildingManager;

public class Runner {
	public static void main(String[] args){
		int storiesNumber = 5;   //args[0];
		int passengerNumber = 5; //args[1];
		int elevatorCapacity = 5; //args[2];
		System.out.println("INITIALIZATION");
		BuildingManager manager = new BuildingManager(storiesNumber, passengerNumber, elevatorCapacity);
		manager.startElevation();
		System.out.println("Главный поток закончился.");
	}
}

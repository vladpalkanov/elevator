package managers;

import java.util.ArrayList;

import tasks.ElevationTask;
import controllers.ElevatorController;

public class ElevationManager {
	private ElevatorController controller;
	private ArrayList<Thread> passengerTasks;
	private Thread elevatorTask;
	
	
	public ElevationManager(int elevatorCapacity, int passengerNumber){
		this.controller = new ElevatorController(elevatorCapacity, passengerNumber);
		this.passengerTasks = new ArrayList<Thread>();
		this.elevatorTask = new ElevationTask(this.controller);
	}
	
	public void startAll(){
		for(Thread task : passengerTasks){
			task.start();
		}		
		elevatorTask.start();
	}
	public void addPassengerTask(Thread task){
		this.passengerTasks.add(task);
	}
	public ElevatorController getController(){
		return this.controller;
	}
}

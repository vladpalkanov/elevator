package tasks;

import controllers.ElevatorController;

public class ElevationTask extends Thread{
	private ElevatorController controller; 
	
	public ElevationTask(ElevatorController controller){
		this.controller = controller;
	}
	public void run(){
		while(!controller.isTransportationCompleted()){		
			System.out.println("ELEVATOR IS AT "+controller.getCurrentStorey()+" FLOOR NOW!");
			controller.doExiting();
			controller.doEntering();
			controller.move();
		}
		System.out.println("Лифт приехал.");
	}
}

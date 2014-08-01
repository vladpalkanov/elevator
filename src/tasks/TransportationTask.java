package tasks;

import controllers.ElevatorController;
import exceptions.CannotTakePlaceException;
import managers.BuildingManager;
import beans.Passenger;
import beans.Storey;
import beans.TransportationState;

public class TransportationTask extends Thread{
	/** The story for synchronization */

	private Passenger passenger;
	private ElevatorController controller; 
	private Storey dispatchStorey;
	private Storey arrivalStorey;
	
	public TransportationTask(Passenger passenger, ElevatorController controller){
		this.passenger = passenger;
		this.controller = controller;
		this.passenger.setState(TransportationState.IN_PROGRESS);
		this.dispatchStorey = BuildingManager.get(passenger.getDispatchStorey());
		this.arrivalStorey = BuildingManager.get(passenger.getDestinationStorey());
	}
	public void run() {
		boolean inElevator = false;
		Object arrivalLock = arrivalStorey.getArrivalLock();
		Object dispatchLock = dispatchStorey.getDispatchLock();
		try{
			while(!inElevator){
				synchronized (dispatchLock) {
					dispatchLock.wait();
				}
				try {
					controller.enterElevator(passenger);
					dispatchStorey.exitStorey(passenger);
					inElevator = true;
				} catch (CannotTakePlaceException e) {
					
				}
			}
			while(inElevator){
				synchronized (arrivalLock) {
					arrivalLock.wait();
				}
				arrivalStorey.enterStorey(passenger);
				controller.exitElevator(passenger);
				inElevator = false;
			}
		} catch (InterruptedException e){
			e.printStackTrace();
			passenger.setState(TransportationState.ABORTED);
		}
		System.out.println("Пассажир #"+passenger.getPassengerId()+" закончил поток.");
		passenger.setState(TransportationState.COMPLETED);
		
	}
}

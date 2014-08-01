package controllers;

import java.util.ArrayList;

import exceptions.CannotTakePlaceException;
import managers.BuildingManager;
import beans.Direction;
import beans.Passenger;
import beans.Storey;

public class ElevatorController {
	private ArrayList<Passenger> elevatorCar = new ArrayList<Passenger>();
	private Direction direction;
	private int elevatorCapacity;
	private int currentStorey;
	private int lastStorey;
	private int transportedPassengers;
	private int passengerNumber;
	private Object elevatorLock = new Object();
	private int needToEnter;
	private int needToExit;
	
	public ElevatorController(int elevatorCapacity, int passengerNumber){
		this.elevatorCapacity = elevatorCapacity;
		this.direction = Direction.UP;
		this.currentStorey = 1;
		this.lastStorey = BuildingManager.getLastStorey();
		this.transportedPassengers = 0;
		this.passengerNumber = passengerNumber;
		this.needToEnter = 0;
		this.needToExit = 0;
	}
	
	public void move(){
		if(currentStorey == lastStorey){
			direction = Direction.DOWN;
		} 
		if(currentStorey == 1){
			direction = Direction.UP;
		}
		if(direction == Direction.UP){
			currentStorey++;
		} else {
			currentStorey--;
		}
	}
	public void doExiting(){
		Storey arrivalStorey = BuildingManager.get(currentStorey);
		Object arrivalLock = arrivalStorey.getArrivalLock();
		needToExit = 0;
		for(Passenger passenger : elevatorCar){
			if(passenger.getDestinationStorey() == this.currentStorey){
				needToExit++;
			}
		}
		System.out.println(needToExit+" хотят выйти.");
		if(needToExit!=0){
			synchronized (arrivalLock) {
				System.out.println("Лифт будит пассажиров в кабине.");
				arrivalLock.notifyAll();
			}
			synchronized (elevatorLock) {
				try {
					System.out.println("Лифт ждет, пока выйдут.");
					elevatorLock.wait();						
				} catch (InterruptedException e) {
				e.printStackTrace();
				}
			}
		}
	}
	public void doEntering(){
		Storey dispatchStorey = BuildingManager.get(currentStorey);
		Object dispatchLock = dispatchStorey.getDispatchLock();
		needToEnter = 0; 
		for(Passenger passenger : dispatchStorey.getDispatchContainer()){
			if(passenger.getDirection() == this.direction){
				needToEnter++;
			}
		}
		System.out.println(needToEnter+" хотят войти.");
		if(needToEnter!=0){	
			synchronized (dispatchLock) {
				System.out.println("Лифт будит пассажиров на этаже.");
				dispatchLock.notifyAll();				
			}
			synchronized (elevatorLock) {
				try {
					System.out.println("Лифт ждет, пока зайдут.");
					elevatorLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void enterElevator(Passenger pass) throws CannotTakePlaceException{
		synchronized(elevatorCar){
			if(elevatorCar.size() < elevatorCapacity || pass.getDirection() == direction){
				elevatorCar.add(pass);
				System.out.println("Пассажир #"+pass.getPassengerId() + " зашел в лифт." );
				needToEnter--;
				synchronized (elevatorLock) {
					if(needToEnter==0){
						System.out.println("Лифт просыпается.");
						elevatorLock.notify();
					}
				}
			} else {
				throw new CannotTakePlaceException();
			}
		}
	}
	public void exitElevator(Passenger pass){
		synchronized (elevatorCar) {
			elevatorCar.remove(pass);
			System.out.println("Пассажир #"+pass.getPassengerId() + " вышел из лифта." );
			System.out.println("Осталось перевезти "+(passengerNumber - transportedPassengers)+" пассажиров.");
		}
		
			needToExit--;
			if(needToExit==0){
				synchronized(elevatorLock){
					System.out.println("Лифт просыпается.");
					elevatorLock.notify();
				}
			}
		
	}
	public boolean isTransportationCompleted(){
		return transportedPassengers == passengerNumber;
	}
	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return the currentStorey
	 */
	public int getCurrentStorey() {
		return currentStorey;
	}
	public Object getElevatorLock(){
		return elevatorLock;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @param currentStorey the currentStorey to set
	 */
	public void setCurrentStorey(int currentStorey) {
		this.currentStorey = currentStorey;
	}
}

package beans;

import java.util.ArrayList;

public class Storey {
	
	private ArrayList<Passenger> dispatchContainer = new ArrayList<Passenger>();
	private ArrayList<Passenger> arrivalContainer = new ArrayList<Passenger>();
	private Object dispatchLock = new Object();
	private Object arrivalLock = new Object();
	
	public Storey(){
	}
	public synchronized void putToStorey(Passenger pass){
		System.out.println(pass.toString());
		this.dispatchContainer.add(pass);
	}
	public synchronized void exitStorey(Passenger pass){
		this.dispatchContainer.remove(pass);
	}
	public synchronized void enterStorey(Passenger pass){
		this.arrivalContainer.add(pass);
	}
	/**
	 * @return the dispatchContainer
	 */
	public ArrayList<Passenger> getDispatchContainer() {
		return dispatchContainer;
	}
	/**
	 * @return the arrivalContainer
	 */
	public ArrayList<Passenger> getArrivalContainer() {
		return arrivalContainer;
	}
	
	public Object getArrivalLock(){
		return this.arrivalLock;
	}
	public Object getDispatchLock(){
		return this.dispatchLock;
	}
}

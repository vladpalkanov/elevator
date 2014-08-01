package beans;


public class Passenger {
	/** Counter for the ID */
	private static int count = 1;
	/** ID of the passenger */
	private final int passengerId = count++;
	private final int destinationStorey;
	private final int dispatchStorey;	
	private TransportationState state;
	
	public Passenger(int destinationStorey, int dispatchStorey){
		this.destinationStorey = destinationStorey;
		this.dispatchStorey = dispatchStorey;
		this.state = TransportationState.NOT_STARTED;
	}

	/**
	 * @return the state
	 */
	public TransportationState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(TransportationState state) {
		this.state = state;
	}

	/**
	 * @return the destinationStorey
	 */
	public int getDestinationStorey() {
		return destinationStorey;
	}
	/**
	 * @return the dispatchnStorey
	 */
	public int getDispatchStorey() {
		return dispatchStorey;
	}
	public int getPassengerId(){
		return passengerId;
	}
	public Direction getDirection(){
		if(this.destinationStorey >  this.dispatchStorey) {
			return Direction.UP;
		} else {
			return Direction.DOWN;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destinationStorey;
		result = prime * result + dispatchStorey;
		result = prime * result + passengerId;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (destinationStorey != other.destinationStorey)
			return false;
		if (dispatchStorey != other.dispatchStorey)
			return false;
		if (passengerId != other.passengerId)
			return false;
		if (state != other.state)
			return false;
		return true;
	}
	
	public String toString(){
		return "Passenger #"+this.passengerId+", on storey "+dispatchStorey+", want to storey "
				+ this.destinationStorey;
	}
}

package op;

import java.util.Comparator;

/**
 * A Process class for the algorithms
 * @author Tony Toscano
 *
 */
public class Process implements Comparable <Process> {
	
	private int number;
	private int arrivalTime;
	private int burstTime;
	private int waitingTime;
	private int remainingTime;
	
	/**
	 * Constructor for a Process
	 * @param num The Process number
	 * @param arrivalTime The Process' arrival time
	 * @param burstTime  The Process' run time
	 * @param reminaingTime  The Process' remaining run time left
	 */
	public Process(int num, int arrivalTime, int burstTime, int remainingTime)
	{
		super();
		number = num;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.remainingTime = remainingTime;
		waitingTime = 0;
	}

	/**
	 * Retrieve the number of a process
	 * @return number of the Process
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Mutator method for number
	 * @param number the new value of what number will be
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Retrieve the arrival time of a Process
	 * @return arrivalTime of the Process
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Mutator method for arrivalTime
	 * @param arrivalTime the new value of what arrivalTime will be
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Retrieve the run time of a Process
	 * @return burstTime of a Process
	 */
	public int getBurstTime() {
		return burstTime;
	}

	/**
	 * Mutator method for burstTime
	 * @param burstTime the new value of what burstTime will be
	 */
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	
	/**
	 * Retrieve the waiting time of a Process
	 * @return waitingTime
	 */
	public int getWaitingTime()
	{
		return waitingTime;
	}
	
	/**
	 * Mutator method for waitingTime
	 * @param waitingTime the new value of what waitingTime will be
	 */
	public void setWaitingTime(int waitingTime)
	{
		this.waitingTime = waitingTime;
	}
	
	/**
	 * A String representation of a Process
	 * @return a formatted version of a Process
	 */
	public String toString()
	{
		String str = "";
//		str += "Process " + number +"\n";
//		str += "Arrival Time at " + arrivalTime + "\n";
//		str += "Burst Time " + burstTime + "\n";
//		str +=  number + "\t\t" + arrivalTime + "\t\t" + burstTime;
		str += "P" + number;
		return str;
		
	}

	/**
	 * Compares the arrivalTime of arg0 to another arrivalTime
	 * @return the difference of this.arrivalTime and the arrival time of arg0
	 */
	@Override
	public int compareTo(Process arg0) {
		// TODO Auto-generated method stub
		int compareArrivalTime = ((Process) arg0).getArrivalTime();
		
		return this.arrivalTime - compareArrivalTime;
	}
	
	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	/**
	 * Anonymous inner class for a Comparator
	 */
	public static Comparator <Process> burstTimeComparator = new Comparator<Process>() {
		
		/**
		 * Compare two processes by run time
		 * @param p1 the first process
		 * @param p2 the second process
		 * @return -1 if p1's burst time < p2's burst time,
		 *  1 if p1's burst time > p2's burst time
		 *  otherwise return 0
		 */
		public int compare(Process p1, Process p2) {
			int processCPU1 = p1.getBurstTime();
			int processCPU2 = p2.getBurstTime();
			
			if(processCPU1 < processCPU2)
				return -1;
			else if(processCPU1 > processCPU2)
				return 1;
			else
				return 0;
		}
	};

	/**
	 * Anonymous inner class for a Comparator
	 */
	public static Comparator <Process> remainingTimeComparator = new Comparator<Process>() {
		
		/**
		 * Compare two processes by run time
		 * @param p1 the first process
		 * @param p2 the second process
		 * @return -1 if p1's burst time < p2's burst time,
		 *  1 if p1's burst time > p2's burst time
		 *  otherwise return 0
		 */
		public int compare(Process p1, Process p2) {
			int processCPU1 = p1.getRemainingTime();
			int processCPU2 = p2.getRemainingTime();
			
			if(processCPU1 < processCPU2)
				return -1;
			else if(processCPU1 > processCPU2)
				return 1;
			else
				return 0;
		}
	};
}

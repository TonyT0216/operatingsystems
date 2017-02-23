package op;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;



public class Project1 {

	Random rand = new Random();
	ArrayList <Process> processList;
	ArrayList <Process> processList2;
	double average;
	
	
	/**
	 * This constructor will run the algorithms
	 */
	public Project1()
	{
		processList = new ArrayList <Process>();						// Create a new ArrayList of processes
		processList2 = new ArrayList <Process>(); 
		
		System.out.println("How many processes do you want to create?");
		Scanner input = new Scanner(System.in);
		int numProcesses = input.nextInt();
		
		for(int number = 0; number < numProcesses; number++)			// This will create the requested number
		{																// of processes
			if(number == 0)												// Assumed that all of the first processes
			{															// will have an arrival time of 0
				int arrivalTime = 0;
				int burstTime = generateRandomNumber();
				processList.add(new Process(1,arrivalTime,burstTime, burstTime));
			} else {													// Every other process will have a randomized
				int arrivalTime = generateRandomNumber();				// arrivalTime and burstTime in between 1 -12
				int burstTime = generateRandomNumber();
				processList.add(new Process(number+1,arrivalTime,burstTime, burstTime));		
			}
		}
		printSequence(processList);										// Print all of the processes that were just entered
		Collections.sort(processList);									
	    input.close();													// close the Scanner
	    
	    processList2.add(new Process(1,0,4,4));							// These lines will manually put the example given in class 
	    processList2.add(new Process(2,1,2,2));							// in a separate ArrayList
	    processList2.add(new Process(3,2,6,6));
	    processList2.add(new Process(4,4,4,4));
	    processList2.add(new Process(5,6,3,4));
	    processList2.add(new Process(6,17,7,7));
	    processList2.add(new Process(7,18,5,5));
	    processList2.add(new Process(8,19,4,4));
	    processList2.add(new Process(9,25,10,10));
	    processList2.add(new Process(10,26,6,6));
	    
	    fcfs(processList);
//	    sjf(processList);
//	    hrrf(processList);
//	    srtf(processList);
	    
	    printSequence(processList2);
//	    Collections.sort(processList2);
	    
//	    fcfs(processList2);
//	    sjf(processList2);
//	    hrrf(processList2);
// 		srtf(processList2);
	    
	}
	
	/**
	 * The random number generator between 1 -12
	 * @return	an int that is in the range of 1 - 12 :) 
	 */
	public int generateRandomNumber()
	{
		int low = 1;
		int high = 13;
		int r = rand.nextInt(high - low) + low; 
		return r;	
	}
	
	
	/**
	 * Will print the entirety of the ArrayList into a clean, format
	 * @param processes	the ArrayList that will printed out 
	 */
	public void printSequence(ArrayList <Process> processes)
	{
		String str = "Process " + "\tArrival Time " +"\tBurst Time";
		System.out.println(str);
		for(Process p : processes)
		{
			str = p.getNumber() + "\t\t" + p.getArrivalTime() + "\t\t" + p.getBurstTime();
//			String output = p.toString();
			System.out.println(str);
		}
	}
	
	/**
	 * Given an ArrayList of Processes, will print them out in a linear matter
	 * @param one the ArrayList to be displayed
	 * @return String in a horizontal format
	 */
	public String readySequence(ArrayList <Process> one)
	{
		String str = "";
		for(Process p : one)
		{
			str += p.toString() + " ";
		}
		
		return str;
	}
	
	/**
	 * Will do the first come, first served algorithm
	 * Remember...it is assumed that the processes will be sorted by
	 * arrivalTime
	 */
	public void fcfs(ArrayList<Process> processes)
	{
		int currentTime = 0;												// the current running time of the algorithm
		ArrayList <Process> readyProcesses = new ArrayList <Process> ();    // the processes the are ready to run
	    Process nextProcess = null;
	    int totalProcesses = processes.size();
	    nextProcess = processes.get(0);										// the first process
		String str = "current time is " + currentTime + "\nready processes " + nextProcess.toString();	 
		str += "\n" + nextProcess.toString() + " is selected\n";
		System.out.print(str);
		currentTime += nextProcess.getBurstTime();							// add the first processes' CPU time to total running time
		nextProcess.setWaitingTime(0);										// assumed that the first process immediately ran
		System.out.println(nextProcess.toString() + " waiting time: " + nextProcess.getWaitingTime());
		processes.remove(nextProcess);										// remove the first process from the the process ArrayList
		scanThroughProcesses(currentTime, processes, readyProcesses);		// Scan through processes since currentTime has been updated
		while(!processes.isEmpty())											// Go through processes until empty
		{
			Iterator <Process> itr = processes.iterator();					// iterator for safe removal
			while(itr.hasNext())
			{
				if(readyProcesses.isEmpty() && nextProcess.getNumber() != 1)	   // readyProcesses empty and NOT the first process?
				{
					scanThroughProcesses(currentTime, processes, readyProcesses);  // refill the readyProcesses list
				}
				
				nextProcess = itr.next();											// go to the next Process
				
				if(nextProcess.getArrivalTime() > currentTime)						// if the next Process available has not arrived...
				{
					int newTimeCalc = nextProcess.getArrivalTime() - currentTime;   // go to it
					currentTime += newTimeCalc;
				}
				
				str = "\ncurrent time is " + currentTime + "\nready processes " + readySequence(readyProcesses);  // Format string 
				str += "\n" + nextProcess.toString()+ " is selected\n";
				System.out.print(str);							// Print out string
				calculateWaitingTime(nextProcess, currentTime); // calculate the process' waiting time
				average += nextProcess.getWaitingTime();		// add the waiting time to the avergage
				System.out.println(nextProcess.toString() + " waiting time: " + nextProcess.getWaitingTime());  // print out the waiting time of nextProcess
				currentTime += nextProcess.getBurstTime();      // add the process' burst time to current time
				itr.remove();                                   // remove process from list
				readyProcesses.remove(nextProcess);             // remove process from readyProcesses
			}
		}
		System.out.println("\nThe total time of all processes is " + currentTime);			// Print out the total of all of the processes' burst time
		printOutAverage(totalProcesses);													// average it out
		double sd = standardDeviation(currentTime, totalProcesses);							// calculate standard deviation
		System.out.println("\nThe standard deviation is " + sd);						    // Print SD out
		
	}
	
	/**
	 * A helper method to help calculate standard deviation
	 * @param currentTime  the total time of all processes that have run
	 * @param totalProcesses 
	 * @return
	 */
	private double standardDeviation(int currentTime, int totalProcesses) {
		double top = java.lang.Math.pow(currentTime - average, 2);		// 
		double bottom = totalProcesses;
		
		return java.lang.Math.sqrt(top / bottom);
		
		
	}

	/**
	 * Helper method to print out the average
	 * @param totalProcesses  the amount of processes that each ArrayList held before removal
	 */
	private void printOutAverage(int totalProcesses) {
		average = average / totalProcesses;
		System.out.println("The average waiting time is " + average);
		
	}

	/**
	 * The shortest job first algorithm
	 * 
	 * @param processes the ArrayList of processes to run
	 */
	public void sjf(ArrayList<Process> processes)
	{
		int currentTime = 0;							// the current running time of the algorithm
		average = 0.0;
		int totalProcesses = processes.size();
		ArrayList <Process> readyProcesses = new ArrayList <Process> ();  
	    Process nextProcess = null;
	    nextProcess = processes.get(0);																				// Run the first process
		String str = "current time is " + currentTime + "\nready processes " + nextProcess.toString();
		str += "\n" + nextProcess.toString() + " is selected";
		System.out.print(str);
		currentTime += nextProcess.getBurstTime();
		nextProcess.setWaitingTime(0);
		processes.remove(nextProcess);																				// Remove the first process
		scanThroughProcesses(currentTime, processes, readyProcesses);									
		Collections.sort(processes, Process.burstTimeComparator);			// Sort the processes by burstTime
		while(!processes.isEmpty())
		{
			

			nextProcess = shortestArrivalTime(readyProcesses);	// Get the shortest arrival time 
			str = "\ncurrent time is " + currentTime + "\nready processes " + readySequence(readyProcesses);
			str += "\n" + nextProcess.toString()+ " is selected\n";
			System.out.print(str);
			calculateWaitingTime(nextProcess, currentTime);
			average += nextProcess.getWaitingTime();
			currentTime += nextProcess.getBurstTime();
			System.out.println(nextProcess.toString() + " waiting time: " + nextProcess.getWaitingTime());
			Iterator <Process> itr = processes.iterator();
			while(itr.hasNext())
			{
				Process search = itr.next();
				if(nextProcess.equals(search))
				{
					itr.remove();
				}
			}
			
			readyProcesses.clear();
			scanThroughProcesses(currentTime, processes, readyProcesses);
		}
		
		
		System.out.println("\nThe total time of all processes is " + currentTime);
		printOutAverage(totalProcesses);
		double sd = standardDeviation(currentTime, totalProcesses);
		System.out.println("\nThe standard deviation is " + sd);
	}
	
	/**
	 * The highest response ration first algorithm
	 * @param processes
	 */
	public void hrrf(ArrayList <Process> processes)
	{
		int currentTime = 0;
		average = 0.0;
		int totalProcesses = processes.size();
		ArrayList <Process> readyProcesses = new ArrayList <Process> ();
	    Process nextProcess = null;
	    nextProcess = processes.get(0);
		String str = "current time is " + currentTime + "\nready processes " + nextProcess.toString();
		str += "\n" + nextProcess.toString() + " is selected";
		System.out.println(str);
		currentTime += nextProcess.getBurstTime();
		nextProcess.setWaitingTime(0);
		processes.remove(nextProcess);
		scanThroughProcesses(currentTime, processes, readyProcesses);
		Collections.sort(processes, Process.burstTimeComparator);
		while(!processes.isEmpty())
		{
			

			nextProcess = highestResponseRatio(readyProcesses, currentTime);
			str = "\ncurrent time is " + currentTime + "\nready processes " + readySequence(readyProcesses);
			str += "\n" + nextProcess.toString()+ " is selected";
			System.out.println(str);
			calculateWaitingTime(nextProcess, currentTime);
			average += nextProcess.getWaitingTime();
			System.out.println(nextProcess.toString() + " waiting time: " + nextProcess.getWaitingTime());
			currentTime += nextProcess.getBurstTime();
			Iterator <Process> itr = processes.iterator();
			while(itr.hasNext())
			{
				Process search = itr.next();
				if(nextProcess.equals(search))
				{
					itr.remove();
				}
			}
			
			readyProcesses.clear();
			scanThroughProcesses(currentTime, processes, readyProcesses);

		}

		System.out.println("\nThe total time of all processes is " + currentTime);
		printOutAverage(totalProcesses);
		double sd = standardDeviation(currentTime, totalProcesses);
		System.out.println("\nThe standard deviation is " + sd);
	}
	
	public void srtf(ArrayList <Process> processes)
	{
		int currentTime = 0;
		average = 0.0;
		int totalProcesses = processes.size();
		ArrayList <Process> readyProcesses = new ArrayList <Process> ();
	    Process nextProcess = null;
	    nextProcess = processes.get(0);
		String str = "current time is " + currentTime + "\nready processes " + nextProcess.toString();
		str += "\n" + nextProcess.toString() + " is selected";
		System.out.println(str);
		while(!processes.isEmpty())
		{
			currentTime++;
			int programCounter = nextProcess.getRemainingTime();
			programCounter--;
			nextProcess.setRemainingTime(programCounter);
			if(programCounter == 0)
			{
				Iterator <Process> itr = processes.iterator();
				while(itr.hasNext())
				{
					Process search = itr.next();
					if(nextProcess.equals(search))
					{
						itr.remove();
						readyProcesses.remove(nextProcess);
						if(processes.isEmpty())
							break;
						System.out.println(str);
						average += nextProcess.getWaitingTime();
						System.out.println(nextProcess.toString() + " waiting time: " + nextProcess.getWaitingTime());  // print out the waiting time of nextProcess
						nextProcess = processes.get(0);
						scanThroughProcesses(currentTime, processes, readyProcesses);
						nextProcess = shortestRemainingTime(readyProcesses,currentTime,nextProcess);
						str = "\ncurrent time is " + currentTime + "\nready processes " + readySequence(readyProcesses);
						str += "\n" + nextProcess.toString() + " is selected";
						System.out.println(str);
						break;
					}
				}
			}
			else 
			{
				scanThroughProcesses(currentTime, processes, readyProcesses);
				nextProcess = shortestRemainingTime(readyProcesses,currentTime,nextProcess);
				str = "\ncurrent time is " + currentTime + "\nready processes " + readySequence(readyProcesses);
				str += "\n" + nextProcess.toString() + " is selected";
				System.out.println(str);
			}
			readyProcesses.clear();
		}
		
		printOutAverage(totalProcesses);
		double sd = standardDeviation(currentTime, totalProcesses);
		System.out.println("\nThe standard deviation is " + sd);
		
	}
	


	private Process shortestRemainingTime(ArrayList<Process> readyProcesses, int currentTime, Process nextProcess) {
		Process p = nextProcess;
		
		for(Process pro: readyProcesses)
		{
			int rem = pro.getRemainingTime();
			if(rem < p.getRemainingTime())
			{
				p = pro;
			}
			else {
				int increaseWait = p.getWaitingTime();
				increaseWait++;
				p.setWaitingTime(increaseWait);
			}
		}
		return p;
		
	}

	/**
	 * Helper method to get the process with the highest response ratio within readyProcesses
	 * @param readyProcesses ArrayList of processes ready to run
	 * @param currentTime the currentTime of the HRRF algorithm
	 * @return the Process with the highest response ratio
	 */
	private Process highestResponseRatio(ArrayList<Process> readyProcesses, int currentTime) {
		Iterator <Process> iter = readyProcesses.iterator();
		double hrrf = Double.MIN_VALUE;
		Process p = null;
		
		while(iter.hasNext())
		{
			Process p2 = iter.next();
			double waitingTime = currentTime - p2.getArrivalTime();
			double priority = (waitingTime + (double)p2.getBurstTime()) / (double)p2.getBurstTime();
			if(priority > hrrf)
			{
				hrrf = priority;
				p = p2;
			}
			
		}
		
		return p;
	}

	/**
	 * Helper method to get the shortest arrival time of within the readyProcesses
	 * @param readyProcesses ArrayList of processes that are ready to run
	 * @return the Process with the shortest arrival time
	 */
	private Process shortestArrivalTime(ArrayList <Process> readyProcesses) {
		
		Iterator <Process> iter = readyProcesses.iterator();
		int smallestBurst = Integer.MAX_VALUE;
		Process p = null;
		
		while(iter.hasNext())
		{
			Process p2 = iter.next();
			int shortest = p2.getBurstTime();
			if(shortest < smallestBurst)
			{
				smallestBurst = p2.getBurstTime();
				p = p2;
			}
			
		}
		
		return p;
	}


	

	public void calculateWaitingTime(Process p, int time)
	{
		int result = time - p.getArrivalTime();
		p.setWaitingTime(result);
	}
	
	/**
	 * Go through all of the processes, with time as a guide, and put the processes that 
	 * have already arrived into an ArrayList of processes
	 * @param time	the currentTime of the algorithm, passed into a parameter
	 * @param processes	ArrayList of ALL Processes 
	 * @param readyProcesses ArrayList of Processes that are available to run
	 */
	public void scanThroughProcesses(int time, ArrayList<Process>processes,ArrayList<Process>readyProcesses)
	{
		
		for(Process one : processes)				// Check every process
		{
			if(one.getArrivalTime() <= time)		
				readyProcesses.add(one);
		}
		
		Collections.sort(readyProcesses);
//		Collections.sort(readyProcesses, Process.burstTimeComparator);  // Used ONLY for SJF algo
//		Collections.sort(readyProcesses, Process.remainingTimeComparator);  // Used ONLY for SRTF algo
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Project1 p1 = new Project1();

	}

}

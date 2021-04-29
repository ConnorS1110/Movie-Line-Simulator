import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simulates a movie theater snack line and calculates the average time each
 * person waited in line, along with the maximum size the line can be. The
 * simulation ends after 10 people have left the line
 * 
 * @author Connor 4-9-21
 *
 */
public class MovieLineSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final int MAX_CUSTOMERS_LEAVE_LINE = 10;

		BinaryHeap<Customer> insideLine = new BinaryHeap<Customer>();
		ArrayList<Integer> timesWaited = new ArrayList<Integer>();

		/*
		 * Initialize variables to keep track of necessary statistics for simulation
		 */
		boolean simulationOver = false;
		int maxSize = 0;
		int AvgCounterTime = 0;
		int AvgBoxTime = 0;
		int AvgPatronTime = 0;
		int totalTime = 0;
		int totalTimeNoPatron = 0;
		int totalCustomers = 0;
		int totalCustomersNoPatron = 0;
		int totalPatronCustomers = 0;
		int totalPatronTime = 0;
		int timeLastLeft = 0;

		/*
		 * Asks for user input on avg. time at the counter, avg. time at the box office,
		 * and avg. rate of movie patrons
		 */
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the avg. time a customer will spend at the counter (in min.): ");
		AvgCounterTime = input.nextInt();
		System.out.print(
				"Enter the avg. time a customer will spend at the box office before joining the snack line (in min.): ");
		AvgBoxTime = input.nextInt();
		System.out.print("Enter the avg. time a movie patron customer will enter the line (in min.): ");
		AvgPatronTime = input.nextInt();
		input.close();

		/*
		 * Simulation happens in minutes. Simulation continues until 10 people have left
		 * the snack line
		 */
		for (int time = 0; simulationOver == false; time++) {

			/*
			 * If someone has left the box office line, they are added to the snack line and
			 */
			if ((time % AvgBoxTime) == 0) {
				insideLine.insert(new Customer(time, insideLine.size() + 1, false));
			}

			// Adds movie patrons to the line
			if ((time % AvgPatronTime) == 0 && time >= AvgPatronTime) {
				insideLine.insert(new Customer(time, 0, true));
			}

			/*
			 * Checks & updates the maximum size of the line based off the current line size
			 */
			if (insideLine.size() > maxSize) {
				maxSize = insideLine.size();
			}

			/*
			 * Decides whether to remove the customer at the front of the line and update
			 * relevant statistic variables
			 */
			if (insideLine.isEmpty() == false) {

				// Assuming the first customer has no wait, they are immediately removed
				if (time == 0) {
					totalCustomers++;
					totalTime += time - insideLine.findMin().getTime();
					timesWaited.add(time - insideLine.findMin().getTime());
					if (insideLine.findMin().getPatron() == true) {
						totalPatronCustomers++;
						totalPatronTime += time - insideLine.findMin().getTime();
					}
					insideLine.deleteMin();
					timeLastLeft = time;
				}

				// If there is nobody in line, but someone is still being served at the counter
				else if (insideLine.size() == 1 && time >= timeLastLeft + AvgCounterTime
						&& (time % (AvgCounterTime + timeLastLeft) == 0)) {
					totalCustomers++;
					totalTime += time - insideLine.findMin().getTime();
					timesWaited.add(time - insideLine.findMin().getTime());
					if (insideLine.findMin().getPatron() == true) {
						totalPatronCustomers++;
						totalPatronTime += time - insideLine.findMin().getTime();
					}
					insideLine.deleteMin();
					timeLastLeft = time;
				}

				// If there are people waiting in line
				else if (time % (AvgCounterTime + timeLastLeft) == 0) {
					totalCustomers++;
					totalTime += time - insideLine.findMin().getTime();
					timesWaited.add(time - insideLine.findMin().getTime());
					if (insideLine.findMin().getPatron() == true) {
						totalPatronCustomers++;
						totalPatronTime += time - insideLine.findMin().getTime();
					} else {
						totalTimeNoPatron += time - insideLine.findMin().getTime();
						totalCustomersNoPatron++;
					}
					insideLine.deleteMin();
					timeLastLeft = time;
				}
			}

			/*
			 * If the total customers served is equal to the number of simulated customers,
			 * the simulation ends
			 */
			if (totalCustomers == MAX_CUSTOMERS_LEAVE_LINE) {
				simulationOver = true;
			}
		}

		/*
		 * Outputs relevant statistics about the simulation
		 */
		System.out.println();
		System.out.println("Times all served customers waited: " + timesWaited);
		System.out.printf("Avg. time each customer waited (including movie patrons): %5.2f minutes%n",
				(float) totalTime / totalCustomers);
		if (totalCustomersNoPatron == 0) {
			System.out.println("Avg. time each customer waited (excluding movie patrons): 0 minutes");
		}
		else {
			System.out.printf("Avg. time each customer waited (excluding movie patrons): %5.2f minutes%n",
					(float) totalTimeNoPatron / totalCustomersNoPatron);
		}
		if (totalPatronCustomers == 0) {
			System.out.println("There were no movie patron customers");
		} else {
			System.out.printf("Avg. time each movie patron customer waited: %5.2f minutes%n",
					(float) totalPatronTime / totalPatronCustomers);
		}
		System.out.println("Max size of the line: " + maxSize + " customers");
	}

}

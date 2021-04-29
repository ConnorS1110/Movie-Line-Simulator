/**
 * Creates customer objects that tracks the time they entered the line, their
 * priority in line, and whether they are a patron customer
 * 
 * @author Connor 4-9-21
 *
 */

public class Customer implements Comparable<Customer> {
	public int time;
	public int priority;
	public boolean patron;

	// Constructor
	public Customer(int time, int priority, boolean patron) {
		this.time = time;
		this.priority = priority;
		this.patron = patron;
	}

	public void setTime(int value) {
		this.time = value;
	}

	public int getTime() {
		return this.time;
	}

	public void setPriority(int value) {
		this.priority = value;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPatron(boolean value) {
		this.patron = value;
	}

	public boolean getPatron() {
		return this.patron;
	}

	// Allows for comparison between customers based off their priority
	@Override
	public int compareTo(Customer x) {
		if (this.getPriority() > x.getPriority()) {
			return 1;
		} else if (this.getPriority() < x.getPriority()) {
			return -1;
		} else {
			return 0;
		}
	}

}

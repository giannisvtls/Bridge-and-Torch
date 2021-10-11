import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Person {

	/**Count keeps an ever increasing number providing us a uniqueId for each family member
	 * Side is false if the family member is on the right side (Starting Point)
	 * Side is true if the family member is on the left side (End Point)
	 */
	public static final AtomicInteger count = new AtomicInteger(0);
	private final int personid;
	private int movingTime;
	private boolean currentSide;

	private ArrayList<Person> familyMembers;

	//Default Constructor
	public Person() 
	{
		familyMembers = new ArrayList<>();
		this.personid=0;
	}

	public Person(Person e)
	{
		this.personid = e.getPersonid();
		this.movingTime = e.getMovingTime();
		this.currentSide = e.getCurrentSide();
	}
	
	public Person(int movingTime)
	{
		personid = count.incrementAndGet() + 64;
		this.movingTime=movingTime;
		this.currentSide = false;
	}



	public void add(Person e){
		familyMembers.add(e);
	}
	
	public int getMovingTime() 
	{
		return this.movingTime;
	}

	public void setCurrentSide(boolean currentSide) {
		this.currentSide = currentSide;
	}

	public int getPersonid() {
		return personid;
	}

	public boolean getCurrentSide() {
		return currentSide;
	}
	
	@Override
	public String toString() {
		return "Person{" +
				"personid=" + personid +
				", movingTime=" + movingTime +
				", currentSide= " + currentSide +
				'}';
	}
}

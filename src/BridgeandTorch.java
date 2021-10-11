import java.util.*;

public class BridgeandTorch {
	public static void main(String[] args) {
		boolean flag = true;

		while (flag) {

			/*Receiving input data from user*/
			System.out.print("Enter the number of people on the bridge: ");
			Scanner sc = new Scanner(System.in);
			int value = sc.nextInt();
			Bridge b = new Bridge(value);

			System.out.print("\nEnter available torch time: ");
			sc = new Scanner(System.in);
			int torchTime = sc.nextInt();
			//

			/*Using our starting state as input and waiting for the result to return*/
			SpaceSearcher spaceSearcher = new SpaceSearcher();
			Bridge terminalState = null;
			long start = System.currentTimeMillis();
			terminalState = spaceSearcher.AstarClosedSet(b);
			long end = System.currentTimeMillis();

			/*Using terminal state to determine if it's a valid result and print information accordingly*/
			if (terminalState == null) {
				System.out.println("Could not find solution");
			} else {

				/*Creating a temp list to retrieve all the states our algorithm went through
					starting from terminal state back to our starting state
					reversing them*/
				Bridge temp = terminalState;
				ArrayList<Bridge> path = new ArrayList<>();
				path.add(terminalState);
				while (temp.getFather() != null) {
					path.add(temp.getFather());
					temp = temp.getFather();
				}
				Collections.reverse(path);

				/*Checking if the torch light will go off before every person crossed
				* if not, print every step*/
				if (path.get(path.size() - 1).getRealScore() > torchTime) {
					System.out.println("No path was found with the given torch time");
				} else {
					System.out.println("Finished!");
					System.out.println("\nInitial State of the bridge");
					for (Bridge bridge : path) {
						bridge.currentState();

						/*Since we took for granted the person returning will always be the fastest one we also need a way to display this process
						 * so for every state we print above, we find the fastest person, return him to the right side by setting his current side as false
						 * and print it*/
						if (!bridge.isTerminal()) {
							for (int j = 0; j < bridge.getCrossing().size(); j++) {
								if (bridge.getPerson(j).getCurrentSide()) {
									bridge.getPerson(j).setCurrentSide(false);
									System.out.println("\nMoving person back to right side");
									bridge.currentState();
									bridge.getPerson(j).setCurrentSide(true);
									break;
								}
							}

						}
						if (!bridge.isTerminal()) {
							System.out.println("\nMoving people to left side");
						}
					}
					System.out.println("\nTotal Time for everyone to cross: " + path.get(path.size() - 1).getRealScore() + " minutes");
				}
			}
			System.out.println("A* search time: " + (double) (end - start) / 1000 + " sec.");

			System.out.print("\nPress 0 if you want to try again: ");
			sc = new Scanner(System.in);
			String input = sc.next();
			if(!input.equals("0")){
				flag = false;
				System.out.println("Goodbye!");
			}
			/*Rest the personID counter*/
			Person.count.set(0);
		}
	}
}



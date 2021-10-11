import java.util.ArrayList;
import java.util.Scanner;

public class Bridge {

    private ArrayList<Person> crossing = new ArrayList<>();

    Scanner s = new Scanner(System.in);
    int n=-1;
    private int sum; //contains the sum of realMovingTime + heuristic for the current state
    private int realScore; //contains the sum of realMoving time for the pair that crossed and the realScore of its father
    private Bridge father = null;

    public Bridge() {}

    /*Used at the start when asking the user to input data about the family members*/
    public Bridge(int members) {
        int currentID = 0;
        System.out.println("\nEnter moving times in ascending order");
        while(currentID != members) {
            try {
                System.out.print("Enter moving time for family member "+ ((char)(currentID+65)) +" : ");
                s = new Scanner(System.in);
                n = s.nextInt();
                if (n != 0 ) {
                    this.add(new Person(n));
                }
            }catch (Exception e) {
                System.out.println("Try again, please enter a valid number");
            }
            currentID++;
        }
    }

    public Bridge(ArrayList<Person> p, int sum) {
        crossing.addAll(p);
        this.sum = sum;
    }

    public Bridge(ArrayList<Person> p, int realScore,int sum,Bridge father) {
        crossing.addAll(p);
        this.realScore = realScore;
        this.sum = sum;
        this.father = father;
    }

//-------------------------------------------------------------------------------------------------------------//
//																											   //
//                                                 															   //
//        																									   //
//        																									   //
//-------------------------------------------------------------------------------------------------------------//

    public void add(Person e) {
        this.crossing.add(e);
    }

    public Person getPerson(int i)
    {
    	return this.crossing.get(i);
    }
    
    public int getSum()
    {
    	return this.sum;
    }

    public int getRealScore() {
        return realScore;
    }

    public Bridge getFather() {
        return father;
    }

    public void setFather(Bridge father) {
        this.father = father;
    }

    public void setRealScore(int realScore) {
        this.realScore = realScore;
    }

    public ArrayList<Person> getCrossing() {
        return crossing;
    }

    public boolean isTerminal()
   {
	   for(int i=0;i<this.crossing.size();i++) {
		   if(!this.getPerson(i).getCurrentSide())
			   return false;
	   }
	   return true;
   }

   /**Using a bridge state, returns a list with all the possible combinations
    * of 1 person returning with the torch and 2 people crossing the bridge, the moving time and the heuristic will be calculated
    * ex. when person A and C are on the left side, A will return, then AB,AD,AE,CD,CE,DE combinations will be on the return list*/
    public ArrayList<Bridge> getChild()
    {
        //Setting the current state as the father of all the states that will derive from it
        Bridge father = new Bridge();
        for(int i=0;i < crossing.size();i++) {
            father.add(new Person(crossing.get(i)));
            father.setFather(getFather());
            father.setRealScore(getRealScore());
        }

        /*Finds the fastest person that is on the left side, since our input is sorted the first one will always be the fastest
        * Saves the time the time it takes for him to return and sets him back on the right side
        */
        int returnTime=0;
        for(int i=0;i < crossing.size();i++) {
            if(crossing.get(i).getCurrentSide()) {
                returnTime = crossing.get(i).getMovingTime();
                crossing.get(i).setCurrentSide(false);
                break;
            }
        }

        /* Creates all the possible combinations and calculates the total score (realMovingTime + heuristic) for each one of them
        *  ex. For our starting state Left side is empty, Right side contains A B C D
        * First for loop will take A, then with the second for loop it will create the combinations of AB, AC,AD with their respective total score and add the to the list that will return
        * Then it takes B, and the second for loop will create the combination BC, BD
        * etc.
         */
        int realMovingTime;
        ArrayList<Bridge> toReturn = new ArrayList<>();
        for(int i=0; i<crossing.size(); i++){
            if(!crossing.get(i).getCurrentSide()) {
                crossing.get(i).setCurrentSide(true);
                for (int j = i; j < crossing.size(); j++) {
                    if (!crossing.get(j).getCurrentSide()) {
                        crossing.get(j).setCurrentSide(true);
                        realMovingTime = returnTime + crossing.get(j).getMovingTime();
                        ArrayList<Person> temp = new ArrayList<>();
                        for (Person person : crossing) {
                            temp.add(new Person(person));
                        }
                        Bridge k = new Bridge(temp, realMovingTime);
                        int total = k.getSum()+k.heuristic();
                        toReturn.add(new Bridge(temp, realMovingTime + father.getRealScore(), total, father));
                        crossing.get(j).setCurrentSide(false);

                    }
                }
                crossing.get(i).setCurrentSide(false);
            }
        }
        return toReturn;
    }

    /**Calculates the heuristic score of a child by using the following method
     * We know that from a bridge state someone will return back with the torch so we keep how much time his return will take
     * Then we calculate how much time each person on the right side will take to cross if they could all cross at once
     * The totalScore that will return, is the sum of those two*/
    public int heuristic()
    {
        //When we are at a terminal state the heuristic time is 0
        if(isTerminal()){
            return 0;
        }
        int sum=0;
        for(int i=0; i<crossing.size(); i++){
            if(crossing.get(i).getCurrentSide()){
                sum = sum + crossing.get(i).getMovingTime() + crossing.get(i).getMovingTime();
                break;
            }
        }

        for(int j=0; j< crossing.size();j++){
            if(!crossing.get(j).getCurrentSide()){
                sum = sum + crossing.get(j).getMovingTime();
            }
        }
        return sum;
    }

   /**Prints the current state of the board*/
   public void currentState()
   {

       System.out.print(">>>>>>>>>>>>>>>>>>>>\nCurrently on Left side(ending side): ");
       for(int i=0; i<crossing.size(); i++){
           if (crossing.get(i).getCurrentSide()){
               System.out.print((char)crossing.get(i).getPersonid() + " ");
           }
       }

       System.out.print("\nCurrently on Right side(starting side): ");
       for(int i=0; i<crossing.size(); i++){
           if (!crossing.get(i).getCurrentSide()){
               System.out.print((char)crossing.get(i).getPersonid() + " ");
           }
       }

       System.out.print("\n>>>>>>>>>>>>>>>>>>>>\n");
   }
    
}

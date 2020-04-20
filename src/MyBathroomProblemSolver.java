import java.util.concurrent.Semaphore;

public class MyBathroomProblemSolver implements BathroomProblemSolverInterface
{
	Semaphore toilets; // The number of available toilets
    Semaphore mutex = new Semaphore(1); // Mutex for mutual exclusion in critical section
    int totalToilets; // total number of toilets (free and occupied)
    int[] inBathroom = {0,0}; // number of men and women in bathroom (should only be one gender at a time)

    public MyBathroomProblemSolver(int n)
	{
	    System.out.println("MyBathroomProblemSolver("+n+")");
		toilets = new Semaphore(n); // n toilets free at start
		this.totalToilets = n; // n toilets total
	}


    void debug() {}

	// Try to enter the bathroom
	public void arriveAtTheBathroom(gender type)
	{
	    System.out.println("arriveAtTheBathroom("+type.toString()+")");
	    debug();

	    boolean done = false;
	    while (!done) {
	        toilets.acquireUninterruptibly(); // wait for a toilet
	        mutex.acquireUninterruptibly(); // enter CS
	        if (inBathroom[gender.opposite(type).ordinal()] == 0 // if none of other gender inside, and <N of same gender inside, then enter
            && inBathroom[type.ordinal()] < totalToilets) {
                inBathroom[type.ordinal()]++;
                done = true;
            }
	        else
	            toilets.release(); // else, wait for another opportunity & signal someone else to try
            mutex.release(); // leave CS
        }

        System.out.println("END enterTheBathroom"+type+")");
        debug();
	}

	// Leave the bathroom
	public void leaveTheBathroom(gender type) 
	{
	    System.out.println("leaveTheBathroom"+type+")");
	    debug();

	    mutex.acquireUninterruptibly(); // enter CS
	    inBathroom[type.ordinal()]--; // leave bathroom
	    mutex.release(); // leave CS
        toilets.release(); // signal a person waiting for a toilet
        System.out.println("END leaveTheBathroom"+type+")");
        debug();
	}


}

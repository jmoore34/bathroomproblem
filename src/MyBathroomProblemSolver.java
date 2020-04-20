import java.util.concurrent.Semaphore;

public class MyBathroomProblemSolver implements BathroomProblemSolverInterface
{
	Semaphore toilets;
    Semaphore mutex = new Semaphore(1);
    int n;
    int[] inBathroom = {0,0};

    public MyBathroomProblemSolver(int n)
	{
	    System.out.println("MyBathroomProblemSolver("+n+")");
		toilets = new Semaphore(n);
		this.n=n;
	}


    void debug() {}

	// Try to enter the bathroom
	public void arriveAtTheBathroom(gender type)
	{
	    System.out.println("arriveAtTheBathroom("+type.toString()+")");
	    debug();

	    boolean done = false;
	    while (!done) {
	        toilets.acquireUninterruptibly();
	        mutex.acquireUninterruptibly();
	        if (inBathroom[gender.opposite(type).ordinal()] == 0
            && inBathroom[type.ordinal()] < n) {
                inBathroom[type.ordinal()]++;
                done = true;
            }
	        else
	            toilets.release();
            mutex.release();
        }

        System.out.println("END enterTheBathroom"+type+")");
        debug();
	}

	// Leave the bathroom
	public void leaveTheBathroom(gender type) 
	{
	    System.out.println("leaveTheBathroom"+type+")");
	    debug();

	    mutex.acquireUninterruptibly();
	    inBathroom[type.ordinal()]--;
	    mutex.release();
        toilets.release();
        System.out.println("END leaveTheBathroom"+type+")");
        debug();
	}


}

import java.util.concurrent.Semaphore;

public class MyBathroomProblemSolver implements BathroomProblemSolverInterface
{
	int numToilets;//Number of toilets
    int switchTheshold; // theshold for moving to next phase
    gender turn; // the gender type that can use the bathroom currently
    int waiting[] = {0,0}; // the number of people of each gender waiting to use the bathroom
    int completed = 0; // the number of people who have left the bathroom in the current phase
    int inside = 0; // the number of people currently using the bathroom

    Semaphore safeToUse[] = {new Semaphore(0), new Semaphore(0)}; // semaphore for waiting to enter the bathroom
    Semaphore mutex = new Semaphore(1);// semaphore for achieving mutual exclusion

    public MyBathroomProblemSolver(int n)
	{
		this.numToilets = n;
		this.switchTheshold = 2*n;
	}

	// Try to enter the bathroom
	public void arriveAtTheBathroom(gender type)
	{
		mutex.acquireUninterruptibly(); // wait to enter CS
        waiting[type.ordinal()]++; //increment the # of waiters for this gender

        // check if can enter the bathroom immediately
        if (!(
                okayToEnter(type) || okayToSwitch(type) ))
        {
            mutex.release(); //leave CS
            safeToUse[type.ordinal()].acquireUninterruptibly(); // wait until it is appropiate to use the bathroom
        }
        else
        {
            if (turn != type) // gender is incompatible with the current mode
            {
                switchGender(type); //switch gender mode to mine
            }
            waiting[type.ordinal()]--; // no longer waiting
            inside++; //enter bathroom
            mutex.release(); // leave CS
        }
	}

	// Leave the bathroom
	public void leaveTheBathroom(gender type) 
	{
		mutex.acquireUninterruptibly(); //wait to enter CS
		inside--; // no longer in bathroom
		completed++; // increment # of people who have completed using the bathroom

		if (okayToEnter(type))
        {
            waiting[type.ordinal()]--; // accept one from this gender
            inside++; // they go inside
            safeToUse[type.ordinal()].release(); // wake up person of my gender
        }
		else if (okayToSwitch(gender.opposite(type)))
        {
            switchGender(gender.opposite(type));
            //calculate the number of people of the other gender to wake up
            inside = Math.min(numToilets, waiting[gender.opposite(type).ordinal()]); // accept as many of people of opposite gender waiting as possible
            for (int i=0; i<inside; ++i) // wake up that many people of opposite gender
            {
                safeToUse[gender.opposite(type).ordinal()].release();
            }
        }

		mutex.release(); // leave CS
	}

    //Helper functions


    // Test whether it is okay to allow a waiting person to use the bathroom
    // returns false if either the bathroom is full or no more people of the current gender will be allowed in the bathroom in the current phase; otherwise, returns true
    boolean okayToEnter(gender type)
    {
        return (turn == type
                && waiting[type.ordinal()] > 0
                && inside < numToilets
                && ((inside + completed < switchTheshold)
                    || (waiting[gender.opposite(type).ordinal()] == 0))
        );
    }


    // test whether it is okay to switch the gender and move to the next phase
    // returns true if all the conditions for moving to the next phase are met:
    //  (a) thereis at least one person of the opposite gender waiting to enter the bathroom
    //  (b) the bathoom is empty, and
    //  (c) either the number of people of the current gender who have used the bathroom is greater than or equal to the threshold or there is no person of the current gender waiting to enter the bathroom.
    boolean okayToSwitch(gender type)
    {
        return (turn != type
                && inside == 0
                && waiting[type.ordinal()] > 0
                && ((inside + completed >= switchTheshold)
                || (waiting[gender.opposite(type).ordinal()] == 0))
        );
    }

    //move to the next phase by changing the gender type that can now use bathroom
    void switchGender(gender type)
    {
        completed = 0; //reset the count
        turn = type; //switch the gender
    }
}

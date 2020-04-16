import java.util.concurrent.atomic.AtomicInteger; 

class Tester implements BathroomProblemSolverInterface
{
	int factor, toilets;
	BathroomProblemSolverInterface solution;
	int men;
	int women;

	AtomicInteger counter;
    //counter is managed such that 
	//counter%factor = # of men in bathroom
	//counter/factor = # of women in bathroom 

	public Tester(BathroomProblemSolverInterface solution, int men, int women, int toilets)
	{
		this.factor = 10 + men * 10;//to avoid conflicts
		this.toilets = toilets;
		this.solution = solution;
		counter = new AtomicInteger();
		this.men = men;
		this.women = women;
	}

	public void begin(int iterations)
	{
		Driver.drive(this, men, women, iterations);
		System.out.println("Test successful");
	}

	public void arriveAtTheBathroom(gender type)
	{
		solution.arriveAtTheBathroom(type);
		int counter_value = counter.getAndAdd(type.ordinal()*factor + (1-type.ordinal()));
		sanity_check(counter_value);
	}

	public void leaveTheBathroom(gender type)
	{
		int counter_value = counter.getAndAdd(-(type.ordinal()*factor + (1-type.ordinal())));
		solution.leaveTheBathroom(type);
		sanity_check(counter_value);
	}

	void sanity_check(int counter_value)
	{
		//int counter_value = counter.get();
		int men = counter_value % factor;
		int women = counter_value / factor;
		assert(men <= toilets) : "More men inside than toilets available";
	    	assert(women <= toilets) : "More women inside than toilets available";
	    	assert(men * women == 0) : "Both genders inside bathroom simultaneously";
	}
}

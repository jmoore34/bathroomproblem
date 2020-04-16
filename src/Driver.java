import java.util.Random; 
import java.util.ArrayList;

class Driver implements Runnable
{
	BathroomProblemSolverInterface solver;
	gender type;
	int iterations;
	
	public Driver(BathroomProblemSolverInterface solver, gender type, int iterations)
	{
		this.solver = solver;
		this.type = type;
		this.iterations = iterations;
	}
	
	public void run()
	{
		Random generator = new Random();
		for(int i = 0; i < iterations; i++)
		{
			solver.arriveAtTheBathroom(type);
			try
			{
				Thread.sleep(generator.nextInt(100)+1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace(); 
			}
			solver.leaveTheBathroom(type);
			try
			{
				Thread.sleep(generator.nextInt(100)+1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace(); 
			}
		}
	}

	static void drive(BathroomProblemSolverInterface solver, int men, int women, int iterations)
	{
		int i;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for(i = 0; i < men; i++)
		{
			threads.add(new Thread(new Driver(solver, gender.MAN, iterations)));
		}
		for(i = 0; i < women; i++)
		{
			threads.add(new Thread(new Driver(solver, gender.WOMAN, iterations)));
		}
		for(Thread t: threads)
		{
			t.start();
		}
		for(Thread t: threads)
		{
			try
			{
				t.join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace(); 
			}
		}
	}
}


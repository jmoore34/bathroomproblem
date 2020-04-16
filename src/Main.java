class Main
{
	public static void main(String[] args)
	{
		int n = 3;//Integer.parseInt(args[0]);//Number of toilets
		int men = 5;//Integer.parseInt(args[1]);//Number of men
		int women = 6;//Integer.parseInt(args[2]);//Number of women
		int iterations = 3;// Integer.parseInt(args[3]);//Number of iterations to arrive and leave the bathroom
		BathroomProblemSolverInterface solver = new MyBathroomProblemSolver(n);
		Tester tester = new Tester(solver, men, women, n);
		tester.begin(iterations);
		
		//Driver.drive(solver, men, women, iterations);
	}
}
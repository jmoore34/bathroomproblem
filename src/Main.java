class Main
{
	public static void main(String[] args)
	{
		int n = Integer.parseInt(args[0]);//Number of toilets
		int men = Integer.parseInt(args[1]);//Number of men
		int women = Integer.parseInt(args[2]);//Number of women
		int iterations = Integer.parseInt(args[3]);//Number of iterations to arrive and leave the bathroom
		BathroomProblemSolverInterface solver = new MyBathroomProblemSolver(n);
		Tester tester = new Tester(solver, men, women, n);
		tester.begin(iterations);
		
		//Driver.drive(solver, men, women, iterations);
	}
}
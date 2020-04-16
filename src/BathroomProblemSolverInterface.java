enum gender {MAN, WOMAN;

static gender opposite(gender g) {
    if (g == MAN)
        return WOMAN;
    else return MAN;
}
}

public interface BathroomProblemSolverInterface
{
	public void arriveAtTheBathroom(gender type);
	public void leaveTheBathroom(gender type);
}

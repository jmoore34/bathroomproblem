JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		BathroomProblemSolverInterface.java \
		MyBathroomProblemSolver.java \
		Driver.java \
		Main.java \
		Tester.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
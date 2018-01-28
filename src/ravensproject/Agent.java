package ravensproject;// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 *
 * You may also create and submit new files in addition to modifying this file.
 *
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 *
 * These methods will be necessary for the project's main method to run.
 *
 */
public class Agent {

    //Conditions to solve problems ALWAYS REMEMBER TO SET IT IN ORDER TO SOLVE BY VERBAL OU VISUAL
    private boolean shouldUseVerbal = true;
    private boolean shouldUseVisual = false;

    private int answer = -1;

    private String matrix2x2Type = "matrix2x2";
    private String matriz3x3Type = "matrix3x3";

    private int horizontalWay = 0;
    private int verticalWay = 1;

    private HashMap<String, RavensFigure> scenarios;
    private HashMap<String, ObjDifferences> horizontalDifferences;
    private HashMap<String, ObjDifferences> verticalDifferences;

    private RavensProblem currentProblem;

    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * <p>
     * Do not add any variables to this signature; they will not be used by
     * main().
     */
    public Agent() {

    }

    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return an int representing its
     * answer to the question: 1, 2, 3, 4, 5, or 6. Strings of these ints
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName(). Return a negative number to skip a problem.
     * <p>
     * Make sure to return your answer *as an integer* at the end of Solve().
     * Returning your answer as a string may cause your program to crash.
     *
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public int Solve(RavensProblem problem) {
        currentProblem = problem;
        scenarios = problem.getFigures();
        String currentProblemType = problem.getProblemType();

        if (problem.hasVerbal() && matrix2x2Type.equals(currentProblemType) && shouldUseVerbal)
            Solve2x2ByVerbal();
        else if (problem.hasVisual() && matrix2x2Type.equals(currentProblemType) && shouldUseVisual)
            Solve2x2ByImage();
        else if (problem.hasVerbal() && matriz3x3Type.equals(currentProblemType) && shouldUseVerbal)
            Solve3x3ByVerbal();
        else if (problem.hasVisual() && matriz3x3Type.equals(currentProblemType) && shouldUseVisual)
            Solve3x3ByImage();

        return answer;
    }

    private void Solve2x2ByVerbal() {
        RavensFigure scenarioA, scenarioB, scenarioC;
        scenarioA = scenarios.get("A");
        scenarioB = scenarios.get("B");
        scenarioC = scenarios.get("C");

        CompareScenarios(scenarioA, scenarioB, horizontalWay);
        CompareScenarios(scenarioA, scenarioC, verticalWay);
    }

    private void Solve2x2ByImage() {
    }

    private void Solve3x3ByVerbal() {
    }

    private void Solve3x3ByImage() {
    }

    //way variable is to know in which direction is going to be comparison
    private void CompareScenarios(RavensFigure scenario1, RavensFigure scenario2, int way)
    {
        for (String objectName:scenario1.getObjects().keySet()) {

            RavensObject aCurrentObj = scenario1.getObjects().get(objectName);
            RavensObject bCurrentObj = null;

            if(scenario2.getObjects().containsKey(objectName))
                bCurrentObj = scenario2.getObjects().get(objectName);

            if(bCurrentObj != null)
                CompareObjects(aCurrentObj, bCurrentObj, way);
        }
    }

    //way variable is to know in which direction is going to be comparison
    private void CompareObjects(RavensObject obj1, RavensObject obj2, int way)
    {
        for (String attribute:obj1.getAttributes().keySet()) {
          //  if(way == horizontalWay)

            //if(way == verticalWay)
        }
    }

    /**private List<String> missingKeysinHash2;
    private List<String> missingKeysinHash1;

    private void CheckIfObjectsHaveSameKeys(HashMap<String, String> hash1, HashMap<String, String> hash2)
    {
        //Check up for the first hash
        for (String key: hash1.keySet()) {
            if (!hash2.containsKey(key)) {
                if(missingKeysinHash2 == null)
                    missingKeysinHash2 = new ArrayList<>();
                missingKeysinHash2.add(key);
            }
        }

        //Check up for the second hash
        for (String key: hash2.keySet()) {
            if (!hash1.containsKey(key)){
                if(missingKeysinHash1 == null)
                    missingKeysinHash1 = new ArrayList<>();
                missingKeysinHash1.add(key);
            }
        }
    }*/

    private String CompareFillAndShape(String color1, String color2)
    {
        if(color1 == color2)
            return "same";
        else
            return  "changed";
    }

    private String CompareSize(String size1, String size2)
    {
        int intsize1;
        int intsize2;

        if("very small".equals(size1))
            intsize1 = 0;
        else if ("small".equals((size1)))
            intsize1 = 1;
        else if ("medium".equals((size1)))
            intsize1 = 2;
        else if("large".equals(size1))
            intsize1 = 3;
        else
            intsize1 = 4;

        if("very small".equals(size2))
            intsize2 = 0;
        else if ("small".equals((size2)))
            intsize2 = 1;
        else if ("medium".equals((size2)))
            intsize2 = 2;
        else if("large".equals(size2))
            intsize2 = 3;
        else
            intsize2 = 4;

        if(intsize1 == intsize2)
            return "same";
        else if(intsize1 > intsize2)
            return "reduced";
        else
            return "expanded";
    }

    class ObjDifferences{
        public String from;
        public String to;

        public HashMap<String, String> atributes;

        public ObjDifferences(String f, String t){
            from = f;
            to = t;
        }
    }
}

package ravensproject;// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

import java.util.HashMap;

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

    private HashMap<String, RavensFigure> scenarios;
    private HashMap<String, ObjDifferences> vectorOfDifferences;
    private HashMap<String, ObjDifferences> potentialAnswer;

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
        scenarios = problem.getFigures();
        String currentProblemType = problem.getProblemType();

        GetInitialAnswer(scenarios.get("A"));

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

    private void GetInitialAnswer(RavensFigure initialState){
        potentialAnswer = new HashMap<>();

        for(String objs:initialState.getObjects().keySet()){
            ObjDifferences differences = new ObjDifferences(initialState.getObjects().get(objs).getName(), initialState.getObjects().get(objs).getAttributes());
            potentialAnswer.put(initialState.getObjects().get(objs).getName(), differences);
        }
    }

    private void ApplyChangesInInitialAnswer()
    {
        for (String obj:vectorOfDifferences.keySet()) {
            for (String attribute:vectorOfDifferences.get(obj).atributes.keySet()) {
                if(vectorOfDifferences.get(obj).atributes.get(attribute).equals("deleted"))
                    potentialAnswer.get(obj).atributes.remove(attribute);
                else
                    potentialAnswer.get(obj).atributes.put(attribute, vectorOfDifferences.get(obj).atributes.get(attribute));
            }
        }
    }



    private void Solve2x2ByVerbal() {
        RavensFigure scenarioA, scenarioB, scenarioC;
        scenarioA = scenarios.get("A");
        scenarioB = scenarios.get("B");
        scenarioC = scenarios.get("C");

        CompareScenarios(scenarioA, scenarioB);
        CompareScenarios(scenarioA, scenarioC);
    }

    private void Solve2x2ByImage() {
    }

    private void Solve3x3ByVerbal() {
    }

    private void Solve3x3ByImage() {
    }

    private void CompareScenarios(RavensFigure scenario1, RavensFigure scenario2)
    {
        int sizeScenario1 = scenario1.getObjects().size();
        int sizeScenario2 = scenario2.getObjects().size();

        int selectedSize = sizeScenario1 > sizeScenario2 ? sizeScenario2 : sizeScenario1;

        for (int i = 0 ; i < selectedSize; i++) {
            //TODO check case where object exists in scenario A but does not exist in scenario2 and where it exits in scenario B but do not exits in scenario A
            //TODO check if it works
            RavensObject aCurrentObj = (RavensObject)scenario1.getObjects().values().toArray()[i];
            RavensObject bCurrentObj = (RavensObject)scenario2.getObjects().values().toArray()[i];

            CompareObjects(aCurrentObj, bCurrentObj);
        }
    }

    private void CompareObjects(RavensObject obj1, RavensObject obj2)
    {
        if(vectorOfDifferences == null)
            vectorOfDifferences = new HashMap<>();

        ObjDifferences diff = new ObjDifferences(obj1.getName());

        for (String attribute:obj1.getAttributes().keySet()) {

            if(!obj2.getAttributes().containsKey(attribute)) {
                diff.atributes.put(attribute, "deleted");
            }
            else{
                if(!obj1.getAttributes().get(attribute).equals(obj2.getAttributes().get(attribute))) {
                    if(vectorOfDifferences.containsKey(obj1.getName())) {
                        if (!vectorOfDifferences.get(obj1.getName()).atributes.containsKey(attribute))
                            diff.atributes.put(attribute, obj2.getAttributes().get(attribute));
                        else {
                            String answer =
                                    CalculateDifferencesBetweenAtrributes(attribute, vectorOfDifferences.get(obj1.getName()).atributes.get(attribute), obj2.getAttributes().get(attribute));
                            diff.atributes.put(attribute, answer);
                        }
                    }
                    else{
                        if(!obj1.getAttributes().get(attribute).equals(obj2.getAttributes().get(attribute)))
                            diff.atributes.put(attribute, obj2.getAttributes().get(attribute));
                    }
                }
            }
        }

        for(String attribute:obj2.getAttributes().keySet()){
            if(!obj1.getAttributes().containsKey(attribute))
                diff.atributes.put(attribute, obj2.getAttributes().get(attribute));
        }

        if(diff.atributes != null && !diff.atributes.isEmpty())
            vectorOfDifferences.put(obj1.getName(), diff);
    }

    private String CalculateDifferencesBetweenAtrributes(String attribute, String diff1, String diff2)
    {
        //TODO
        return "-1";
    }

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
        public String objName;
        public boolean deleted;

        public HashMap<String, String> atributes;

        public ObjDifferences(String n){
           objName = n;
           atributes = new HashMap<>();
        }

        public ObjDifferences(String n, HashMap<String, String>h){
            objName = n;
            atributes = h;
        }

    }
}

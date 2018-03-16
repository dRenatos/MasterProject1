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

    private int answer = -1;

    private String matrix2x2Type = "2x2";

    //Attributes to be ignored in this project1
    private String ignoreAttribute1 = "above";
    private String ignoreAttribute2 = "inside";
    private String ignoreAttribute3 = "overlaps";

    private HashMap<String, RavensFigure> scenarios;
    private HashMap<String, ObjDifferences> vectorOfDifferences;
    private HashMap<String, ObjDifferences> potentialAnswer;

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
        ResetClassVariables();

        scenarios = problem.getFigures();
        String currentProblemType = problem.getProblemType();

        GetInitialAnswer(scenarios.get("A"));

        if (problem.hasVerbal() && matrix2x2Type.equals(currentProblemType) && shouldUseVerbal) {
            Solve2x2ByVerbal();
            ApplyChangesInInitialAnswer();
            FindAnswer();
        }

        //System.out.println(currentProblem.getName() + " " + answer);
        return answer;
    }

    private void ResetClassVariables()
    {
        scenarios = new HashMap<>();
        vectorOfDifferences = new HashMap<>();
        potentialAnswer = new HashMap<>();
        answer = -1;
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
        if(vectorOfDifferences.isEmpty() || vectorOfDifferences == null)
            return;

        /**if (currentProblem.getName().equals("Basic Problem B-10")) {
            System.out.println("-------------------------------------------");
            System.out.println("Vector of Differences");

            for (String obj : vectorOfDifferences.keySet()) {
                System.out.println("Obj:" + obj);
                for (String attribute : vectorOfDifferences.get(obj).atributes.keySet()) {
                    System.out.println(attribute + ":" + vectorOfDifferences.get(obj).atributes.get(attribute));
                }
            }
            System.out.println("-------------------------------------------");
        }*/

        for (String obj:vectorOfDifferences.keySet()) {
            if(!potentialAnswer.containsKey(obj)) {
                potentialAnswer.put(vectorOfDifferences.get(obj).objName, vectorOfDifferences.get(obj));
            }
            else if(vectorOfDifferences.get(obj).deleted) {
                potentialAnswer.remove(obj);
            }
            else {
                for (String attribute : vectorOfDifferences.get(obj).atributes.keySet()) {
                    if ("deleted".equals(vectorOfDifferences.get(obj).atributes.get(attribute)))
                        potentialAnswer.get(obj).atributes.remove(attribute);
                    else
                        potentialAnswer.get(obj).atributes.put(attribute, vectorOfDifferences.get(obj).atributes.get(attribute));
                }
            }
        }
    }

    private void FindAnswer()
    {
        /**System.out.println("-------------------------------------------");
        System.out.println(currentProblem.getName());

        for (String obj:potentialAnswer.keySet()){
            for (String attribute:potentialAnswer.get(obj).atributes.keySet()){
                System.out.println(attribute + ":" + potentialAnswer.get(obj).atributes.get(attribute));
            }
        }
        System.out.println("-------------------------------------------");*/
        for(int i = 1; i<7; i++){
            if(CompareToCheckIfIsTheAnswer(scenarios.get(Integer.toString(i))))
                break;
        }
    }

    private boolean CompareToCheckIfIsTheAnswer(RavensFigure scenario) {
        if (scenario.getObjects().size() != potentialAnswer.size()) {
            //System.out.println(scenario.getName() + " has failed in size");
            return false;
        }

        HashMap<String, ObjDifferences> possibleAnswer = new HashMap<>();
        for (String obj : scenario.getObjects().keySet()) {
            ObjDifferences differences = new ObjDifferences(scenario.getObjects().get(obj).getName(), scenario.getObjects().get(obj).getAttributes());
            possibleAnswer.put(scenario.getObjects().get(obj).getName(), differences);
        }

        /**if (currentProblem.getName().equals("Basic Problem B-10")){
            System.out.println("-------------------------------------------");
            System.out.println(currentProblem.getName() + " " + " Scenario " + scenario.getName());

            for (String obj : potentialAnswer.keySet()) {
                System.out.println("Obj:" + obj);
                for (String attribute : potentialAnswer.get(obj).atributes.keySet()) {
                    System.out.println(attribute + ":" + potentialAnswer.get(obj).atributes.get(attribute));
                }
            }

            System.out.println("++++++++++++++++++++++++++++++++++++++++");

            for (String obj : scenario.getObjects().keySet()) {
                System.out.println("Obj:" + obj);
                for (String attribute : scenario.getObjects().get(obj).getAttributes().keySet()) {
                    System.out.println(attribute + ":" + possibleAnswer.get(obj).atributes.get(attribute));
                }
            }
        }

        /**for (int i=0; i<potentialAnswer.size(); i++){
            ObjDifferences ans = (ObjDifferences) potentialAnswer.values().toArray()[i];
            ObjDifferences potenAns = (ObjDifferences) possibleAnswer.values().toArray()[i];

            for (String attribute:ans.atributes.keySet()){
                if(!attribute.equals(ignoreAttribute1) && !attribute.equals(ignoreAttribute2) && !attribute.equals(ignoreAttribute3)) {
                    if(!ans.atributes.get(attribute).equals(potenAns.atributes.get(attribute))) {
                        //System.out.println(currentProblem.getName() + " " + scenario.getName() +
                         //       " has failed in attribute: " + attribute + " value1: " + potenAns.atributes.get(attribute) + " value2: " + ans.atributes.get(attribute));
                        return false;
                    }
                }
            }
        }*/

        boolean hasFoundObj = false;
        for(String obj1:potentialAnswer.keySet()){
            ObjDifferences diff1 = potentialAnswer.get(obj1);

            for (String obj2:possibleAnswer.keySet()){
                ObjDifferences diff2 = possibleAnswer.get(obj2);
                hasFoundObj = true;

                for (String attribute:diff1.atributes.keySet()){
                    if(!attribute.equals(ignoreAttribute1) && !attribute.equals(ignoreAttribute2) && !attribute.equals(ignoreAttribute3)) {
                        if(!diff1.atributes.get(attribute).equals(diff2.atributes.get(attribute))){
                            hasFoundObj = false;
                            break;
                        }
                    }
                }
                if (hasFoundObj)
                    break;
            }

            if(!hasFoundObj)
                break;
        }

        if(hasFoundObj) {
            answer = Integer.parseInt(scenario.getName());
            return true;
        }
        else
            return false;
    }

    private void Solve2x2ByVerbal() {
        RavensFigure scenarioA, scenarioB, scenarioC;
        scenarioA = scenarios.get("A");
        scenarioB = scenarios.get("B");
        scenarioC = scenarios.get("C");

        CompareScenarios(scenarioA, scenarioB);
        CompareScenarios(scenarioA, scenarioC);
    }

    private void CompareScenarios(RavensFigure scenario1, RavensFigure scenario2)
    {
        int sizeScenario1 = scenario1.getObjects().size();
        int sizeScenario2 = scenario2.getObjects().size();

        int selectedSize = (sizeScenario1 > sizeScenario2) ? sizeScenario2 : sizeScenario1;

        for (int i = 0 ; i < selectedSize; i++) {
            RavensObject aCurrentObj = (RavensObject)scenario1.getObjects().values().toArray()[i];
            RavensObject bCurrentObj = (RavensObject)scenario2.getObjects().values().toArray()[i];

            CompareObjects(aCurrentObj, bCurrentObj);
        }

        if(sizeScenario1 != sizeScenario2) //Check necessity to add or remove objects in VectorOfDifference
        {
            if(sizeScenario1 > sizeScenario2)
            {
                int sizediff = sizeScenario1 - sizeScenario2;

                for(int i=0; i<sizediff; i++){       //Removing objects
                    AddObjectInVectorOfDifference((RavensObject)scenario1.getObjects().values().toArray()[sizeScenario2+i], false);
                }
            }
            else
            {
                int sizediff = sizeScenario2 - sizeScenario1;

                for(int i=0; i<sizediff; i++){      //Adding objects
                    AddObjectInVectorOfDifference((RavensObject)scenario2.getObjects().values().toArray()[sizeScenario1+i], true);
                }
            }
        }
    }

    private void AddObjectInVectorOfDifference(RavensObject object, boolean state)
    {
        ObjDifferences diff;
        if(state)
            diff = new ObjDifferences(object.getName(), object.getAttributes());
        else
        {
            diff = new ObjDifferences(object.getName());
            diff.deleted = true;
        }

        if(vectorOfDifferences == null)
            vectorOfDifferences = new HashMap<>();

        vectorOfDifferences.put(diff.objName, diff);
    }

    private void CompareObjects(RavensObject obj1, RavensObject obj2)
    {
        if(vectorOfDifferences == null)
            vectorOfDifferences = new HashMap<>();

        ObjDifferences diff = new ObjDifferences(obj1.getName());

        for (String attribute:obj1.getAttributes().keySet()) {
            if(!ignoreAttribute1.equals(attribute) && !ignoreAttribute2.equals(attribute) && !ignoreAttribute3.equals(attribute)) {     //To check if the attributes are differents from those to ignore
                if (!obj2.getAttributes().containsKey(attribute)) {
                    diff.atributes.put(attribute, "deleted");
                } else {
                    //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                    //    System.out.println("Comparing alignment problem 5 step 1");

                    if (!obj1.getAttributes().get(attribute).equals(obj2.getAttributes().get(attribute))) {
                        //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                         //   System.out.println("Comparing alignment problem 5 step 2");

                        if (vectorOfDifferences.containsKey(obj1.getName())) {
                            if (!vectorOfDifferences.get(obj1.getName()).atributes.containsKey(attribute)) {

                                //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                                //   System.out.println("Comparing alignment problem 5 WRONG CASE 1");

                                vectorOfDifferences.get(obj1.getName()).atributes.put(attribute, obj2.getAttributes().get(attribute));
                            }else {
                                //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                                 //   System.out.println("Comparing alignment problem 5 WRONG CASE 2");

                                String answer =
                                        CalculateDifferencesBetweenAtrributes(attribute, obj1.getAttributes().get(attribute),
                                                vectorOfDifferences.get(obj1.getName()).atributes.get(attribute), obj2.getAttributes().get(attribute));

                                //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                                  //  System.out.println("answer is: " + answer);

                                vectorOfDifferences.get(obj1.getName()).atributes.put(attribute, answer);
                            }
                        } else {
                            //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                             //   System.out.println("Comparing alignment problem 5 step 3");

                            diff.atributes.put(attribute, obj2.getAttributes().get(attribute));
                        }
                    }
                }
            }
        }

        for(String attribute:obj2.getAttributes().keySet()){
            if(!ignoreAttribute1.equals(attribute) && !ignoreAttribute2.equals(attribute) && !ignoreAttribute3.equals(attribute)) {     //To check if the attributes are differents from those to ignore
                if (!obj1.getAttributes().containsKey(attribute)) {
                    //if(currentProblem.getName().equals("Basic Problem B-05") && attribute.equals("alignment"))
                     //   System.out.println("Comparing alignment problem 5 step 4");

                    diff.atributes.put(attribute, obj2.getAttributes().get(attribute));
                }
            }
        }

        if(diff.atributes != null && !diff.atributes.isEmpty() && !vectorOfDifferences.containsKey(obj1.getName()))
            vectorOfDifferences.put(obj1.getName(), diff);
    }

    private String CalculateDifferencesBetweenAtrributes(String attribute, String originalValue, String diff1, String diff2)
    {
        String answer = "";

        if("angle".equals(attribute))
            answer = CompareAngle(diff1, diff2);
        else if("alignment".equals(attribute))
            answer = CompareAligment(originalValue, diff1, diff2);

        return answer;
    }

    private String CompareAngle(String value1, String value2)
    {
        if("deleted".equals(value1))
            return value1;

        if("deleted".equals(value2))
            return value2;

        int baseAngle = 90;
        int val1 = Integer.parseInt(value1);
        int val2= Integer.parseInt(value2);

        int baseLine = val1 % baseAngle;

        int partialResult = Math.abs(val1 - val2);
        int result = baseLine + partialResult;

        return Integer.toString(result);
    }

    private String CompareAligment(String baseValue, String value1, String value2)
    {
       // System.out.println("Values to compare: " + " " + baseValue + " " + value1 + " " + value2);

        String[] partialAnswer = baseValue.split("-");
        String[] group1 = value1.split("-");
        String[] group2 = value2.split("-");

        String result="";
        for(int i=0; i<partialAnswer.length; i++)
        {
           // System.out.println("Comparing: " + partialAnswer[i]);

            if(!partialAnswer[i].equals(group1[i])) {
               // System.out.println("group1 Difference: " + partialAnswer[i] + " " + group1[i]);
                result += group1[i];
            }

            if(!partialAnswer[i].equals(group2[i])) {
               // System.out.println("group2 Difference: " + partialAnswer[i] + " " + group2[i]);
                result += group2[i];
            }

            if(i==0)
                result+="-";
        }

        //System.out.println("Final result: " + result);
        return result;
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

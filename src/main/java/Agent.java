// Uncomment these lines to access image processing.
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

    private HashMap<String, RavensFigure> images;
    private HashMap<String, Differences> diffhash;

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
        images = problem.getFigures();
        problem.getProblemType();
        if (problem.hasVerbal() && problem.getProblemType() == "2x2") {
            Solve2x2ByVerbal();
        } else if (problem.hasVisual() && problem.getProblemType() == "2x2") {
            Solve2x2ByImage();
        } else if (problem.hasVerbal() && problem.getProblemType() == "3x3") {
            Solve3x3ByVerbal();
        } else
            Solve3x3ByImage();
        return -1;
    }

    private void Solve2x2ByVerbal() {
        RavensFigure aImage,bImage,cImage;
        aImage = images.get("A");
        bImage = images.get("B");
        cImage = images.get("C");

        for (String object:
             aImage.getObjects().keySet()) {

            RavensObject aCurrentObj = aImage.getObjects().get(object);
            RavensObject bCurrentObj;
            String objName = aCurrentObj.getName();

            if(bImage.getObjects().containsKey(objName))
                bCurrentObj = bImage.getObjects().get(objName);


        }
    }

    private void Solve2x2ByImage() {
    }

    private void Solve3x3ByVerbal() {
    }

    private void Solve3x3ByImage() {
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

    class Differences{
        public String shape;
        public String color;
        public String size;

        public Differences(String s, String c, String z){
            shape = s;
            color = c;
            size = z;
        }
    }
}

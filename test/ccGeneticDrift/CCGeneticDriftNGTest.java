package ccGeneticDrift;

import java.util.List;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author GHajba
 */
public class CCGeneticDriftNGTest {

    /**
     * Test of sortAlgorithm method, of class CCGeneticDrift.
     */
    @Test
    public void testSortAlgorithm() {
        System.out.println("sortAlgorithm");
        String input = "8 0 3 1 6 5 -2 4 7";
        CCGeneticDrift instance = new CCGeneticDrift();
        int expResult = 5;
        int result = instance.sortAlgorithm(input);
        assertEquals(result, expResult);
    }

    /**
     * Test of calculateScore method, of class CCGeneticDrift.
     */
    @Test
    public void testCalculateScore() {
        System.out.println("calculateScore");
        String input1 = "8 0 3 1 6 5 -2 4 7 1 3 -2 6";
        String input2 = "8 0 3 1 6 5 -2 4 7 3 2 -2 6";
        CCGeneticDrift instance = new CCGeneticDrift();
        int expResult1 = 2;
        int expResult2 = 4;
        int result1 = instance.calculateScore(input1);
        assertEquals(result1, expResult1);
        int result2 = instance.calculateScore(input2);
        assertEquals(result2, expResult2);
    }

    /**
     * Test of buildInverse method, of class CCGeneticDrift.
     */
    @Test
    public void testBuildInverse() {
        System.out.println("buildInverse");
        int xIndex = 2;
        int yIndex = 5;
        String input1 = "6 3 1 6 5 -2 4";
        String input2 = "6 3 1 6 5 -2 4";
        CCGeneticDrift instance = new CCGeneticDrift();
        List<Integer> permutation = instance.inputStringToIntegerList(input1);
        String expResult = "[6, 3, 1, 2, -5, -6, 4]";
        List result = instance.buildInverse(permutation, xIndex, yIndex);
        assertEquals(result.toString(), expResult);
    }

    /**
     * Test of findOrientedPairs method, of class CCGeneticDrift.
     */
    @Test
    public void testFindOrientedPairs() {
        System.out.println("findOrientedPairs");
        
        CCGeneticDrift instance = new CCGeneticDrift();
        String input = "6 3 1 6 5 -2 4";
        List<Integer> numbers = instance.inputStringToIntegerList(input);
        String expResult = "[1 -2, 3 -2]";
        List result = instance.findOrientedPairs(numbers);
        assertEquals(result.toString(), expResult);
        assertEquals(result.size(), 2);
    }
    
}

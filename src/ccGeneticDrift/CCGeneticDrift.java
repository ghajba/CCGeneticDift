package ccGeneticDrift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author GHajba
 */
public class CCGeneticDrift {

    class OrientedPair {

        Integer x;
        Integer y;

        int xIndex;
        int yIndex;

        public OrientedPair(Integer x, Integer y, int xIndex, int yIndex) {
            this.x = x;
            this.y = y;
            this.xIndex = xIndex;
            this.yIndex = yIndex;
        }

        @Override
        public String toString() {
            return "" + x + " " + y;
        }

        public String toInputString() {
            return "" + x + " " + xIndex + " " + y + " " + yIndex;
        }

    }

    class OrientedPairComparator implements Comparator<OrientedPair> {

        @Override
        public int compare(OrientedPair o1, OrientedPair o2) {
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }

            if (o1.x.equals(o2.x)) {
                return o1.y - o2.y;
            }
            return o1.x - o2.x;
        }

    }

    /**
     * Sorts as long as there are oriented pairs in the permutation, and takes in each iteration the inverse
     *  with the highest score
     * @param input the permutation as a string
     * @return  the number of iterations to sort the permutation
     */
    public int sortAlgorithm(String input) {
        List<Integer> numbers = inputStringToIntegerList(input);

        Integer permutations = numbers.get(0);
        List<Integer> permutationList = numbers.subList(0, permutations + 1);

        int iterations = 0;

        while (true) {

            List<OrientedPair> orientedPairs = findOrientedPairs(permutationList);
            if (orientedPairs.isEmpty()) {
                break;
            }
            iterations++;

            int maxScore = 0;
            List<Integer> result = null;
            for (OrientedPair op : orientedPairs) {

                final List<Integer> inverseList = buildInverse(permutationList, op.xIndex, op.yIndex);
                int score = findOrientedPairs(
                        inverseList).size();
                if (score >= maxScore) {
                    maxScore = score;
                    result = inverseList;
                }

            }
            permutationList = new ArrayList<>(result);
        }

        return iterations;

    }

    /**
     * converts an input integer String to a list of Integers
     * @param input the input permutation as string
     * @return the list of Integers converted from the input
     */
    public List<Integer> inputStringToIntegerList(String input) {
        String[] numberStrings = input.split(" ");
        List<Integer> numbers = new ArrayList<>();
        for (String n : numberStrings) {
            numbers.add(Integer.valueOf(n));
        }
        return numbers;
    }

    /**
     * The score is how much oriented pairs are in the inverse of the permutation
     * @param input the permutation and the indices as a String
     * @return the score of the input
     */
    public int calculateScore(String input) {
        List<Integer> numbers = inputStringToIntegerList(input);

        Integer permutations = numbers.get(0);
        final Integer xIndex = numbers.get(permutations + 2);
        final Integer yIndex = numbers.get(permutations + 4);

        List<Integer> inverseList = buildInverse(numbers.subList(0, permutations + 1), xIndex, yIndex);
        return findOrientedPairs(inverseList).size();

    }

    /**
     * Builds the inverse of the permutation
     * @param permutation the permutations including its length
     * @param xIndex the index of the first part of the oriented pair
     * @param yIndex the index of the second part of the oriented pair
     * @return the inverse of the permutation
     */
    public List<Integer> buildInverse(final List<Integer> permutation, int xIndex, int yIndex) {
        List<Integer> resultList = new ArrayList<>(permutation);
        boolean negativeSum = (permutation.get(xIndex)+permutation.get(yIndex)) < 0;
        if (negativeSum) {
            // invert from xIndex+1 to yIndex

            List<Integer> tempList = resultList.subList(xIndex+1, yIndex + 1);
            Collections.reverse(tempList);

            int counter = 0;
            for (int i = xIndex+1; i < yIndex + 1; i++) {
                resultList.set(i, tempList.get(counter) * -1);
                counter++;
            }

        } else {
            // invert from xIndex to yIndex-1
            List<Integer> tempList = resultList.subList(xIndex, yIndex);
            Collections.reverse(tempList);

            int counter = 0;
            for (int i = xIndex; i < yIndex; i++) {
                resultList.set(i, tempList.get(counter) * -1);
                counter++;
            }

        }

        return resultList;
    }

    /**
     * Finds oriented pairs in a permutation
     * @param permutation the permutation to find the pairs in
     * @return the found oriented pairs or an empty list
     */
    public List<OrientedPair> findOrientedPairs(List<Integer> permutation) {
        List<OrientedPair> orientedPairs = new ArrayList<>();

        for (int i = 1; i < permutation.size() - 1; i++) {
            Integer x = permutation.get(i);
            for (int j = i + 1; j < permutation.size(); j++) {

                Integer y = permutation.get(j);
                if ((x < 0 && y < 0) || (x >= 0 && y >= 0)) {
                    continue;
                }

                if (Math.abs(x + y) != 1) {
                    continue;
                }
                OrientedPair op;

                op = new OrientedPair(x, y, i, j);
                orientedPairs.add(op);
            }
        }

        orientedPairs.sort(new OrientedPairComparator());

        return orientedPairs;

    }

}

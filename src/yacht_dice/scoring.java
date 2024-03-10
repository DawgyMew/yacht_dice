package yacht_dice;
import java.util.Scanner;
/**
 * Scoring Calculations for Yacht Dice.
 * @author Kat
 * 
 */

// Aces, Deuces, Threes, Fours, Fives, Sixes
// Subtotal Bonus of 35 if above 63

// Choice, 4 of a Kind, Full House, S. Straight, L. Straight, Yacht
// S. Straight: 15 Points, L Straight: 30 Points, Yacht: 50 Points
// Total

public class scoring {
    // test for the scoring that uses a default number array //
    public static void test(Scanner sc){
        //int[] testNums = {1, 6, 3, 5, 3}; 
        int[] testNums = {1, 2, 2, 3, 4}; 
        boolean[] testStatus = {true, false, false, false, true, false, false, false, false, false, false, false};
        int[] testScoreSheet = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        generateScores(testNums, testStatus, testScoreSheet);
        sc.nextLine();
    }
    // temp test if the calculation works and applies the subtotal bonus //
    public static void testScores(){
        int[] testSheet = {10, 14, 10, 10, 10, 10, 10, 0, 0, 0, 0, 0};
        System.out.println(calcTotal(testSheet));
    }
    // 
    public static int[] generateScores(int[] dieRolls, boolean[] scoreStatus, int[] scoreSheet){
        var numberPoints = checkNumbers(dieRolls);
        var choicePoints = checkChoice(dieRolls);
        var fourKindPoints = checkFourKind(dieRolls);
        var fullHousePoints = checkFullHouse(dieRolls);
        var straightPoints = checkStraights(dieRolls);
        var yachtPoints = checkYacht(dieRolls);
        var allScores = combineAllScores(numberPoints, choicePoints, fourKindPoints, fullHousePoints, straightPoints, yachtPoints);
        displayAllScores(allScores, scoreStatus, scoreSheet);
        return allScores;
    }
    // Returns the totals of all the individual number combinations. //
    private static int[] checkNumbers(int[] die){
        int[] scoreArray = {0, 0, 0, 0, 0, 0};
        for (int i = 1; i <= scoreArray.length; i++){
            int count = 0;
            for (var number : die){
                if (number == i){
                    count++;
                }
            }
            //System.out.println(i + " " + count + " " + (i * count));
            scoreArray[i - 1] = i * count;
        }
        //System.out.println(Arrays.toString(scoreArray));
        return scoreArray;
    }
    // choice adds up all 5 die rolls //
    private static int checkChoice(int[] die){
        int score = 0;
        for (var roll : die){
            score += roll;
        }
        return score;
    }
    // check if there are four of a kind //
    private static int checkFourKind(int[] die){
        for (int i = 1; i <= 6; i++){
            var count = countNum(i, die);
            if (count >= 4){
                return (i * 4);
            }
        }
        return 0;
    }
    private static int checkFullHouse(int[] die){
        for (int i = 1; i <= 6; i++){
            var count = countNum(i, die);
            if (count == 5){ // check if all 5 are the same. Technically counts lel
                return (i * 5);
            }
            else if (count == 3){
                int threeCount = i;
                System.out.println(threeCount);
                for (int j = 1; j <= 6; j++){
                    var smallCount = countNum(j, die);
                    if (smallCount == 2){
                        int twoCount = j;
                        return ((twoCount * 2) + (threeCount * 3));
                    }
                        
                }
            }
        }
        return 0; // return nothing if doesnt meet criteria
    }
    // Straights //
    private static int[] checkStraights(int[] die){
        int[] straightScores = {0, 0};
        sortArray(die);
        int chain = 0;
        int maxChain = 0;
        for (int i = 0; i < die.length - 1; i++){
            var difference = die[i + 1] - die[i];
            if (difference == 1){
                chain++;
            }
            else if (difference == 0){
                continue;
            }
            else{
                chain = 0;
            }
            if (chain > maxChain){ 
                maxChain = chain;
            }
            //System.out.println("Chain: " + chain);
            //System.out.println("Max Chain: " + maxChain);
        }
        //System.out.println("Final Max Chain: " + maxChain);
        // Large Straight //
        switch(maxChain){
            default -> {straightScores[0] = 0; straightScores[1] = 0;}
            case 4 -> {straightScores[0] = 15; straightScores[1] = 30;} // Large Straight 
            case 3 -> {straightScores[0] = 15; straightScores[1] = 0;} // Small Straight
        }
            // 4 is max it can calculate
        return straightScores;
    }
    // checks if all the die are the same for a yacht. //
    private static int checkYacht(int[] die){
        for (var rolls : die){
            if (die[0] != rolls){
                return 0;
            }
        }
        return 50;
    }


    // returns the amount of times that a specific number is in the array //
    private static int countNum(int numToCount, int[] rolls){
        int count = 0;
        for (var number : rolls){
            if (numToCount == number){
                count++;
            }
        }
        return count;
    }
    // combines all the scores into one array //
    private static int[] combineAllScores(int[] numScores, int choice, int fourKind, int fullHouse, int[] straights, int yacht){
        int[] combinedScores = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        System.arraycopy(numScores, 0, combinedScores, 0, 6); // each of the top 6 numbered scores, also netbeans suggested this idk :3
        combinedScores[6] = choice;
        combinedScores[7] = fourKind;
        combinedScores[8] = fullHouse;
        System.arraycopy(straights, 0, combinedScores, 9, 2);
        combinedScores[11] = yacht;
        //System.out.println(Arrays.toString(combinedScores));
        return combinedScores;
    }
    public static void displayAllScores(int[] numScores, boolean[] scoreUsed, int[] scoreSheet){
        String[] sectionNames = {"Aces", "Deuces", "Threes", "Fours", "Fives", "Sixes", "Choice", "4 of a Kind", "Full House", "S. Straight", "L. Straight", "Yacht"};
        for (int i = 0; i < sectionNames.length; i++){
            String scoreNum;
            if (scoreUsed[i] == true){
                scoreNum = "[" + scoreSheet[i] + "]";
            }
            else{
                scoreNum = "" + numScores[i];
            }
            System.out.printf("%n%s: %s", sectionNames[i], scoreNum);
        }
    }
    // writes
    public static int chooseScore(boolean[] scoreStatus, Scanner sc){
        String[] sectionNames = {"aces", "deuces", "threes", "fours", "fives", "sixes", "choice", "4 of a kind", "full house", "s straight", "l straight", "yacht"};
        do{
            System.out.print("\nWhich Score would you like to save?(Enter the category name): ");
            var errMessage = "";
            var catChoice = sc.nextLine();
            for (int i = 0; i < sectionNames.length; i++){
                if (catChoice.equalsIgnoreCase(sectionNames[i])){
                    if (scoreStatus[i] == false){
                        return i;
                    }
                    else{
                        errMessage = (sectionNames[i] + " already has a score. Try again..");
                    }
                }
            }
            if (errMessage.isEmpty()){
                errMessage = "Category not Found. Try again..";
            }
            System.out.println(errMessage);
        } while(true); 
    }
    public static void setScore(int scoreSheet[], boolean scoreSheetStatus[], int[] possScores, int choice){
        var scoreNum = possScores[choice];
        scoreSheet[choice] = scoreNum;
        scoreSheetStatus[choice] = true;
    }
    // Calculates the total of the scoresheet //
    public static int calcTotal(int[] scoreSheet){
        int total = 0;
        for (int i = 0; i < scoreSheet.length; i++){
            if (i == 6 && total >= 63){
                System.out.println("Subtotal Bonus! + 35 Points!");
                total += 35;
            }
            total += scoreSheet[i];
        }
        return total;
    }
    // uses bubble sort to sort the array //
    public static void sortArray(int arr[]){
        int i, j, temp;
        int length = arr.length;
        boolean swapped;
        for (i = 0; i < length - 1; i++){
            swapped = false;
            for (j = 0; j < length - i - 1; j++){
                if (arr[j] > arr[j + 1]){
                    temp = arr[j]; // makes temp variable for the number
                    arr[j] = arr[j + 1]; // replaces current with next
                    arr[j + 1] = temp; // replaces next with current(temp)
                    swapped = true;
                }
            }
            // stops if doesnt swap
            if (!swapped) break; 
        }
    }
}

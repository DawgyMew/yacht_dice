package yacht_dice;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Yacht Dice
 * @author Kat
 * Recreation of the hit game yacht dice in java
 * :3
 */
public class Yacht_dice extends scoring{
    public static void main(String[] args) {
        // define variables
        Scanner sc = new Scanner(System.in);
        scoring.test(sc);
        //scoring.testScores();
        final var MAX_ROLL_COUNT = 3;
        int[] scoreSheet = new int[12];
        // scoreSheetStatus keeps track of if the score has been written to.
        boolean[] scoreSheetStatus = new boolean[12];
        //scoring.test(sc);
        //scoring.chooseScore(scoreSheetStatus, sc);
        for (int turn = 0; turn < scoreSheet.length; turn++){
            int[] savedNums = new int[5];// array for numbers the user saves
            int[] finalRolls = new int[5]; // array to save the final rolls because it wont let me define newRolls outside of the loop and im going to meow so loud.
            // For 1 turn
            for (int i = 1; i <= MAX_ROLL_COUNT; i++){
                if (checkSaved(savedNums) < 5){ // makes it skip to the end if user saves all the numbers
                    int[] newRolls = new int[5]; // Create empty array with 5 empty slots
                    for (int j = 0; j < (5 - checkSaved(savedNums)); j++){
                        newRolls[j].rollDie();
                    }
                    //showNewRolls(newRolls);
                    if (i != MAX_ROLL_COUNT){
                        savedNums = saveRoll(newRolls, savedNums, sc);
                        removeSavedNum(savedNums, sc);
                    }
                    //System.out.println(Arrays.toString(savedNums));
                    finalRolls = newRolls;
                }
            }
            var finalNumbers = combineArrays(savedNums, finalRolls);
            System.out.println(Arrays.toString(finalNumbers));
            var scoreArray = scoring.generateScores(finalNumbers, scoreSheetStatus, scoreSheet); // possible scores to get
            var catChoice = scoring.chooseScore(scoreSheetStatus, sc);
            scoring.setScore(scoreSheet, scoreSheetStatus, scoreArray, catChoice);
        }   
        System.out.println("Final Score Sheet:");
        boolean[] emptyStatus = {false, false, false, false, false, false, false, false, false, false, false, false};
        scoring.displayAllScores(scoreSheet, emptyStatus, scoreSheet);
        var total = scoring.calcTotal(scoreSheet);
        System.out.println("\nTotal Score: " + total);
        sc.close();
    }
    

    // Get a random number from 1 to 6 //
    public static int rollDice(){
        var rollNum = (int)(Math.random() * 6) + 1;
        return rollNum;
    }

    // Display the new numbers that were rolled //
    public static void showRolls(Die[] rolls, int[] saved){
        var numInSave = checkSaved(saved);
        System.out.print("\nNew: ");
        if (numInSave != 0){System.out.print("\t Saved:");}
        System.out.println();
        // loop that displays the numbers
        for (var rollNum : rolls){
            if (rollNum != 0){
                System.out.print(rollNum + " ");
            }
        }
        if (numInSave != 0){ 
            System.out.print("-- ");
            for (var rollNum : saved){
                if (rollNum != 0){
                    System.out.print(rollNum + " ");
                }
            }
        }
        // Loop that prints out which number the roll is in
        //System.out.println("\n===========");
        //for (int i = 1; i <= newRolls.length; i++){
        //    System.out.print(i + " ");
        //}
    }
    // Let the user save any of the numbers they rolled. //
    public static int[] saveRoll(Die[] rolls, int[] saved, Scanner sc){
        int responseNum = 10; // high number to start
        do{
            showRolls(rolls, saved);
            System.out.print("\nWhich number would you like to save? (Enter '0' to move on): ");
            try{responseNum = Integer.parseInt(sc.nextLine());}
            catch (NumberFormatException e) {System.out.println("Invalid Response."); continue;}
            //responseNum = Integer.parseInt(response);
            for (int i = 0; i < rolls.length; i++){ // check each number in the roll array 
                if (responseNum == rolls[i].getRoll()){
                    for (int j = 0; j < saved.length; j++){
                        if (saved[j] == 0){
                            saved[j] = responseNum; // replace the first available 0 in the saved array with the number.
                            rolls[i] = 0;
                            break; // prevent it from replacing all of the 0s.
                        }
                    }
                    break; // prevent it from saving all the instances of the chosen number.
                }
            }
        } while (responseNum != 0);
        return saved;
    }
    // Let the user remove any values from the saved array //
    public static void removeSavedNum(int saved[], Scanner sc){
        int responseNum = 10;
        while (responseNum != 0 && (checkSaved(saved) != 0)){
            System.out.print("\nWhich saved number would you like to remove?(Enter '0' to move on): ");
            responseNum = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < saved.length; i++){
                if (saved[i] == responseNum){
                    saved[i] = 0;
                    break;
                }
            }
        }
    }
    // Returns number of actual numbers are in the saved list // 
    public static int checkSaved(int[] saved){
        int count = 0;
        for (var num : saved)
            if (num != 0){
                count += 1;
            }
        return count;
    }
    // combines the saved and last new roll :3 //
    public static int[] combineArrays(int[] saved, int[] rolls){
        int[] combined = {0, 0, 0, 0, 0};
        for (var number : saved){ // checks each number in the saved list
            if (number != 0){ // only replaces not zeros
                for (int i = 0; i < combined.length; i++){ 
                    if (combined[i] == 0){ // only replace the zeros in the combined list
                        combined[i] = number;
                        break;
                    }
                }
                //break;
            }
        }
        for (var number : rolls){
            if (number != 0){
                for (int i = 0; i < combined.length; i++){
                    if (combined[i] == 0){
                        combined[i] = number;
                        break;
                    }
                }
                //break;
            }
        }
        return combined;
    }
}

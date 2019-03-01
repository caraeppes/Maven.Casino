package io.zipcoder.casino.DiceGame.Yahtzee;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.DiceGame.DiceUtils.DiceGame;
import io.zipcoder.casino.Utilities.Player;
import io.zipcoder.casino.Utilities.Console;
import io.zipcoder.casino.Utilities.DisplayGraphics;

import java.util.*;

import static io.zipcoder.casino.DiceGame.Yahtzee.YahtzeeScorecard.getScoreCardString;

public class Yahtzee extends DiceGame {
    private YahtzeePlayer yahtzeePlayer;
    private int score;

    public void setScoreCard(TreeMap<String, Integer> scoreCard) {
        this.scoreCard = scoreCard;
    }

    private static TreeMap<String, Integer> scoreCard;
    private ArrayList<Dice> savedDice;
    private ArrayList<Dice> rolledDice;
    private boolean playing = true;
    Console console = Console.getInstance();
    String input = "";


    public Yahtzee(Player player) {
        this.yahtzeePlayer = new YahtzeePlayer(player);
        this.score = 0;
        this.scoreCard = YahtzeeScorecard.setUpScoreCard();
        this.savedDice = new ArrayList<>();
        this.rolledDice = new ArrayList<>();
    }

    @Override
    public void play() {
        console.println(DisplayGraphics.welcomeToYahtzeeString());
        input = console.getStringInput("\nHello %s!  Welcome to Yahtzee!  Type 'roll' to begin!", yahtzeePlayer.getName());
        while (playing) {
           playGame();
        }
    }


    // walkAway takes user back to the casino
    public void walkAway() {
        playing = false;
        System.out.println("Thank you for playing Yahtzee!");
    }


    // getAllDice merges all rolledDice and savedDice into one ArrayList
    public ArrayList<Dice> getAllDice(ArrayList<Dice> rolledDice, ArrayList<Dice> savedDice) {
        ArrayList<Dice> allDice = rolledDice;
        for (Dice die : savedDice) {
            allDice.add(die);
        }
        return allDice;
    }


    // this method will get the score for the entered category based on the dice
    public static Integer getScoreForCategory(String category, ArrayList<Dice> allDice) {
        String categoryToScore = category.toUpperCase().replaceAll("\\s+","");

            Integer value = YahtzeeCategory.valueOf(categoryToScore).getValue();
            Integer score = YahtzeeCategory.valueOf(categoryToScore).perform(allDice, value);

        return score;
    }

    public static int scoreUpperSection(ArrayList<Dice> allDice, int value){
        int score = 0;
        for (Dice die : allDice){
            if (die.getValue() == value){
                score += value;
            }
        }
        return score;
    }


    public static boolean hasThreeOfAKind(ArrayList<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);
        for (Integer dieCount : diceCount) {
            if (dieCount >= 3) {
                return true;
            }
        }
        return false;
    }

    public static Integer scoreThreeOfAKind(ArrayList<Dice> allDice) {
        if (hasThreeOfAKind(allDice)) {
            return getSumOfDice(allDice);
        }
        return 0;
    }


    public static boolean hasFourOfAKind(ArrayList<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);
        for (Integer dieCount : diceCount) {
            if (dieCount >= 4) {
                return true;
            }
        }
        return false;
    }


    public static int scoreFourOfAKind(ArrayList<Dice> allDice) {
        if (hasFourOfAKind(allDice)) {
            return getSumOfDice(allDice);
        }
        return 0;
    }


    public static boolean hasFullHouse(ArrayList<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);
        boolean hasTwoCount = false;
        boolean hasThreeCount = false;

        for (Integer dieCount : diceCount) {
            if (dieCount == 2) {
                hasTwoCount = true;
            }
            if (dieCount == 3) {
                hasThreeCount = true;
            }
        }
        return (hasTwoCount && hasThreeCount);
    }


    public static int scoreFullHouse(ArrayList<Dice> allDice) {
        if (hasFullHouse(allDice)) {
            return 25;
        } else {
            return 0;
        }
    }


    public static boolean hasSmallStraight(ArrayList<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);

        if ((diceCount[0] >= 1) && (diceCount[1] >= 1) && (diceCount[2] >= 1) && (diceCount[3] >= 1)) {
            return true;
        }
        if ((diceCount[1] >= 1) && (diceCount[2] >= 1) && (diceCount[3] >= 1) && (diceCount[4] >= 1)) {
            return true;
        }
        if ((diceCount[2] >= 1) && (diceCount[3] >= 1) && (diceCount[4] >= 1) && (diceCount[5] >= 1)) {
            return true;
        } else {
            return false;
        }
    }


    public static int scoreSmallStraight(ArrayList<Dice> allDice) {
        if (hasSmallStraight(allDice)) {
            return 30;
        } else {
            return 0;
        }
    }


    public static boolean hasLargeStraight(ArrayList<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);

        if ((diceCount[0] == 1) && (diceCount[1] == 1) && (diceCount[2] == 1) && (diceCount[3] == 1) && (diceCount[4] == 1)) {
            return true;
        }
        if ((diceCount[1] == 1) && (diceCount[2] == 1) && (diceCount[3] == 1) && (diceCount[4] == 1) && (diceCount[5] == 1)) {
            return true;
        } else {
            return false;
        }
    }


    public static int scoreLargeStraight(ArrayList<Dice> allDice) {
        if (hasLargeStraight(allDice)) {
            return 40;
        } else {
            return 0;
        }
    }


    public static boolean hasYahtzee(ArrayList<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);

        for (Integer dieCount : diceCount) {
            if (dieCount == 5) {
                return true;
            }
        }
        return false;
    }


    public static int scoreYahtzee(ArrayList<Dice> allDice) {
        if (hasYahtzee(allDice)) {
            return 50;
        } else {
            return 0;
        }
    }


    public static int scoreChance(ArrayList<Dice> allDice) {
        return getSumOfDice(allDice);
    }



    public YahtzeePlayer getYahtzeePlayer() {
        return this.yahtzeePlayer;
    }

    public static TreeMap<String, Integer> getScoreCard() {
        return scoreCard;
    }

    public ArrayList<Dice> getSavedDice() {
        return this.savedDice;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Dice> getRolledDice() {
        return rolledDice;
    }


    public static Integer[] countDice(ArrayList<Dice> dice) {
        Integer[] diceCounter = {0, 0, 0, 0, 0, 0};
        for (Dice die : dice) {
            diceCounter[die.getValue() - 1]++;
        }
        return diceCounter;
    }


    public static int getSumOfDice(ArrayList<Dice> dice) {
        int sum = 0;

        for (Dice die : dice) {
            sum += die.getValue();
        }
        return sum;
    }



    public String listOfDiceToDiceString(ArrayList<Dice> diceList) {
        String diceString = "";
        for (Dice die : diceList) {
            if (die.getValue() == 1) {
                diceString = diceString + "  ⚀  |";
            } else if (die.getValue() == 2) {
                diceString = diceString + "  ⚁  |";
            } else if (die.getValue() == 3) {
                diceString = diceString + "  ⚂  |";
            } else if (die.getValue() == 4) {
                diceString = diceString + "  ⚃  |";
            } else if (die.getValue() == 5) {
                diceString = diceString + "  ⚄  |";
            } else if (die.getValue() == 6) {
                diceString = diceString + "  ⚅  |";
            }
        }
        return diceString;

    }


    public String getCurrentDiceString(ArrayList<Dice> rolledDice, ArrayList<Dice> savedDice) {
        String currentDiceString = "";
        String spacerString = "\n|------------------------------------------|\n";
        String numberString = "|            |  1  |  2  |  3  |  4  |  5  |";


        String rolledDiceString = "|Rolled Dice |" + listOfDiceToDiceString(rolledDice);
        for (int i = 0; i < 5 - rolledDice.size(); i++) {
            rolledDiceString = rolledDiceString + "     |";
        }

        String savedDiceString = "| Saved Dice |";

        for (int i = 0; i < rolledDice.size(); i++) {
            savedDiceString = savedDiceString + "     |";
        }

        savedDiceString = savedDiceString + listOfDiceToDiceString(savedDice);
        currentDiceString = spacerString + numberString + spacerString + rolledDiceString + spacerString + savedDiceString + spacerString;

        return currentDiceString;
    }


    public String allOptions() {
        return "Type 'save' to save rolled dice.\n" +
                "Type 'return' to return saved dice to be rolled again.\n" +
                "Type 'roll' to roll again.\n" +
                "Type 'scorecard' to see scorecard.\n" +
                "Type 'mark' to mark a score on you scorecard.\n" +
                "Type 'exit' to walk away.";
    }


    public void roll() {
        try {
            rolledDice = yahtzeePlayer.playerRollDice(5 - savedDice.size());
            console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
            console.println(getCurrentDiceString(rolledDice, savedDice));
            input = console.getStringInput(allOptions());

        } catch (YahtzeePlayer.TooManyRollsException tooManyRollsException) {
            input = console.getStringInput("\nYou have already rolled 3 times.  Type 'mark' to mark your scorecard.");
        }
    }

    public void saveDice() {
        input = console.getStringInput("Type the locations of the dice you want to save.\n" +
                "(Ex: '123' to save first three dice)");
        try {
            for (Dice die : yahtzeePlayer.saveDice(rolledDice, input)) {
                savedDice.add(die);
            }
            console.println("Dice saved.");
            console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
            console.println(getCurrentDiceString(rolledDice, savedDice));
            input = console.getStringInput(allOptions());

        } catch (IndexOutOfBoundsException i) {
            input = console.getStringInput("Invalid input.  " + allOptions());
        }
    }


    public void returnDice() {
        input = console.getStringInput("Type the locations of the dice you want to return.\n" +
                "(Ex: '345' to return last three dice)");
        try {
            for (Dice die : yahtzeePlayer.returnDice(savedDice, input)) {
                rolledDice.add(die);
            }
            console.println("Dice returned");
            console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
            console.println(getCurrentDiceString(rolledDice, savedDice));
            input = console.getStringInput(allOptions());

        } catch (ArrayIndexOutOfBoundsException aioobEx) {
            input = console.getStringInput("Invalid input.  " + allOptions());
        }
    }

    public void showScorecard() {
        console.println(getScoreCardString());
        input = console.getStringInput("Type 'back' to go back.  Type 'mark' to mark scorecard");
        if (input.toLowerCase().equals("back")) {
            console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
            console.println(getCurrentDiceString(rolledDice, savedDice));
            input = console.getStringInput(allOptions());
        }
    }


    public String categoryString() {
        return "Enter the category you want to mark on your scorecard.\n" +
                " 'aces', 'twos', 'threes', fours', 'fives', 'sixes'\n" +
                "     'three of a kind', 'four of a kind', 'full house',\n" +
                "'small straight', large straight', 'yahtzee', 'chance'\n" +
                "           Enter 'back' to go back.\n";
    }

    public void back() {
        console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
        console.println(getCurrentDiceString(rolledDice, savedDice));
        input = console.getStringInput(allOptions());
    }


    public void markScore() {
        if (YahtzeeScorecard.isValidCategory(input)) {
            if (scoreCard.get(input.toLowerCase()) != null) {
                console.println("You already have a score for %s", input);
            } else {
                YahtzeeScorecard.markScoreCard(input.toLowerCase(), getAllDice(rolledDice, savedDice));
                scoreCard.put("total score", YahtzeeScorecard.getTotalScore());
                rolledDice.removeAll(rolledDice);
                savedDice.removeAll(savedDice);
                console.println(getScoreCardString());
                yahtzeePlayer.setRollNumber(0);

                checkScorecardComplete();
            }
        } else {
            input = console.getStringInput("Invalid category. Enter 'mark' to try again.");
        }
    }


    public void checkScorecardComplete() {
        if (YahtzeeScorecard.scorecardComplete()) {
            console.println("Thank you for playing Yahtzee!  Your final score is %d.", YahtzeeScorecard.getTotalScore());
            playing = false;
            input = "back";
        } else {
            input = console.getStringInput("Type 'roll' to start your next turn.");
        }
    }


    public void invalidInputCheck(){
        if (!(input.toLowerCase().equals("roll") || input.toLowerCase().equals("save") || input.toLowerCase().equals("return") ||
                input.toLowerCase().equals("scorecard") || input.toLowerCase().equals("mark") || input.toLowerCase().equals("back")
                || input.toLowerCase().equals("exit"))) {

            input = console.getStringInput("Invalid input.  " + allOptions());
        }
    }


    public void checkForBack(){
        input = console.getStringInput(categoryString());
        if (input.toLowerCase().equals("back")) {
            back();


        } else {
            markScore();
        }
    }

    public void playGame(){
        try {
            YahtzeeAction.valueOf(input.toUpperCase()).perform(this);
        }catch(IllegalArgumentException iae) {
            invalidInputCheck();
        }
    }
}


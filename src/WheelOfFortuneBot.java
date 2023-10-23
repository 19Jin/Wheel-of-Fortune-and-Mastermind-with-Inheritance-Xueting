import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WheelOfFortuneBot {

    public String phrase;
    public StringBuilder hiddenPhrase;
    public StringBuilder previousPhrase;
    private int letterTableIndex = 0;

    public static void main(String[] args) {

        String url = "https://www.usfca.edu/arts-sciences/programs/graduate/computer-science-bridge";

        WebAnalyze analyzer = new WebAnalyze();
        List<Map.Entry<Character, Integer>> sortedLetterFrequency = analyzer.freqWebText(url);
        //create an empty String to store the letters based on the occurrences from high to low
        String letterTable = "";
        //Put the results of alphabetic frequency sorting into letterTable
        for (Map.Entry<Character, Integer> entry : sortedLetterFrequency) {
            letterTable += entry.getKey();
        }
        System.out.println("Frequency of The Letter Based On CSB Website: ");
        System.out.println(letterTable);

        //a not that intelligent bot player:
        String notIntelligentLetter = "abcdefghijklmnopqrstuvwxyz";

        //create a game
        WheelOfFortuneBot guessGame = new WheelOfFortuneBot();

        //input the number of guesses allowed
        System.out.println("Enter the number of guesses allowed: ");
        Scanner numberKeyboard = new Scanner(System.in);
        int num = numberKeyboard.nextInt();

        //user plays guessing game
        guessGame.randomPhrase();
        System.out.println("The phrase is: " + guessGame.phrase);
        guessGame.generateHiddenPhrase();
        System.out.println(guessGame.hiddenPhrase);

        //counting:
        int numOfGuess = 0;
        int numOfIncorrect = 0;
        int numOfLast;

        //Bot plays the game:
        while(numOfGuess < num){

            char c = guessGame.getGuess(letterTable);//input variable: notIntelligentLetter if the bot not intelligent
            System.out.println("Bot entered:  " + c);

            boolean flag = guessGame.processGuess(c);

            if(!flag){
                numOfIncorrect++;
            }else if(flag && !guessGame.previousPhrase.toString().equals(guessGame.phrase)){
                ;
            }else if(flag && guessGame.previousPhrase.toString().equals(guessGame.phrase)){
                break;
            }

            numOfGuess++;
            numOfLast = num - numOfGuess;
            System.out.println("Number of Previous Misses: " + numOfIncorrect);
            System.out.println("You Still Have " + numOfLast + " Chances");
        }

        if(numOfGuess >= num){
            System.out.println("You Failed");
            System.out.println("You Missed " + numOfIncorrect + " Times");
        }else{
            System.out.println("Congratulations! You Win!!");
        }

    }


    public void randomPhrase (){
        // Get a random phrase from the list
        List<String> phraseList=null;
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Get a random phrase from the list
        Random rand = new Random();
        int r= rand.nextInt(3); // gets 0, 1, or 2
        String phrase = phraseList.get(r);
        this.phrase = phrase;
    }

    public void generateHiddenPhrase(){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < phrase.length(); i++) {
            if (Character.isLetter(phrase.charAt(i))) {
                sb.append('*');
            } else {
                sb.append(phrase.charAt(i));
            }
        }
        this.hiddenPhrase = sb;
        this.previousPhrase = sb;
    }


    //get the input from user and return it
    public char getGuess(String letterTable){
        // Check if the index is within bounds
        if (letterTableIndex < letterTable.length()) {
            // Get the character at the current index
            char res = letterTable.charAt(letterTableIndex);
            // Increment the index for the next call
            letterTableIndex++;
            return res;
        } else {
            // If we've reached the end of letterTable, return a default character or handle it accordingly
            System.out.println("No more letters in letterTable.");
            return ' '; // You can change this to a different default character
        }
    }


    //processGuess: return whether a letter matches and modifies the hidden part if there is a match
    public boolean processGuess(Character user){

        boolean res = false;

        if(!Character.isLetter(user)){
            System.out.println("You Should Guess The Letter.");
        }else if(!phrase.toLowerCase().contains(String.valueOf(user))){
            System.out.println("Wrong Letter");
        }else if(phrase.toLowerCase().contains(String.valueOf(user))){
            for(int idx = 0; idx < phrase.length(); idx++){
                if(phrase.toLowerCase().charAt(idx) == user){
                    previousPhrase.setCharAt(idx, phrase.charAt(idx));
                }
            }
            System.out.println("Correct Letter!");
            System.out.println(previousPhrase);
            res = true;
        }

        return res;
    }

}

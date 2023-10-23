import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class WheelOfFortuneObject {
    public String phrase;
    public StringBuilder hiddenPhrase;
    public StringBuilder previousPhrase;


    public static void main(String[] args){
        //create a game
        WheelOfFortuneObject guessGame = new WheelOfFortuneObject();

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

        //games:
        while(numOfGuess < num){

            char c = guessGame.getGuess();

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

    //returns the initial hidden phrase
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
    public char getGuess(){
        System.out.println("Enter a letter: ");
        Scanner keyboard = new Scanner(System.in);
        String userGuess = keyboard.nextLine().toLowerCase();
        char getGuess = userGuess.charAt(0);
        return getGuess;
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
            System.out.println(this.previousPhrase);
            res = true;
        }

        return res;
    }

}

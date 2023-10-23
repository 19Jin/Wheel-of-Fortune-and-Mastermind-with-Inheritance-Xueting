import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class WheelOfFortuneMethods {
    public static void main(String[] args) {

        //input the number of guesses allowed
        System.out.println("Enter the number of guesses allowed: ");
        Scanner numberKeyboard = new Scanner(System.in);
        int num = numberKeyboard.nextInt();

        //user plays guessing game
        Boolean match = true;
        String p = randomPhrase();
        System.out.println(p);
        StringBuilder hidden = generateHiddenPhrase(p);

        //counting:
        int numOfGuess = 0;
        int numOfIncorrect = 0;

        //games:
        while(numOfGuess < num){

            System.out.println("Enter a letter: ");
            char userGuess = getGuess();
            boolean flag = processGuess(userGuess, p, hidden);

            if(!flag){
                numOfIncorrect++;
            }else if(flag && !p.equals(hidden.toString())){
                System.out.println("Correct Letter");
            }else if(flag && p.equals(hidden.toString())){
                break;
            }
            numOfGuess++;
            int numOfLast = num - numOfGuess;
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

    //randomPhrase -- returns a single phrase randomly chosen from a list
    public static String randomPhrase(){
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
        return phrase;
    }

    //generateHiddenPhrase -- returns the initial hidden phrase
    public static StringBuilder generateHiddenPhrase(String phrase){
        // Generate the initial hidden phrase
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < phrase.length(); i++){
            if(Character.isLetter(phrase.charAt(i))){
                sb.append('*');
            }else{
                sb.append(phrase.charAt(i));
            }
        }
        System.out.println(sb);
        return sb;
    }

    //getGuess-- gets input from user and returns it.
    public static char getGuess(){
        //input the number of guesses allowed
        Scanner keyboard = new Scanner(System.in);
        String userGuess = keyboard.nextLine().toLowerCase();
        return userGuess.charAt(0);
    }

    // processGuess -- returns whether a letter matches,
    // and modifies the partially hidden phrase if there is a match.

    public static boolean processGuess(char userGuess, String phrase, StringBuilder hiddenPhrase){
        // check whether the guess letter matches
        // modifies the partially hidden phrase
        // return ture if matches, other false
        if(!phrase.toLowerCase().contains(String.valueOf(userGuess))){
            System.out.println("Wrong Letter");
            return false;
        }else if(!Character.isLetter(userGuess)){
            System.out.println("You Should Guess The Letter");
            return false;
        }
        else{
            for(int idx = 0; idx < phrase.length(); idx++){
                if(phrase.toLowerCase().charAt(idx) == userGuess){
                    hiddenPhrase.setCharAt(idx, phrase.charAt(idx));
                }
            }
            System.out.println(hiddenPhrase);
        }
        return true;
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BuyAVowel {
    public String phrase;
    public StringBuilder hiddenPhrase;
    public StringBuilder previousPhrase;
    public int money;
    public String vowel = "aeiou";
    private Set<Character> set;

    //constructor
    public BuyAVowel() {
        set = new HashSet<>();
    }

    public static void main(String[] args){
        //create a game
        BuyAVowel guessGame = new BuyAVowel();

        //input the number of guesses allowed
        System.out.println("Enter the number of guesses allowed: ");
        Scanner numberKeyboard = new Scanner(System.in);
        int num = numberKeyboard.nextInt();

        //set the number of money owned by user
        guessGame.setMoney();

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

            guessGame.buyVowel();

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
                    this.set.add(user);
                }
            }
            System.out.println("Correct Letter!");
            System.out.println(this.previousPhrase);
            res = true;
        }

        return res;
    }

    public void setMoney(){
        //input the number of money owned by user
        System.out.println("Enter the amount of user's money: ");
        Scanner moneyKeyboard = new Scanner(System.in);
        int m = moneyKeyboard.nextInt();
        this.money = m;
    }

    //whether to spend money but a vowel
    public void buyVowel(){
        System.out.println("Do you need to spend 100 bucks to buy a vowel?: Y or N");
        Scanner answerKeyboard = new Scanner(System.in);
        char answer = answerKeyboard.next().charAt(0);
        if(answer == 'Y' && money >0){
            if(!this.set.contains('e') && phrase.contains("e")){
                System.out.println("The vowel you bought is Letter e ");
                for(int idx = 0; idx < phrase.length(); idx++){
                    if(phrase.toLowerCase().charAt(idx) == 'e'){
                        previousPhrase.setCharAt(idx, phrase.charAt(idx));
                        this.set.add('e');
                    }
                }
            }else if(!this.set.contains('a') && phrase.contains("a")){
                System.out.println("The vowel you bought is Letter a ");
                for(int idx = 0; idx < phrase.length(); idx++){
                    if(phrase.toLowerCase().charAt(idx) == 'a'){
                        previousPhrase.setCharAt(idx, phrase.charAt(idx));
                        this.set.add('a');
                    }
                }
            }else if(!this.set.contains('i') && phrase.contains("i")){
                System.out.println("The vowel you bought is Letter i ");
                for(int idx = 0; idx < phrase.length(); idx++){
                    if(phrase.toLowerCase().charAt(idx) == 'i'){
                        previousPhrase.setCharAt(idx, phrase.charAt(idx));
                        this.set.add('i');
                    }
                }
            }else if(!this.set.contains('o') && phrase.contains("o")){
                System.out.println("The vowel you bought is Letter o ");
                for(int idx = 0; idx < phrase.length(); idx++){
                    if(phrase.toLowerCase().charAt(idx) == 'o'){
                        previousPhrase.setCharAt(idx, phrase.charAt(idx));
                        this.set.add('o');
                    }
                }
            }else if(!this.set.contains('u') && phrase.contains("u")){
                System.out.println("The vowel you bought is Letter u ");
                for(int idx = 0; idx < phrase.length(); idx++){
                    if(phrase.toLowerCase().charAt(idx) == 'u'){
                        previousPhrase.setCharAt(idx, phrase.charAt(idx));
                        this.set.add('u');
                    }
                }
            }

            money = money-100;

        }else if(money < 100){
            System.out.println("you don't have enough money");
        }else{
            return;
        }
    }

}

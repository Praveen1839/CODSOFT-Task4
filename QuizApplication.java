import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
class Question {
    private String question;
    private String[] options;
    private int correctOption;
    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }
    public String getQuestion() {
        return question;
    }
    public String[] getOptions() {
        return options;
    }
    public int getCorrectOption() {
        return correctOption;
    }
}
public class QuizApplication {
    private ArrayList<Question> questions;
    private int score;
    private ArrayList<String> results;
    private boolean timeUp;
    public QuizApplication() {
        questions = new ArrayList<>();
        score = 0;
        results = new ArrayList<>();
        loadQuestions();
    }
    private void loadQuestions() {
        questions.add(new Question("What is the capital of France?", 
                new String[]{"1. Berlin", "2. Paris", "3. Madrid", "4. Rome"}, 2));
        questions.add(new Question("Who wrote 'Hamlet'?", 
                new String[]{"1. Charles Dickens", "2. Mark Twain", "3. William Shakespeare", "4. Jane Austen"}, 3));
        questions.add(new Question("What is 2 + 2?", 
                new String[]{"1. 3", "2. 4", "3. 5", "4. 6"}, 2));
    }
    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Welcome to the Quiz Application ===");
        System.out.println("You have 10 seconds to answer each question.");
        System.out.println("Press Enter to start...");
        scanner.nextLine();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.getQuestion());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }
            timeUp = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeUp = true;
                    System.out.println("\nTime's up! Moving to the next question...");
                    results.add("Question " + (i + 1) + ": Incorrect (Time's Up)");
                    timer.cancel();
                }
            }, 10000);

            System.out.print("Your answer: ");
            String input = scanner.nextLine();
            timer.cancel();
            if (timeUp) continue;
            try {
                int choice = Integer.parseInt(input);
                if (choice == question.getCorrectOption()) {
                    System.out.println("Correct!");
                    score++;
                    results.add("Question " + (i + 1) + ": Correct");
                } else {
                    System.out.println("Incorrect!");
                    results.add("Question " + (i + 1) + ": Incorrect");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Moving to the next question...");
                results.add("Question " + (i + 1) + ": Incorrect (Invalid Input)");
            }
        }
        displayResults();
        scanner.close();
    }
    private void displayResults() {
        System.out.println("\n=== Quiz Results ===");
        System.out.println("Final Score: " + score + "/" + questions.size());
        for (String result : results) {
            System.out.println(result);
        }
    }
    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication();
        quiz.startQuiz();
    }
}
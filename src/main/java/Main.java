import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        
        try (Scanner scanner = new Scanner(System.in)) {
            while(true) {
                System.out.print("$ ");
                String input = scanner.nextLine();
                if(input.equals("exit 0")) {
                    break;
                }
                else if(input.split(" ")[0].equals("echo")) {
                    System.out.println(input.substring(input.indexOf(" ") + 1));
                }
                else System.out.println(input + ": command not found");
            }
        }
    }
}
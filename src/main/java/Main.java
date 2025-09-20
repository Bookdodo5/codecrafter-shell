import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        List<String> builtins = List.of("echo", "type", "exit");
        
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
                else if(input.split(" ")[0].equals("type")) {
                    String command = input.split(" ")[1];
                    if(builtins.contains(command)) {
                        System.out.println(command + " is a shell builtin");
                    }
                    else {
                        System.out.println(command + ": not found");
                    }
                }
                else {
                    System.out.println(input + ": command not found");
                }
            }
        }
    }
}

import java.util.Scanner;
import java.util.List;
import java.io.File;

public class Main {

    private static String checkExecutable(String command, String[] path) {
        for (String directory : path) {
            File file = new File(directory, command);
            if (file.exists() && !file.isDirectory() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    private static String type(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length < 2) {
            return "type: missing argument";
        }
        
        String command = parts[1];
        String PATH = System.getenv("PATH");
        String[] path = PATH.split(File.pathSeparator);

        List<String> builtins = List.of("echo", "type", "exit", "ls");

        if (builtins.contains(command)) {
            return command + " is a shell builtin";
        }
        
        String executablePath = checkExecutable(command, path);
        if (executablePath != null) {
            return command + " is " + executablePath;
        }
        
        return command + ": not found";
    }

    public static void main(String[] args) throws Exception {

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("$ ");
                String input = scanner.nextLine();
                if (input.equals("exit 0")) {
                    break;
                } else if (input.split(" ")[0].equals("echo")) {
                    System.out.println(input.substring(input.indexOf(" ") + 1));
                } else if (input.split(" ")[0].equals("type")) {
                    System.out.println(type(input));
                } else {
                    System.out.println(input + ": command not found");
                }
            }
        }
    }
}

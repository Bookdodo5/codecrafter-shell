
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

    private static String type(String command, String[] path) {
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
                String command = input.split(" ")[0];
                String argument = input.substring(input.indexOf(" ") + 1);

                String PATH = System.getenv("PATH");
                String[] path = PATH.split(File.pathSeparator);

                if (input.equals("exit 0")) {
                    break;
                } else if (command.equals("echo")) {
                    System.out.println(input.substring(input.indexOf(" ") + 1));
                } else if (command.equals("type")) {
                    System.out.println(type(argument, path));
                } else if (checkExecutable(command, path) != null) {
                    ProcessBuilder processBuilder = new ProcessBuilder(command, argument);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();
                    System.out.println(exitCode);
                }
                else {
                    System.out.println(input + ": command not found");
                }
            }
        }
    }
}

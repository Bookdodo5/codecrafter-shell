
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private static void run(String command, String argument) {
        try {
            String[] argumentParts = argument.split(" ");
            String[] arguments = new String[argumentParts.length + 1];
            arguments[0] = command;
            System.arraycopy(argumentParts, 0, arguments, 1, argumentParts.length);
            ProcessBuilder processBuilder = new ProcessBuilder(arguments);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error executing command: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
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
                    run(command, argument);
                } else {
                    System.out.println(input + ": command not found");
                }
            }
        }
    }
}

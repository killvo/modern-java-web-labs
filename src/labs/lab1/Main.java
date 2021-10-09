package labs.lab1;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // 16 варіант
    /*
    Знайти у заданій директорії всі текстові файли та переписати до інших файлів
    m останніх слів з кожного із n останніх рядків. Створені файли зберегти у
    новій директорії
    */

    private static final String DIR_WITH_RESULTS_PATH = "src/labs/lab1/results";
    private static final int THREAD_POOL_SIZE = 100;
    private static final int M = 4;
    private static final int N = 10;

    public static void main(String[] args) {
        makeResultsDirWhenNotExist();
        File sourceDirectory = getSourceDirectoryFile();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        Callable<Integer> task = new HandleFile(sourceDirectory, new File(DIR_WITH_RESULTS_PATH), executor, N, M);
        Future<Integer> results = executor.submit(task);
        try {
            System.out.println("Кількість оброблених файлів: " + results.get());
            executor.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void makeResultsDirWhenNotExist() {
        File dirWithResults = new File(DIR_WITH_RESULTS_PATH);
        if (!dirWithResults.exists()) {
            dirWithResults.mkdir();
        }
    }

    private static File getSourceDirectoryFile() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ConsoleOutputMessages.ENTER_PATH.getMessage());
        String dirName = scanner.nextLine();

        File sourceDirectory;
        while (true) {
            if (enteredPathIncorrect(dirName)) {
                System.out.println(ConsoleOutputMessages.INCORRECT_PATH.getMessage());
            } else {
                sourceDirectory = new File(dirName);
                if (sourceDirectory.exists()) {
                    break;
                }
                System.out.println(ConsoleOutputMessages.DIRECTORY_DOESNT_EXIST.getMessage());
            }
            dirName = scanner.nextLine();
        }

        return sourceDirectory;
    }

    private static boolean enteredPathIncorrect(String path) {
        Pattern pattern = Pattern.compile("^/|(/[\\w-_]+)+$");
        Matcher matcher = pattern.matcher(path);
        return !matcher.matches();
    }
}

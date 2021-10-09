package labs.lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HandleFile implements Callable<Integer> {
    private final File fileToHandle;
    private final File directoryWithResults;
    private final ExecutorService executor;
    private final int lastRowsAmount;
    private final int lastWordsAmount;

    public HandleFile(File fileToHandle, File directoryWithResults, ExecutorService executor,
                      int lastRowsAmount, int lastWordsAmount) {
        this.fileToHandle = fileToHandle;
        this.directoryWithResults = directoryWithResults;
        this.executor = executor;
        this.lastRowsAmount = lastRowsAmount;
        this.lastWordsAmount = lastWordsAmount;
    }

    @Override
    public Integer call() {
        int handledFilesCount = 0;
        List<Future<Integer>> tasks = new ArrayList<>();
        File[] files = fileToHandle.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                Callable<Integer> handleFile = new HandleFile(f, directoryWithResults, executor,
                                                                lastRowsAmount, lastWordsAmount);
                tasks.add(executor.submit(handleFile));
            } else {
                performHandle(f);
                handledFilesCount++;
            }
        }
        for (Future<Integer> task : tasks) {
            try {
                handledFilesCount += task.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return handledFilesCount;
    }

    private void performHandle(File file) {
        long startTime = System.nanoTime();
        List<String> allLines = new ArrayList<>();
        String finalString = "";
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNextLine()) {
                allLines.add(scanner.nextLine());
            }
            final int numberOfLinesInFile = allLines.size();
            int fromLine = numberOfLinesInFile < lastRowsAmount ? 0 : (numberOfLinesInFile - lastRowsAmount);
            for (int i = fromLine; i < numberOfLinesInFile; i++) {
                String line = allLines.get(i);
                finalString += getProcessedLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeFile(file.getName(), finalString);
        long endTime = System.nanoTime();
        printFileHandlingTime(file.getName(), startTime, endTime);
    }

    private String getProcessedLine(String line) {
        String[] words = line.split("\\s+");
        final int numberOfWordsInLine = words.length;
        String finalLine = "";
        int fromWord = numberOfWordsInLine < lastWordsAmount ? 0 : (numberOfWordsInLine - lastWordsAmount);
        for (int w = fromWord; w < numberOfWordsInLine; w++) {
            finalLine += words[w] + " ";
        }
        return finalLine + "\n";
    }

    private void writeFile(String fileToWriteName, String linesToWrite) {
        String fileToWritePath = directoryWithResults + "/" + fileToWriteName;
        File fileToWrite = new File(fileToWritePath);
        try (FileWriter fw = new FileWriter(fileToWrite, true)) {
            fileToWrite.createNewFile();
            fw.write(linesToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFileHandlingTime(String fileName, long startTime, long endTime) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " ; Обробив файл: " + fileName +
                            " за " + (endTime - startTime) / 1_000 + " мікросекунд");
    }
}

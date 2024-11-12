import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SortComparison {

    public static void main(String[] args) {
        String[] fileNames = {"src/aleatorio_100.csv", "src/aleatorio_1000.csv", "src/aleatorio_10000.csv",
                "src/crescente_100.csv", "src/crescente_1000.csv", "src/crescente_10000.csv",
                "src/decrescente_100.csv", "src/decrescente_1000.csv", "src/decrescente_10000.csv"};

        for (String fileName : fileNames) {
            System.out.println("Lendo arquivo: " + fileName);
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    System.out.println("Arquivo não encontrado: " + fileName);
                    continue;
                }

                int[] data = readCSV(fileName);

                int[] bubbleData = data.clone();
                long startBubble = System.nanoTime();
                bubbleSort(bubbleData);
                long endBubble = System.nanoTime();
                System.out.println("Bubble Sort - " + fileName + ": " + (endBubble - startBubble) + " ns");

                int[] insertionData = data.clone();
                long startInsertion = System.nanoTime();
                insertionSort(insertionData);
                long endInsertion = System.nanoTime();
                System.out.println("Insertion Sort - " + fileName + ": " + (endInsertion - startInsertion) + " ns");

                int[] quickData = data.clone();
                long startQuick = System.nanoTime();
                quickSort(quickData, 0, quickData.length - 1);
                long endQuick = System.nanoTime();
                System.out.println("Quick Sort - " + fileName + ": " + (endQuick - startQuick) + " ns");

            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo " + fileName + ": " + e.getMessage());
            }
        }
    }

    public static int[] readCSV(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        ArrayList<Integer> data = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            try {

                data.add(Integer.parseInt(line.trim()));
            } catch (NumberFormatException e) {

                System.out.println("Linha ignorada: " + line);
            }
        }
        br.close();
        return data.stream().mapToInt(i -> i).toArray();
    }


    public static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }


    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }


    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }


    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}

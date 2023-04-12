import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        long start = System.nanoTime();

        // Dosya adını belirle
        String filename = "/Users/ulascancicek/Desktop/koordinantlar/tsp_85900_1";

        // Dosyayı aç
        File file = new File(filename);

        // Scanner nesnesi oluştur
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Veri sayısını belirle
        int n = 0;
        String line = scanner.nextLine(); // ilk satırı oku ve atla
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            n++;
        }

        // İki boyutlu dizi oluştur ve verileri okuyup diziye at
        scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        float[][] coordinates = new float[n][2];
        line = scanner.nextLine(); // ilk satırı oku ve atla
        int i = 0;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] parts = line.split(" ");
            float x = 0.0f;
            float y = 0.0f;
            try {
                x = Float.parseFloat(parts[0]);
                y = Float.parseFloat(parts[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            coordinates[i][0] = x;
            coordinates[i][1] = y;
            i++;
        }

        // TSP için en kısa yol bul
        int[] path = tsp(coordinates);

        // Sonucu ekrana yazdır
        System.out.print("En kısa yol: ");
        for (int j = 0; j < path.length; j++) {
            System.out.print(path[j] + " ");
        }

        // adım ve mesafe bilgisi
        shortestPath(coordinates, path);

        // Projenin çalışma süresini hesapla ve yazdır
        long end = System.nanoTime();
        long timeElapsed = end - start;

        if (timeElapsed < 1000000000) {
            System.out.println('\n' + "Proje " + timeElapsed / 1000000 + " milisaniye sürdü.");
        } else {
            System.out.println('\n' + "Proje " + timeElapsed / 1000000000 + " saniye sürdü.");
        }

    }

    // TSP için en kısa yol hesaplama metodu
    public static int[] tsp(float[][] coordinates) {
        int n = coordinates.length;
        int[] path = new int[n];
        boolean[] visited = new boolean[n];
        visited[0] = true;
        path[0] = 0;
        for (int i = 1; i < n; i++) {
            int nearest = -1;
            float minDistance = Float.MAX_VALUE;
            for (int j = 1; j < n; j++) {
                if (!visited[j]) {
                    float distance = distance(coordinates[path[i - 1]], coordinates[j]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearest = j;
                    }
                }
            }
            path[i] = nearest;
            visited[nearest] = true;
        }
        return path;
    }

    // İki nokta arasındaki uzaklığı hesaplayan metot
    public static float distance(float[] p1, float[] p2) {
        float xDiff = p1[0] - p2[0];
        float yDiff = p1[1] - p2[1];
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public static void shortestPath(float[][] coordinates, int[] path) {
        float totalDistance = 0.0f;
        for (int i = 1; i < path.length; i++) {
            totalDistance += distance(coordinates[path[i-1]], coordinates[path[i]]);
        }
        totalDistance += distance(coordinates[path[0]], coordinates[path[path.length-1]]);

        int numSteps = path.length - 1;
        System.out.println("\nEn kısa yol " + numSteps + " adımda bulundu.");
        System.out.println("Gezilen toplam mesafe: " + totalDistance);
    }



}

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] arr = generate();
        SplayTree tree = new SplayTree();
        double i = insert(tree, arr);
        double s = search(tree, arr);
        double d = delete(tree, arr);
        System.out.println("Insert: time = " + i + " nano, count = " + tree.getCountInsert());
        System.out.println("Search: time = " + s + " nano, count = " + tree.getCountSearch());
        System.out.println("Delete: time = " + d + " nano, count = " + tree.getCountDelete());
        tree.reset();
    }

    public static int[] generate() {
        Random rand = new Random();
        int[] randomArray = new int[10000];
        for (int i = 0; i < 10000; i++) {
            randomArray[i] = rand.nextInt(100000);
        }
        return randomArray;
    }

    public static double insert(SplayTree tree, int[] arr) {
        long totalInsertTime = 0;

        for (int num : arr) {
            long startTime = System.nanoTime();
            tree.insert(num);
            long endTime = System.nanoTime();
            totalInsertTime += (endTime - startTime);
        }
        double avgInsertTime = (double) totalInsertTime / arr.length;
        return avgInsertTime;
    }

    public static double search(SplayTree tree, int[] arr) {
        Random rand = new Random();
        long totalSearchTime = 0;

        for (int i = 0; i < 100; i++) {
            int randomIndex = rand.nextInt(10000);
            int searchValue = arr[randomIndex];

            long startTime = System.nanoTime();
            tree.search(searchValue);
            long endTime = System.nanoTime();

            totalSearchTime += (endTime - startTime);
        }

        double avgSearchTime = (double) totalSearchTime / 100;
        return avgSearchTime;
    }

    public static double delete(SplayTree tree, int[] arr) {
        Random rand = new Random();
        long totalDeleteTime = 0;

        for (int i = 0; i < 1000; i++) {
            int randomIndex = rand.nextInt(10000);
            int deleteValue = arr[randomIndex];

            long startTime = System.nanoTime();
            tree.delete(deleteValue);
            long endTime = System.nanoTime();

            totalDeleteTime += (endTime - startTime);
        }

        double avgDeleteTime = (double) totalDeleteTime / 1000;
        return avgDeleteTime;
    }
}

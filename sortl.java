package sortl;
import java.util.*;
import java.io.*;

/**
 * This class provides an implementation of the merge sort algorithm for sorting
 * a list of strings, as well as functionality for reading and processing files.
 * It can read from files, sort the data using merge sort, and optionally print the 
 * time taken to perform the sort.
 */
public class sortl {

    // Global ArrayList to hold the strings to be sorted
    static ArrayList<String> arr = new ArrayList<String>();

    /**
     * Merges two sorted subarrays into a single sorted subarray.
     * The subarrays are divided from index 'l' to index 'r'.
     * 
     * @param l the leftmost index of the subarray
     * @param r the rightmost index of the subarray
     */
    public static void merge(int l, int r) {
        
        // Create a new ArrayList to store the merged result
        ArrayList<String> newarr = new ArrayList<String>();
        
        // Copy all elements from the global array 'arr' to 'newarr'
        for(String x : arr) {
            newarr.add(x);
        }
        
        // Initialize variables for merging
        int j = (l + r) / 2 + 1; // Start of the right subarray
        int k = l; // Start of the merged array

        // Merge the two sorted halves
        for(int i = l; i <= (l + r) / 2; i++) {
            // Compare elements from left and right subarrays
            while(j <= r && arr.get(j).compareTo(arr.get(i)) < 0) {
                newarr.set(k, arr.get(j));
                j++;
                k++;
            }
            newarr.set(k, arr.get(i));
            k++;
        }

        // Update the global array with the sorted elements
        arr = newarr;
    }

    /**
     * Sorts the array using the merge sort algorithm.
     * It recursively divides the array into smaller subarrays and sorts them.
     * 
     * @param l the leftmost index of the array/subarray
     * @param r the rightmost index of the array/subarray
     */
    public static void sort(int l, int r) {
        
        // Base case: if the subarray has only one element, it's already sorted
        if(l == r) {
            return;
        }
        
        // Find the midpoint of the array/subarray
        int m = (l + r) / 2;

        // Recursively sort the left and right subarrays
        sort(l, m);
        sort(m + 1, r);

        // Merge the sorted subarrays
        merge(l, r);
    }

    /**
     * Reads data from a file and adds each line to a list of strings.
     * 
     * @param filename the name of the file to be read
     */
    public static void FileReader(String filename) {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader;

        try {
            // Open the file for reading
            reader = new BufferedReader(new FileReader(filename));
            String line;
            
            // Read each line and add it to the list
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any I/O errors
        }
    }

    /**
     * The main method serves as the entry point of the program. 
     * It processes command-line arguments, reads files, sorts the contents,
     * and optionally times the sorting operation.
     * 
     * @param args command-line arguments for specifying file names and timing options
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        
        String filename = null;
        boolean timing = false;

        // Parse command-line arguments
        for (String arg : args) {
            if (arg.equals("-o")) {
                timing = true;
                // Timing toggle
            }
            if (arg.startsWith("-f")) {
                filename = arg.substring(2);
                System.out.println("Reading from " + filename);
                processFile(filename, timing); // Process the specified file
            } else {
                // If no filename is provided, process default files
                processFile("bestCase.txt", timing);
                processFile("worstCase.txt", timing);
            }
        }

        // If no arguments are passed, process default files
        if (args.length == 0) {
            processFile("bestCase.txt", timing);
            processFile("worstCase.txt", timing);
        }
    }

    /**
     * Reads a file, sorts its contents using merge sort, and optionally prints the
     * time taken to sort. Finally, it prints the sorted contents to the console.
     * 
     * @param filename the name of the file to be processed
     * @param timing   flag to indicate whether to time the sorting operation
     * @throws FileNotFoundException if the file is not found
     */
    public static void processFile(String filename, boolean timing) throws FileNotFoundException {
        
        BufferedReader reader;
        arr.clear(); // Got some really funny test results before realizing I needed this line

        // Read file and copy into arr
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                arr.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If timing is enabled, record the start time
        long startTime = 0;
        if (timing) {
            startTime = System.nanoTime();
        }

        // Sort the array using merge sort
        sort(0, arr.size() - 1);

        // If timing is enabled, calculate and print the elapsed time
        if (timing) {
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
            System.out.println("Elapsed time for Array sorting: " + elapsedTimeInSeconds + " seconds");
        }

        // Print the sorted list
        System.out.println("Sorted list for Array:");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}
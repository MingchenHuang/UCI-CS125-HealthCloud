package com.example.healthcloud;
import java.util.*;

public class Recommandation {

    public static Vector<Vector<Integer>> result ;
    public static Vector<Vector<Integer>> qualified;
    public static HashMap<Integer, String> mapping;
    public static Vector<String> options_set;
    public static int weight_value;
    public static int sum_value;

    public Recommandation(int Weight, int Sum) {
        result = new Vector<>();
        qualified = new Vector<>();
        mapping = new HashMap<>(11);
        options_set = new Vector<>();
        weight_value = Weight;
        sum_value = Sum;
        first();
    }

    public Vector<String> get_options_set()
    {
        return options_set;
    }


    //start function
    public void first() {
        // write your code here
        result.clear();
        qualified.clear();

        //int sum = 1700;
        //int sum = sum_value;
        //int[] data = new int[] {246, 267, 352, 359, 492, 490, 773, 562, 494, 422, 492};
        //int weight = 65;
        //int weight = weight_value;


        int[] data = initialization(weight_value);
        //get all combination
        get_combination(data);
        //System.out.println("Size of result vector: " + result.size() + "\n\n\n");

        //get at least 4 different options or cases
        check_qualified_numb(sum_value);


        //TODO: Return the name of exercises from this main function when moving to android studio....
        //get string options
        form_new_options();

        // options_set is the final cases.


    }

    public void form_new_options() {
        options_set.clear();
        String exercise = "";
        for (int i = 0; i < qualified.size(); i++) {
            exercise = "";
            //System.out.println("Mapping: \n" + mapping);
            for (int j = 0; j < qualified.get(i).size(); j++) {
                if (j == 0)
                {
                    exercise =  mapping.get( qualified.get(i).get(j) );
                }
                else {
                    exercise = exercise + ", " + mapping.get(qualified.get(i).get(j));
                }
                //option.add( mapping.get( qualified.get(i).get(j) ) );
                //System.out.println( "loop option: " + qualified.get(i).get(j) +"    " + mapping.get( qualified.get(i).get(j) ) + "\n\n\n");
                //break;
            }
            options_set.add(exercise);

            // break;
        }

    }

    public int[] initialization(int weight) {
        int[] small = new int[]{198, 215, 283, 289, 393, 390, 624, 454, 399, 340, 397};
        int[] middle = new int[]{246, 267, 352, 359, 492, 490, 773, 562, 488, 422, 499};
        int[] large = new int[]{294, 319, 420, 428, 587, 580, 923, 671, 588, 503, 589};
        double weight_lb = weight * 2.20462 - 125;
        mapping.clear();
        if (weight_lb <= 15) {
            mapping.put(198, "Golf");
            mapping.put(215, "Walking");
            mapping.put(283, "Kayaking");
            mapping.put(289, "Softball/Baseball");
            mapping.put(393, "Swimming");
            mapping.put(390, "Tennis");
            mapping.put(624, "Running");
            mapping.put(454, "Bicycle");
            mapping.put(399, "Football");
            mapping.put(340, "Basketball");
            mapping.put(397, "Soccer");

            return small;
        } else if (weight_lb <= 45) {
            mapping.put(246, "Golf");
            mapping.put(267, "Walking");
            mapping.put(352, "Kayaking");
            mapping.put(359, "Softball/Baseball");
            mapping.put(492, "Swimming");
            mapping.put(490, "Tennis");
            mapping.put(773, "Running");
            mapping.put(562, "Bicycle");
            mapping.put(488, "Football");
            mapping.put(422, "Basketball");
            mapping.put(499, "Soccer");

            return middle;
        } else  //>= 171
        {
            mapping.put(294, "Golf");
            mapping.put(319, "Walking");
            mapping.put(420, "Kayaking");
            mapping.put(428, "Softball/Baseball");
            mapping.put(587, "Swimming");
            mapping.put(580, "Tennis");
            mapping.put(923, "Running");
            mapping.put(671, "Bicycle");
            mapping.put(588, "Football");
            mapping.put(503, "Basketball");
            mapping.put(589, "Soccer");

            return large;
        }
    }

    //make sure the qualified vector has equal and more than 4 different options
    public void check_qualified_numb(int sum) {
        int i = 0;
        do {
            qualified.clear();
            get_qualified(sum, i * 1);

            if (qualified.size() >= 4) {
                break;
            }
            i++;

        } while (true);
    }

    //get valid vector sum that within a range
    public void get_qualified(int sum, int range) {
        for (int i = 0; i < result.size(); i++) {
            int total = sum_of_vector(result.get(i));
            if ((total > (sum - range)) && (total < (sum + range))) {
                Collections.sort(result.get(i));
                if (!qualified.contains(result.get(i))) {
                    qualified.add(result.get(i));
                    //System.out.println("get_qualified function: " + result.get(i));
                }
            }
        }
    }

    //sum each vector element from result
    public int sum_of_vector(Vector<Integer> container) {
        int sum = 0;
        for (int i = 0; i < container.size(); i++) {
            sum += container.get(i);
        }
        return sum;
    }


    public void get_combination(int data[]) {
        for (int i = 1; i < data.length; i++) {
            printCombination(data, data.length, i);
        }
    }


    public void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r) {
        // Current combination is ready to be printed, print it
        if (index == r) {
            Vector<Integer> container = new Vector<>();

            for (int j = 0; j < r; j++)
                container.add(data[j]);
            //System.out.print(data[j]+" ");
            //System.out.println("");
            result.add(container);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr[i];
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    public void printCombination(int arr[], int n, int r) {
        // A temporary array to store all combination one by one
        int data[] = new int[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n - 1, 0, r);
    }


    public int[] remove_first_element(int data[]) {
        int size = data.length - 1;
        int[] new_data = new int[size];

        for (int i = 0; i < size; i++) {
            new_data[i] = data[i + 1];
        }
        return new_data;

    }

    public int min(int data[]) {
        int m = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] < m) {
                m = data[i];
            }
        }
        return m;
    }
}

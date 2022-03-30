package hello;

public class Calculator {
    public int sumPositiveValues(int... numbers) {
        int sum = 0;
        for (int number : numbers) {
            if(number>0){
                sum += number;
            }
        }
        return sum;
    }
}

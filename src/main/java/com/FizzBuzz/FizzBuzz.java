package com.FizzBuzz;

import java.util.Arrays;
import java.util.List;

public class FizzBuzz {
    private List<Integer> baseNums = Arrays.asList(3, 5);
    private List<String> baseTags = Arrays.asList("Fizz", "Buzz");

    private boolean buildN(int num, int checkNum, String tag, StringBuilder builder){
        if (num % checkNum == 0) {
            builder.append(tag);
            return true;
        }
        return false;
    }

    public String call(int num) {
        StringBuilder builder = new StringBuilder();

        boolean hit = false;
        for(int i=0;i<baseNums.size();i++){
            if(buildN(num, baseNums.get(i), baseTags.get(i), builder)) hit = true;
        }

        if(!hit) builder.append(num);

        return builder.toString();
    }
}

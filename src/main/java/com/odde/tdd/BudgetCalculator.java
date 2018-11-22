package com.odde.tdd;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetCalculator {
    BudgetRepo br;
    public BudgetCalculator(BudgetRepo b){
        br = b;
    }

    public long calcBudget(LocalDate startDate, LocalDate endDate){
        long total = 0;
        for(Budget budget: br.findAll()){
            LocalDate monthStart = budget.getMonth().atDay(1);
            LocalDate monthEnd = budget.getMonth().atEndOfMonth();
            LocalDate startOfBudget = (startDate.isAfter(monthStart))? startDate:monthStart;
            LocalDate endOfBudget = (endDate.isBefore(monthEnd))? endDate:monthEnd;
            if(!startOfBudget.isAfter(endOfBudget)){
                int dc = endOfBudget.getDayOfMonth() - startOfBudget.getDayOfMonth() + 1;
                total += budget.getAmount() * dc / budget.getMonth().lengthOfMonth();
            }
        }
        return total;
    }
}

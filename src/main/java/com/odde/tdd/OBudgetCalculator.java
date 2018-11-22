package com.odde.tdd;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OBudgetCalculator {
    private Map<YearMonth, Budget> repoMap = new HashMap<>();

    public OBudgetCalculator(BudgetRepo repo){
        for(Budget budget: repo.findAll()){
            repoMap.put(budget.getMonth(), budget);
        }
    }

    private List<YearMonth> getMonthEnds(LocalDate startDate, LocalDate endDate){
        List<YearMonth> ret = new ArrayList<>();
        ret.add(getYM(startDate, 1));
        ret.add(getYM(endDate, -1));

        return ret;
    }

    public long calcBudget(LocalDate startDate, LocalDate endDate){

        List<YearMonth> ends = getMonthEnds(startDate, endDate);

        long baseAmount = 0;
        for(YearMonth i = ends.get(0); i.compareTo(ends.get(1)) <=0;i = i.plusMonths(1)){
            baseAmount += getWholeMonthAmount(i);
        }

        long startAmount = getEndAmount(startDate, START);
        long endAmount = getEndAmount(endDate, END);

        long sameMonthBias = 0;
        if(getYM(startDate).compareTo(getYM(endDate)) == 0){
            sameMonthBias = - getWholeMonthAmount(startDate);
        }

        return startAmount + baseAmount + endAmount + sameMonthBias;
    }

    private YearMonth getYM(LocalDate date) {
        return getYM(date, 0);
    }

    private YearMonth getYM(LocalDate date, int offset) {
        int year = date.getYear();
        int month = date.getMonthValue() + offset;
        while(month < 1){
            month += 12;
            year -= 1;
        }
        while(month > 12){
            month -= 12;
            year += 1;
        }
        return YearMonth.of(year, month);
    }

    private Budget searchBudget(YearMonth targetDate){
        return repoMap.getOrDefault(targetDate, Budget.ZERO);
    }

    private long getWholeMonthAmount(YearMonth targetDate){
        Budget curBudget = searchBudget(targetDate);
        return curBudget.getAmount();
    }

    private long getWholeMonthAmount(LocalDate targetDate){
        return getWholeMonthAmount(getYM(targetDate));
    }

    long getEndAmount(LocalDate targetDate, String tag){
        Budget budget = searchBudget(getYM(targetDate));

        int d = targetDate.getDayOfMonth();
        int all = targetDate.lengthOfMonth();

        return calcMonthValue(tag, d, all, budget.getAmount());
    }

    final static String START = "start";
    final static String END = "end";
    private long calcMonthValue(String tag, int dOfMonth, int all, long amount) {
        switch(tag) {
            case START:
                    return amount * (all - dOfMonth + 1) / all;
            case END:
                    return amount * dOfMonth / all;
        }
        return 0;
    }
}

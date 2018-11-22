package com.odde.tdd;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetTest {
    private BudgetRepo repo;
    private BudgetRepo emptyRepo;
    Map<YearMonth, Budget> repoMap;

    DateTimeFormatter formatter;

    private BudgetCalculator calculator;
    private BudgetCalculator emptyCalculator;

    @Before
    public void init(){
        repo = mock(BudgetRepo.class);
        when(repo.findAll()).thenReturn(Arrays.asList(
                new Budget(YearMonth.of(2018, 1), 31)
                ,new Budget(YearMonth.of(2017, 12), 155)
                ,new Budget(YearMonth.of(2018, 2), 280)
                ,new Budget(YearMonth.of(2018, 3), 31)
                ,new Budget(YearMonth.of(2018, 4), 60)
                ,new Budget(YearMonth.of(2018, 5), 93)
                ,new Budget(YearMonth.of(2018, 6), 120)
                )
        );

        repoMap = new HashMap<>();
        for(Budget budget: repo.findAll()){
            repoMap.put(budget.getMonth(), budget);
        }
        calculator = new BudgetCalculator(repo);

        emptyRepo = mock(BudgetRepo.class);
        when(emptyRepo.findAll()).thenReturn(new ArrayList<>());

        emptyCalculator = new BudgetCalculator(emptyRepo);

        formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    }

    private LocalDate getDate(String dateStr){
        //convert String to LocalDate
        return LocalDate.parse(dateStr, formatter);
    }

    @Test
    public void emptyTest() {
        LocalDate start = getDate("19/12/2017");
        LocalDate end = getDate("19/12/2017");

        long result = emptyCalculator.calcBudget(start, end);

        assertEquals(0, result);
    }

    @Test
    public void lossTest() {
    }

    @Test
    public void oneDayTest() {
        LocalDate start = getDate("19/12/2017");
        LocalDate end = getDate("19/12/2017");

        long result = calculator.calcBudget(start, end);

        assertEquals(5, result);
    }

    @Test
    public void simpleTest(){
        LocalDate start = getDate("31/01/2018");
        LocalDate end = getDate("1/03/2018");

        long result = calculator.calcBudget(start, end);

        assertEquals(282, result);
    }

    @Test
    public void crossYearTest(){
        LocalDate start = getDate("30/12/2017");
        LocalDate end = getDate("22/01/2018");
        long result = calculator.calcBudget(start, end);

        assertEquals(32, result);
    }

    @Test
    public void withinOneMonthTest(){
        LocalDate start = getDate("21/01/2018");
        LocalDate end = getDate("22/01/2018");
        long result = calculator.calcBudget(start, end);

        assertEquals(2, result);

        start = getDate("15/01/2018");
        end = getDate("25/01/2018");
        result = calculator.calcBudget(start, end);
        assertEquals(11, result);
    }

//    @Test
//    public void endMonthValueTest(){
//        LocalDate date = getDate("1/01/2018");
//        assertEquals(1, calculator.getEndAmount(date, BudgetCalculator.END));
//        date = getDate("12/03/2018");
//        assertEquals(12, calculator.getEndAmount(date, BudgetCalculator.END));
//    }
//
//    @Test
//    public void startMonthValueTest(){
//        LocalDate date = getDate("31/01/2018");
//        assertEquals(1, calculator.getEndAmount(date, BudgetCalculator.START));
//        date = getDate("20/03/2018");
//        assertEquals(12, calculator.getEndAmount(date, BudgetCalculator.START));
//    }
}

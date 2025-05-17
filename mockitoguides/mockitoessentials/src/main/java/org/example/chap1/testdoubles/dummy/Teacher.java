package org.example.chap1.testdoubles.dummy;

import java.math.BigDecimal;
import java.util.List;

/*
 Create a Teacher class to generate a student's grades
*/
public class Teacher {

    private BigDecimal calculatePercentage(BigDecimal aggregate, int numberOfSubject){
        BigDecimal percent = new BigDecimal(
                aggregate.doubleValue() / numberOfSubject
        );
        return percent;
    }

    public Grades generateGrade(List<Marks> marksList){
        BigDecimal aggregate = BigDecimal.ZERO;
        for(Marks marks : marksList){
            aggregate = aggregate.add(marks.getMarks());
        }

        BigDecimal percentage =  calculatePercentage(aggregate, marksList.size());

        if(percentage.compareTo(new BigDecimal("90.00")) >= 0){
            return Grades.Excellent;
        } else if(percentage.compareTo(new BigDecimal("75.00")) >= 0){
            return Grades.VeryGood;
        } else if(percentage.compareTo(new BigDecimal("60.00")) >= 0){
            return Grades.Good;
        } else if(percentage.compareTo(new BigDecimal("40.00")) >= 0){
            return Grades.Average;
        }
        return Grades.Poor;

    }
}

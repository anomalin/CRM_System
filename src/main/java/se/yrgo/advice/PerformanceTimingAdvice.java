package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;

public class PerformanceTimingAdvice {

    public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable {
        long startTime =System.nanoTime();

        try {
            Object value= method.proceed();
            return value;
        }finally {
            //after
            double endTime= System.nanoTime();
            double timeTaken = endTime - startTime;
            System.out.println("The method " +
                    method.getSignature().getName() + " from the class " + method.getSignature().getDeclaringTypeName() +  " took " + timeTaken
                    /1000000);
        }
    }
}

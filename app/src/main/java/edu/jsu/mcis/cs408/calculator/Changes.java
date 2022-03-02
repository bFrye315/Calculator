package edu.jsu.mcis.cs408.calculator;

import java.math.BigDecimal;

public class Changes {
    private BigDecimal num1;
    private BigDecimal num2;
    private BigDecimal answer;
    private final BigDecimal ONEHUNDRED = new BigDecimal("100");
    private final BigDecimal ZERO = new BigDecimal(0);
    private boolean add, sub, multi, div, error;
    private final double ONE_HALF = 0.5;
    private final String ERROR_MSG = "Error";

    public Changes(){

    }

    public void Add(String firstNum){
        ClearOps();
        add = true;
        if(num1 == null){
            this.num1 = new BigDecimal(firstNum);
        }
    }

    public void Subtract(String firstNum){
        ClearOps();
        sub = true;
        if(num1 == null){
            this.num1 = new BigDecimal(firstNum);
        }
    }

    public void Multiply(String firstNum){
        ClearOps();
        multi = true;
        if(num1 == null){
            this.num1 = new BigDecimal(firstNum);
        }
    }

    public void Divide(String firstNum){
        ClearOps();
        div = true;
        if(num1 == null){
            this.num1 = new BigDecimal(firstNum);
        }
    }

    public String SquareRoot(String firstNum){
        if (firstNum.charAt(0) == '-'){
            error = true;
        }
        else{
            this.num1 = new BigDecimal(firstNum);

            answer = BigDecimal.valueOf(Math.pow(num1.doubleValue(), ONE_HALF));
            num1 = answer;
        }

        if (error){
            Clear();
            error = false;
            return ERROR_MSG;
        }
        else{
            return String.valueOf(answer);
        }
    }

    private void ClearOps(){
        add = false;
        sub = false;
        multi = false;
        div = false;
    }

    public void Clear(){
        num1 = null;
        num2 = null;
        answer = null;
        ClearOps();
    }

    public String Equals(String secondNum){

        this.num2 = new BigDecimal(secondNum);

        if (add){
            answer = num1.add(num2);
            num1 = answer;
        }
        else if (sub){
            answer = num1.subtract(num2);
            num1 = answer;
        }
        else if (multi){
            answer = num1.multiply(num2);
            num1 = answer;
        }
        else if (div){
            if(num2.equals(ZERO)){
                error = true;
            }
            else{
                answer = num1.divide(num2);
            }
            num1 = answer;
        }

        if (error){
            Clear();
            error = false;
            return ERROR_MSG;
        }
        else{
            return String.valueOf(answer);
        }
    }

    public String Percentages(String secondNum){

        this.num2 = new BigDecimal(secondNum);
        BigDecimal perc = new BigDecimal(String.valueOf(num1.multiply(num2.divide(ONEHUNDRED))));

        if (add){
            answer = num1.add(perc);
            add = false;
            num1 = answer;
        }
        else if (sub){
            answer = num1.subtract(perc);
            sub = false;
            num1 = answer;
        }
        else if (multi){
            answer = num1.multiply(perc);
            multi = false;
            num1 = answer;
        }
        else if (div){
            if(perc.equals(ZERO)){
                error = true;
            }
            else{
                answer = num1.divide(perc);
            }
            div = false;
            num1 = answer;
        }

        if (error){
            Clear();
            return ERROR_MSG;
        }
        else{
            return String.valueOf(answer);
        }
    }

}

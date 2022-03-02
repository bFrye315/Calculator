package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private StringBuilder equation = new StringBuilder(28);
    Changes changes = new Changes();
    private String answer = "";
    final private String DIV_BY_ZERO ="Cannot Divide by Zero";
    final private String NEG_SQRT = "Cannot get Square Root of Negative Number";
    final private String MULTI_DEC = "Cannot have more than one Decimal";
    boolean maxCap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        CalculatorClickHandler click = new CalculatorClickHandler();
        ConstraintLayout layout = binding.calLayout;

        for(int i = 0; i < layout.getChildCount(); i++){
            View child = layout.getChildAt(i);
            if (child instanceof Button){
                ((Button)child).setOnClickListener(click);

            }
        }
    }


    class CalculatorClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            TextView t = (TextView) binding.answerView;
            String tag = ((Button)view).getTag().toString();

            /** add in check for length
                check length against certain amount (27 since option for making negative need to be there)
                if over, toast saying num is too big
                if not, allow number to be entered

             */
            if (equation.length() == 24) {
                maxCap = true;
            }

            switch (view.getId()){
                // add, subtract, multiply, divide buttons
                case R.id.btnAdd:
                    changes.Add(String.valueOf(equation));
                    equation.setLength(0);
                    maxCap = false;
                    break;
                case R.id.btnSub:
                    changes.Subtract(String.valueOf(equation));
                    equation.setLength(0);
                    maxCap = false;
                    break;
                case R.id.btnMultip:
                    changes.Multiply(String.valueOf(equation));
                    equation.setLength(0);
                    maxCap = false;
                    break;
                case R.id.btnDiv:
                    changes.Divide(String.valueOf(equation));
                    equation.setLength(0);
                    maxCap = false;
                    break;

                // positive or negative number
                case R.id.btnPosNeg:

                    if(equation.length() == 0){
                        equation.append(tag);
                        t.setText(equation);
                    }
                    else if(equation.charAt(0) == tag.charAt(0)){
                        equation.deleteCharAt(0);
                        t.setText(equation);
                    }
                    else{
                        equation.insert(0,tag);
                        t.setText(equation);
                    }
                    break;

                // decimal handling
                case R.id.btnDec:
                    boolean decExist = false;
                    for(int i = 0; i < equation.length(); i++){
                        if(equation.charAt(i) == tag.charAt(0)){
                            decExist = true;
                            break;
                        }
                    }
                    if (decExist){
                        Toast errorToast = Toast.makeText(binding.getRoot().getContext(), MULTI_DEC, Toast.LENGTH_SHORT);
                        errorToast.show();
                    }
                    else{
                        equation.append(tag);
                        t.setText(equation);
                    }
                    break;

                //square root, equals, and percent (an operation is preformed)
                case R.id.btnSqrt:
                    answer = changes.SquareRoot(String.valueOf(equation));
                    if (answer.equals("Error")){
                        Toast errorToast = Toast.makeText(binding.getRoot().getContext(), NEG_SQRT, Toast.LENGTH_SHORT);
                        errorToast.show();
                        equation.setLength(0);
                    }
                    t.setText(answer);
                    maxCap = false;
                    break;
                case R.id.btnEquals:
                    answer = changes.Equals(String.valueOf(equation));
                    if (answer.equals("Error")){
                        Toast errorToast = Toast.makeText(binding.getRoot().getContext(), DIV_BY_ZERO, Toast.LENGTH_SHORT);
                        errorToast.show();
                        equation.setLength(0);
                    }
                    t.setText(answer);
                    maxCap = false;
                    break;
                case R.id.btnPercent:
                    answer = changes.Percentages(String.valueOf(equation));
                    if (answer.equals("Error")){
                        Toast errorToast = Toast.makeText(binding.getRoot().getContext(), DIV_BY_ZERO, Toast.LENGTH_SHORT);
                        errorToast.show();
                        equation.setLength(0);
                    }
                    t.setText(answer);
                    maxCap = false;
                    break;

                // clear screen and all values
                case R.id.btnClear:
                    equation.setLength(0);
                    maxCap = false;
                    changes.Clear();
                    t.setText(equation);
                    break;

                // basic numbers input (0-9)
                default:
                    if(maxCap){
                        Toast errorToast = Toast.makeText(binding.getRoot().getContext(), "Max character limit reached", Toast.LENGTH_SHORT);
                        errorToast.show();
                        maxCap = false;
                    }
                    else{
                        equation.append(tag);
                        t.setText(equation);
                    }
                    break;
            }
        }
    }
}
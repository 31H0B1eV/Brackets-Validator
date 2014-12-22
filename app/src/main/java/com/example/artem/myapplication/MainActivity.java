package com.example.artem.myapplication;

import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;


public class MainActivity extends ActionBarActivity {

    TextView txt;
    Button btn;
    ExtractEditText etxt;

    boolean result = false; // for testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);
        btn = (Button)findViewById(R.id.btn);
        etxt   = (ExtractEditText)findViewById(R.id.eText);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        String test = etxt.getText().toString();
                        BracketsValidator validator = new BracketsValidator();
                        boolean correct = validator.validate(test);

                        txt.setText("Brackets are placed " + (correct ? "" : "in") + "correct");
                        result = correct;
                    }
                });
    }

    public class BracketsValidator {

        private Stack<Character> stack = new Stack<Character>();

        private boolean isOpeningBracket(char bracket) {
            return "({[".indexOf(bracket) != -1;
        }

        private boolean isClosingBracket(char bracket) {
            return ")}]".indexOf(bracket) != -1;
        }

        private boolean isPair(char opening, char closing) {
            return opening == '(' && closing == ')' || opening == '['
                    && closing == ']' || opening == '{' && closing == '}';
        }

        public boolean validate(String input) {
            for (char c : input.toCharArray()) {
                if (isClosingBracket(c) && stack.isEmpty()) {
                    return false;
                }
                if (isOpeningBracket(c)) {
                    stack.push(c);
                }
                if (isClosingBracket(c)) {
                    if (isPair(stack.peek(), c)) {
                        stack.pop();
                    }
                }
            }
            return stack.isEmpty();
        }

    }
}

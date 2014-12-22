package com.example.artem.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;


public class MainActivity extends ActionBarActivity {

    TextView txt;
    Button btn;
    EditText etxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);
        btn = (Button)findViewById(R.id.btn);
        etxt   = (EditText)findViewById(R.id.eText);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        String test = etxt.getText().toString();
                        BracketsValidator validator = new BracketsValidator();
                        boolean correct = validator.validate(test);

                        txt.setText("Brackets are placed " + (correct ? "" : "in") + "correct");
                    }
                });
    }

    /**
     * release keyboard (test works with default) after button pressed
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
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

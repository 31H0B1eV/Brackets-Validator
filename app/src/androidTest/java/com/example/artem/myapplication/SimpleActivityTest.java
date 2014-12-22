package com.example.artem.myapplication;

import android.app.Application;
import android.inputmethodservice.ExtractEditText;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class SimpleActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public SimpleActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testButtonIsPressed() throws Exception {

        solo.enterText((ExtractEditText) solo.getView(R.id.eText), "(){}[]");
        solo.clickOnButton("test");

        solo.sleep(1000);

        solo.clearEditText((ExtractEditText) solo.getView(R.id.eText));
        solo.enterText((ExtractEditText) solo.getView(R.id.eText), "wtf()] / {(2 + 2)}");
        solo.clickOnButton("test");

        solo.sleep(1000);

        solo.clearEditText((ExtractEditText) solo.getView(R.id.eText));
        solo.enterText((ExtractEditText) solo.getView(R.id.eText), "a + (42 - b) * [wtf()] / {(2 + 2)}");
        solo.clickOnButton("test");

        solo.sleep(1000);

        solo.clearEditText((ExtractEditText) solo.getView(R.id.eText));
        solo.enterText((ExtractEditText) solo.getView(R.id.eText), "( } []");
        solo.clickOnButton("test");

        solo.sleep(1000);

        solo.clearEditText((ExtractEditText) solo.getView(R.id.eText));
        solo.enterText((ExtractEditText) solo.getView(R.id.eText), "a + '('42) * [wtf'(')] / {(2 + 2)}");
        solo.clickOnButton("test");

        solo.sleep(1000);
    }
}
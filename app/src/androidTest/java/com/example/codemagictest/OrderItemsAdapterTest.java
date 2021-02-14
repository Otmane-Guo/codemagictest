package com.example.codemagictest;

import android.app.Activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class OrderItemsAdapterTest {

    private static String FAKE_LOGIN = "jaafar";
    private static String FAKE_PASSWORD = "123";


    @Rule public ActivityScenarioRule<HomeActivity> activityScenarioRule = new ActivityScenarioRule<HomeActivity>(HomeActivity.class);
    @Test
    public void test_click_home_fragment(){
        onView(withId(R.id.home))
                .perform(click());
        onView(withId(R.id.myhomepage))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_click_cart_before_login_fragment(){
        onView(withId(R.id.cart))
                .perform(click());
        onView(withId(R.id.loginpage))
                .check(matches(isDisplayed()));
    }
    @Test
    public void test_click_cart_after_login_fragment(){
        onView(withId(R.id.cart))
                .perform(click());
        onView(withId(R.id.login))
                .perform(typeText(FAKE_LOGIN), closeSoftKeyboard())
                ;
        onView(withId(R.id.password))
                .perform(typeText(FAKE_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click())
                //.perform(swipeUp())
                //.check(matches(withText("SHOP BY CATEGORY")))
        ;

        //onView(withId(R.id.myhomepage))
        //        .check(matches(isDisplayed()));
       // Activity activity =





    }

    @Test
    public void test_click_orders_before_login_fragment(){
        onView(withId(R.id.MyOrders))
                .perform(click());
        onView(withId(R.id.loginpage))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_click_orders_after_login_fragment(){
        onView(withId(R.id.MyOrders))
                .perform(click());
        onView(withId(R.id.login))
                .perform(typeText(FAKE_LOGIN), closeSoftKeyboard())
        ;
        onView(withId(R.id.password))
                .perform(typeText(FAKE_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click())
        //.perform(swipeUp())
        //.check(matches(withText("SHOP BY CATEGORY")))
        ;
    }


    @Test
    public void test_click_profile_before_login_fragment(){
        onView(withId(R.id.user))
                .perform(click());
        onView(withId(R.id.loginpage))
                .check(matches(isDisplayed()));
    }

}
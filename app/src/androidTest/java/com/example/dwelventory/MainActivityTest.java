package com.example.dwelventory;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.example.dwelventory.R;
import com.example.dwelventory.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainScenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddItem() {
        // add two items
        onView(withText(containsString("Total Cost"))).check(matches(isDisplayed()));

        // Item item1 = new Item("Billy", date1, "Pygmy Goat", "Caramel w/ Black
        // Markings",serial,200, comment, photos);
        // Item item2 = new Item("Jinora", date2, "Pygmy Goat", "Caramel w/ Black
        // Markings", 200);
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Jacob"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("1-2-3456"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Pygmy Goat"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Caramel w/ Black Markings"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("200"));
        Espresso.pressBack();
        onView(withText("Confirm")).check(matches(isDisplayed()));
        onView(withText("Confirm")).perform(click());
        onView(withText("Total Cost"));
        // asserts that item was added
        onView(withText("Jacob")).check(matches(isDisplayed()));
        //
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Billy"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("10-21-9876"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Pygmy Goat"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Caramel w/ Black Markings"));

        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("200"));
        Espresso.pressBack();
        onView(withText("Confirm")).perform(click());
    }
    @Test
    public void testEditItem(){
        ActivityScenario<MainActivity> scenario = mainScenario.getScenario();
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Ozzie"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("09-11-2076"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Pygmy Goat"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Caramel w/ Black Markings"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("200"));
        Espresso.pressBack();
        onView(withText("Confirm")).perform(click());
        onView(withText(containsString("Ozzie"))).check(matches(isDisplayed()));
        scenario.onActivity(activity -> {
            ListView itemList = activity.findViewById(R.id.item_list);
            itemList.performItemClick(itemList.getAdapter().getView(0,null,null),0,itemList.getAdapter().getItemId(0));
        });
        onView(withId(R.id.item_name_button)).perform(ViewActions.replaceText(""));
        onView(withId(R.id.item_name_button)).perform(ViewActions.clearText());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Oz"));
        Espresso.pressBack();
        onView(withText("Confirm")).perform(click());
        // accidentally types Ozg not Oz
        onView(withText(containsString("Oz"))).check(matches(isDisplayed()));
    }
    @Test
    public void testDeleteItem(){
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("George"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("01-04-1999"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Pygmy Goat"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Caramel w/ Black Markings"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("200"));
        Espresso.pressBack();
        onView(withText("Confirm")).perform(click());
        onView(withText("George")).check(matches(isDisplayed()));
        onView(withText("George")).perform(longClick());
        onView(withId(R.id.checkbox)).perform(click());
        onView(withId(R.id.deletebtn)).perform(click());
        // delete
        onView(withText("George")).check(doesNotExist());
    }

    @Test
    public void testTotalCost(){
        ActivityScenario<MainActivity> scenario = mainScenario.getScenario();
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Ozzie"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("09-11-2076"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Pygmy Goat"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Caramel w/ Black Markings"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("200"));
        Espresso.pressBack();
        onView(withText("Confirm")).perform(click());
        onView(withText(containsString("Total Cost: $200"))).check(matches(isDisplayed()));
        scenario.onActivity(activity -> {
            ListView itemList = activity.findViewById(R.id.item_list);
            itemList.performItemClick(itemList.getAdapter().getView(0,null,null),0,itemList.getAdapter().getItemId(0));
        });
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.replaceText(""));
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.clearText());
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("250"));
        Espresso.pressBack();
        onView(withText("Confirm")).perform(click());
        // accidentally types a 6 at the end
        onView(withText(containsString("Total Cost: $250"))).check(matches(isDisplayed()));
    }

    @Test
    public void testMakeFilter() {
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Tom"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("03-21-2050"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Poodle"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Black with brown eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("35"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());
        onView(withText("Total Cost")).check(matches(isDisplayed()));



        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Odell"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("05-12-2420"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Husky"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("White with blue eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("100"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());

        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Johnson"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("10-25-2038"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Husky"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("White with blue eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("150"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());

        onView(withId(R.id.filter_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Make"))).perform(click());
        onView(withId(R.id.filter_make_etext)).perform(ViewActions.typeText("Poodle"));
        onView(withId(R.id.filter_make_donebtn)).perform(click());
        onView(withText("Tom")).check(matches(isDisplayed()));
        onView(withText("Odell")).check(doesNotExist());
        onView(withText("Johnson")).check(doesNotExist());
        onView(withText("35")).check(matches(isDisplayed()));
    }

    @Test
    public void testDateFilter() {
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Tom"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("03-21-2050"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Poodle"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Black with brown eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("35"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());


        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Odell"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("05-12-2420"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Husky"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("White with blue eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("100"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());

        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Johnson"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("10-25-2038"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Husky"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("White with blue eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("150"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());

        onView(withId(R.id.filter_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Date"))).perform(click());
        onView(withId(R.id.filter_date_cal1)).perform(ViewActions.typeText("01/01/2038"));
        onView(withId(R.id.filter_date_cal2)).perform(ViewActions.typeText("01/01/2039"));
        onView(withId(R.id.filter_date_donebtn)).perform(click());
        onView(withText("Johnson")).check(matches(isDisplayed()));
        onView(withText("Odell")).check(doesNotExist());
        onView(withText("Tom")).check(doesNotExist());
        onView(withText("150")).check(matches(isDisplayed()));
    }

    @Test
    public void testKeywordFilter(){
        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Tom"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("03-21-2050"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Poodle"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("Black with brown eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("35"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());


        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Odell"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("05-12-2420"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Husky"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("White with blue eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("100"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());

        onView(withId(R.id.add_item_button)).perform(click());
        onView(withId(R.id.item_name_button)).perform(ViewActions.typeText("Johnson"));
        onView(withId(R.id.date_button)).perform(ViewActions.typeText("10-25-2038"));
        onView(withId(R.id.make_button)).perform(ViewActions.typeText("Husky"));
        onView(withId(R.id.model_button)).perform(ViewActions.typeText("White with blue eyes"));
        Espresso.pressBack();
        onView(withId(R.id.estimated_val_button)).perform(ViewActions.typeText("150"));
        Espresso.pressBack();

        onView(withText("Confirm")).perform(click());

        onView(withId(R.id.filter_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Description Word"))).perform(click());
        onView(withId(R.id.filter_kwords_etext)).perform(ViewActions.typeText("Odell"));
        onView(withId(R.id.filter_kwords_donebtn)).perform(click());
        onView(withText("Odell")).check(matches(isDisplayed()));
        onView(withText("Tom")).check(doesNotExist());
        onView(withText("Johnson")).check(doesNotExist());
        onView(withText("100")).check(matches(isDisplayed()));
    }
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testSelectionMode() {
        onData(anything())
                .inAdapterView(withId(R.id.item_list))
                .atPosition(0)
                .perform(longClick());

        onView(withText("Selected Items : 0")).check(matches(isDisplayed()));

        onView(withId(R.id.selectAll)).perform(click());

        onView(withText("Selected Items : 2")).check(matches(isDisplayed()));

        onView(withId(R.id.deletebtn)).perform(click());

        onView(withText("Selected Items : 0")).check(matches(isDisplayed()));

        onView(withId(R.id.closebtn)).perform(click());

    }
}

package pl.dariuszbacinski.stackoverflow;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<SearchActivity> searchActivityActivityTestRule = new ActivityTestRule<SearchActivity>(SearchActivity.class);

    @Test
    public void alwaysPass() throws Exception {
        assertTrue(true);
    }
}
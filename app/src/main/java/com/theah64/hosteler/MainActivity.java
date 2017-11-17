package com.theah64.hosteler;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.theah64.hosteler.database.FoodHistories;
import com.theah64.hosteler.models.FoodHistory;
import com.theah64.hosteler.utils.DateUtils;

import java.util.Date;

public class MainActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FoodHistories foodHistories = FoodHistories.getInstance(this);
        final LayoutInflater inflater = LayoutInflater.from(this);

        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setArguments(args);
        caldroidFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onLongClickDate(Date date, View view) {
                //Ask additional charge and it's description
                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.Additional_charge)
                        .customView()
            }

            @Override
            public void onSelectDate(Date date, View view) {

                final String clickedDate = DateUtils.formatWithddMMyyyy(date);
                final FoodHistory foodHistory = foodHistories.get(FoodHistories.COLUMN_DATE, clickedDate);

                new MaterialDialog.Builder(MainActivity.this)
                        .title(DateUtils.getReadableDateFormat(date))
                        .items(R.array.food_types)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                /**
                                 * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected check box to actually be selected
                                 * (or the newly unselected check box to be unchecked).
                                 * See the limited multi choice dialog example in the sample project for details.
                                 **/
                                return true;
                            }
                        })
                        .positiveText(R.string.DONE)
                        .show();

                System.out.println(foodHistory);

            }
        });

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.flCalendar, caldroidFragment);
        t.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem miSettings = menu.findItem(R.id.miSettings);
        final MenuItem miPaymentHistory = menu.findItem(R.id.miPaymentHistory);
        miSettings.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_cog).colorRes(android.R.color.white).sizeDp(22));
        miPaymentHistory.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_history).colorRes(android.R.color.white).sizeDp(22));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miSettings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

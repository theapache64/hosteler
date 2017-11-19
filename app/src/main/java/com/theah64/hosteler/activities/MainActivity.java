package com.theah64.hosteler.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.theah64.hosteler.R;
import com.theah64.hosteler.database.FoodHistories;
import com.theah64.hosteler.models.FoodHistory;
import com.theah64.hosteler.utils.DateUtils;
import com.theah64.hosteler.widgets.ValidTextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FoodHistories foodHistoriesTable = FoodHistories.getInstance(this);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);

        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        caldroidFragment.setMaxDate(new Date());

        //Setting background colors for dates
        final List<FoodHistory> foodHistories = foodHistoriesTable.getAll();
        for (FoodHistory foodHistory : foodHistories) {
            setDateBackground(caldroidFragment, foodHistory);
        }

        caldroidFragment.setArguments(args);
        caldroidFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onLongClickDate(final Date date, View view) {
                //Ask additional charge and it's description

                @SuppressLint("InflateParams") final View adChargeLayout = inflater.inflate(R.layout.additional_charge_layout, null, false);
                final ValidTextInputLayout vtilAdditionalCharge = adChargeLayout.findViewById(R.id.vtilAdditionalCharge);
                final ValidTextInputLayout vtilDescription = (ValidTextInputLayout) adChargeLayout.findViewById(R.id.vtilDescription);

                //checking if there's data available
                final String sDate = DateUtils.formatWithddMMyyyy(date);
                final FoodHistory[] foodHistory = {foodHistoriesTable.get(FoodHistories.COLUMN_DATE, sDate)};

                if (foodHistory[0] != null) {
                    vtilAdditionalCharge.setText(String.valueOf(foodHistory[0].getAdditionalCharge()));
                    vtilDescription.setText(foodHistory[0].getDescription());
                }

                new MaterialDialog.Builder(MainActivity.this)
                        .title(DateUtils.getReadableDateFormat(date))
                        .customView(adChargeLayout, true)
                        .positiveText(R.string.ADD)
                        .autoDismiss(false)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    if (!vtilAdditionalCharge.isMatch()) {
                                        return;
                                    }

                                    //Submit additional charge here
                                    final int additionalCharge = Integer.parseInt(vtilAdditionalCharge.getString());
                                    final String c = vtilDescription.getString();


                                    if (foodHistory[0] == null) {
                                        foodHistory[0] = new FoodHistory(null, sDate, vtilDescription.getString(), 0, 0, 0, 0, additionalCharge, null);
                                        foodHistoriesTable.add(foodHistory[0]);
                                    } else {
                                        foodHistory[0].setAdditionalCharge(additionalCharge);
                                        foodHistory[0].setDescription(vtilDescription.getString());
                                        foodHistoriesTable.update(foodHistory[0]);
                                    }


                                    updatePrice();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .build()
                        .show();
            }

            @Override
            public void onSelectDate(Date date, View view) {

                final String clickedDate = DateUtils.formatWithddMMyyyy(date);
                FoodHistory foodHistory = foodHistoriesTable.get(FoodHistories.COLUMN_DATE, clickedDate);

                final List<Integer> selectedIndices = new ArrayList<>();

                if (foodHistory == null) {
                    foodHistory = new FoodHistory(null, clickedDate, null, 0, 0, 0, 0, 0, null);
                }

                //Convert to list
                final List<String> foodTypeList = new ArrayList<String>();
                foodTypeList.addAll(Arrays.asList(getResources().getStringArray(R.array.food_types)));

                if (foodHistory.getBreakfast() > 0) {
                    selectedIndices.add(foodTypeList.indexOf(getString(R.string.Breakfast)));
                }

                if (foodHistory.getDinner() > 0) {
                    selectedIndices.add(foodTypeList.indexOf(getString(R.string.Dinner)));
                }

                if (foodHistory.getGuestBreakfast() > 0) {
                    selectedIndices.add(foodTypeList.indexOf(getString(R.string.Guest_Breakfast)));
                }

                if (foodHistory.getGuestDinner() > 0) {
                    selectedIndices.add(foodTypeList.indexOf(getString(R.string.Guest_Dinner)));
                }


                final FoodHistory finalFoodHistory = foodHistory;
                new MaterialDialog.Builder(MainActivity.this)
                        .title(DateUtils.getReadableDateFormat(date) + (foodHistory.getAdditionalCharge() > 0 ? "*" : ""))
                        .items(R.array.food_types)
                        .itemsCallbackMultiChoice(selectedIndices.toArray(new Integer[selectedIndices.size()]), new MaterialDialog.ListCallbackMultiChoice() {
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
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    if (dialog.getSelectedIndices() != null) {

                                        final int breakFastCharge = Integer.parseInt(pref.getString(getString(R.string.breakfast_charge), getString(R.string.default_breakfast_cost)));
                                        final int dinnerCharge = Integer.parseInt(pref.getString(getString(R.string.dinner_charge), getString(R.string.default_dinner_cost)));
                                        final int totalHostelers = Integer.parseInt(pref.getString(getString(R.string.total_hostelers), getString(R.string.default_total_hostelers)));
                                        final int guestBreakfastCharge = breakFastCharge / totalHostelers;
                                        final int guestDinnerCharge = dinnerCharge / totalHostelers;

                                        List<Integer> selectedIndices = Arrays.asList(dialog.getSelectedIndices());

                                        //Looping through all 4 indices
                                        for (int i = 0; i < 4; i++) {

                                            if (selectedIndices.contains(i)) {
                                                if (i == foodTypeList.indexOf(getString(R.string.Breakfast))) {
                                                    finalFoodHistory.setBreakfast(breakFastCharge);
                                                } else if (i == foodTypeList.indexOf(getString(R.string.Dinner))) {
                                                    finalFoodHistory.setDinner(dinnerCharge);
                                                } else if (i == foodTypeList.indexOf(getString(R.string.Guest_Breakfast))) {
                                                    finalFoodHistory.setGuestBreakfast(guestBreakfastCharge);
                                                } else if (i == foodTypeList.indexOf(getString(R.string.Guest_Dinner))) {
                                                    finalFoodHistory.setGuestDinner(guestDinnerCharge);
                                                }
                                            } else {
                                                if (i == foodTypeList.indexOf(getString(R.string.Breakfast))) {
                                                    finalFoodHistory.setBreakfast(0);
                                                } else if (i == foodTypeList.indexOf(getString(R.string.Dinner))) {
                                                    finalFoodHistory.setDinner(0);
                                                } else if (i == foodTypeList.indexOf(getString(R.string.Guest_Breakfast))) {
                                                    finalFoodHistory.setGuestBreakfast(0);
                                                } else if (i == foodTypeList.indexOf(getString(R.string.Guest_Dinner))) {
                                                    finalFoodHistory.setGuestDinner(0);
                                                }
                                            }
                                        }

                                        if (finalFoodHistory.getId() == null) {
                                            //insert
                                            foodHistoriesTable.add(finalFoodHistory);
                                        } else {
                                            //update
                                            foodHistoriesTable.update(finalFoodHistory);
                                        }

                                        //Update calendar background
                                        setDateBackground(caldroidFragment, finalFoodHistory);
                                        caldroidFragment.refreshView();

                                        updatePrice();
                                    }

                                }
                            }
                        })
                        .show();

                System.out.println(foodHistory);

            }
        });

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.flCalendar, caldroidFragment);
        t.commit();

    }

    private void setDateBackground(CaldroidFragment caldroidFragment, FoodHistory foodHistory) {

        final Date date = DateUtils.parseWithddMMyyyy(foodHistory.getDate());

        if (foodHistory.getBreakfast() > 0 || foodHistory.getDinner() > 0) {
            int drawableId;

            if (foodHistory.getBreakfast() > 0 && foodHistory.getDinner() == 0) {
                drawableId = R.drawable.breakfast_only;
            } else if (foodHistory.getDinner() > 0 && foodHistory.getBreakfast() == 0) {
                drawableId = R.drawable.dinner_only;
            } else {
                drawableId = R.drawable.both;
            }

            caldroidFragment.setTextColorForDate(R.color.white, date);
            caldroidFragment.setBackgroundDrawableForDate(ContextCompat.getDrawable(this, drawableId), date);
        } else {
            caldroidFragment.setTextColorForDate(R.color.grey_800, date);
            caldroidFragment.clearBackgroundDrawableForDate(date);
        }
    }

    private void updatePrice() {
        //TODO: Update total price here
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

        switch (id) {
            case R.id.miSettings:
                startActivity(new Intent(this, SettingsPrefActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }

    public int getInteger(@IntegerRes int id) {
        return getResources().getInteger(id);
    }
}

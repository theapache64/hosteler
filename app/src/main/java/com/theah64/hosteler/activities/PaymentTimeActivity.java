package com.theah64.hosteler.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.theah64.hosteler.R;
import com.theah64.hosteler.database.FoodHistories;
import com.theah64.hosteler.models.Bill;
import com.theah64.hosteler.models.FoodHistory;
import com.theah64.hosteler.widgets.ValidTextInputLayout;

import butterknife.BindView;

public class PaymentTimeActivity extends BaseAppCompatActivity {

    //Generated with ButterLayout (http://github.com/theapache64/butterLayout): Sun Nov 19 09:36:37 UTC 2017
    @BindView(R.id.tvBreakfastCount)
    TextView tvBreakfastCount;

    @BindView(R.id.tvTotalBreakfastCost)
    IconTextView tvTotalBreakfastCost;

    @BindView(R.id.tvDinnerCount)
    TextView tvDinnerCount;

    @BindView(R.id.tvTotalDinnerCost)
    IconTextView tvTotalDinnerCost;

    @BindView(R.id.tvGuestBreakfastCount)
    TextView tvGuestBreakfastCount;

    @BindView(R.id.tvTotalGuestBreakfastCost)
    IconTextView tvTotalGuestBreakfastCost;

    @BindView(R.id.tvGuestDinnerCount)
    TextView tvGuestDinnerCount;

    @BindView(R.id.tvTotalGuestDinnerCost)
    IconTextView tvTotalGuestDinnerCost;

    @BindView(R.id.tvTotalAdditionalCharge)
    IconTextView tvTotalAdditionalCharge;

    @BindView(R.id.tvPendingAmount)
    IconTextView tvPendingAmount;

    @BindView(R.id.tvAdvanceAmount)
    IconTextView tvAdvanceAmount;

    @BindView(R.id.tvGrandTotal)
    IconTextView tvGrandTotal;

    @BindView(R.id.vtilPaymentAmount)
    ValidTextInputLayout vtilPaymentAmount;


    public static final String KEY_ADVANCE_AMOUNT = "advance_amount";
    public static final String KEY_PENDING_AMOUNT = "pending_amount";

    private static final String PRIMARY_AMOUNT_FORMAT = "{fa-rupee 17sp @color/grey_400} %s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bill bill = FoodHistories.getInstance(this).getBill();

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, PaymentTimeActivity.class));
    }
}

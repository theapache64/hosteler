package com.theah64.hosteler.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.theah64.hosteler.R;
import com.theah64.hosteler.database.tables.FoodHistories;
import com.theah64.hosteler.database.tables.PaymentHistories;
import com.theah64.hosteler.models.Bill;
import com.theah64.hosteler.models.PaymentHistory;
import com.theah64.hosteler.widgets.ValidTextInputLayout;

import butterknife.BindView;

public class PaymentTimeActivity extends BaseAppCompatActivity {

    //Generated with ButterLayout (http://github.com/theapache64/butterLayout): Sun Nov 19 12:08:45 UTC 2017
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

    @BindView(R.id.llPendingAmount)
    View llPendingAmount;

    @BindView(R.id.tvPendingAmount)
    IconTextView tvPendingAmount;

    @BindView(R.id.llAdvanceAmount)
    View llAdvanceAmount;

    @BindView(R.id.tvAdvanceAmount)
    IconTextView tvAdvanceAmount;

    @BindView(R.id.tvGrandTotal)
    IconTextView tvGrandTotal;

    @BindView(R.id.llNewPendingAmount)
    View llNewPendingAmount;

    @BindView(R.id.tvNewPendingAmount)
    IconTextView tvNewPendingAmount;

    @BindView(R.id.llNewAdvanceAmount)
    View llNewAdvanceAmount;

    @BindView(R.id.tvNewAdvanceAmount)
    IconTextView tvNewAdvanceAmount;

    @BindView(R.id.vtilPaymentAmount)
    ValidTextInputLayout vtilPaymentAmount;


    private static final String PRIMARY_AMOUNT_FORMAT = "{fa-rupee 17sp @color/grey_400} %s";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bill bill = FoodHistories.getInstance(this).getBill();
        final PaymentHistory lastPaymentHistory = PaymentHistories.getInstance(this).getLastPaymentHistory();

        long grandTotal = bill.getGrandTotal();
        long pendingAmount = 0;
        long advanceAmount = 0;

        if (lastPaymentHistory != null) {
            pendingAmount = lastPaymentHistory.getPendingAmount();
            advanceAmount = lastPaymentHistory.getAdvanceAmount();
            grandTotal += pendingAmount;
            grandTotal -= advanceAmount;
        }


        tvBreakfastCount.setText("x" + bill.getBreakfastCount());
        tvDinnerCount.setText("x" + bill.getDinnerCount());
        tvGuestBreakfastCount.setText("x" + bill.getGuestBreakfastCount());
        tvGuestDinnerCount.setText("x" + bill.getGuestDinnerCount());

        setRupeeText(tvTotalBreakfastCost, bill.getTotalBreakfastCost());
        setRupeeText(tvTotalDinnerCost, bill.getTotalDinnerCost());
        setRupeeText(tvTotalGuestBreakfastCost, bill.getTotalGuestBreakfastCost());
        setRupeeText(tvTotalGuestDinnerCost, bill.getTotalGuestDinnerCost());
        setRupeeText(tvTotalAdditionalCharge, bill.getTotalAdditionalCharge());


        setRupeeText(tvPendingAmount, pendingAmount);
        setRupeeText(tvAdvanceAmount, advanceAmount);
        setRupeeText(tvGrandTotal, grandTotal);
        setRupeeText(tvNewAdvanceAmount, 0);
        setRupeeText(tvNewPendingAmount, 0);

        //Hiding unnes field
        llPendingAmount.setVisibility(pendingAmount > 0 ? View.VISIBLE : View.GONE);
        llAdvanceAmount.setVisibility(advanceAmount > 0 ? View.VISIBLE : View.GONE);
        llNewPendingAmount.setVisibility(View.GONE);
        llNewAdvanceAmount.setVisibility(View.GONE);

        final long finalGrandTotal = grandTotal;
        vtilPaymentAmount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {

                    final long amount = Long.parseLong(charSequence.toString());

                    long diff = finalGrandTotal - amount;

                    setRupeeText(tvNewAdvanceAmount, Math.abs(diff));
                    setRupeeText(tvNewPendingAmount, diff);

                    llNewPendingAmount.setVisibility(diff > 0 ? View.VISIBLE : View.GONE);
                    llNewAdvanceAmount.setVisibility(diff < 0 ? View.VISIBLE : View.GONE);
                } else {
                    llNewPendingAmount.setVisibility(View.GONE);
                    llNewAdvanceAmount.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setRupeeText(IconTextView itv, long cost) {
        itv.setText(String.format(PRIMARY_AMOUNT_FORMAT, cost));
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

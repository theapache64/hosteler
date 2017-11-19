package com.theah64.hosteler.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.widget.IconTextView;
import com.theah64.hosteler.R;
import com.theah64.hosteler.database.tables.FoodHistories;
import com.theah64.hosteler.database.tables.PaymentHistories;
import com.theah64.hosteler.models.Bill;
import com.theah64.hosteler.models.PaymentHistory;
import com.theah64.hosteler.utils.DateUtils;
import com.theah64.hosteler.widgets.ValidTextInputLayout;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentTimeActivity extends BaseAppCompatActivity {

    public static final int RQ_CODE_PAYMENT_TIME = 12;
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

    @BindView(R.id.llPayment)
    View llPayment;

    @BindView(R.id.tvNewAdvanceAmount)
    IconTextView tvNewAdvanceAmount;

    @BindView(R.id.vtilPaymentAmount)
    ValidTextInputLayout vtilPaymentAmount;

    @BindView(R.id.bPay)
    Button bPay;


    private static final String PRIMARY_AMOUNT_FORMAT = "{fa-rupee 17sp @color/grey_400} %s";
    private long grandTotal;
    private FoodHistories foodHistoriesTable;
    private PaymentHistories paymentHistoriesTable;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodHistoriesTable = FoodHistories.getInstance(this);
        paymentHistoriesTable = PaymentHistories.getInstance(this);

        final Bill bill = foodHistoriesTable.getBill();
        final PaymentHistory lastPaymentHistory = paymentHistoriesTable.getLastPaymentHistory();

        grandTotal = bill.getGrandTotal();
        long pendingAmount = 0;
        long advanceAmount = 0;

        if (lastPaymentHistory != null) {
            pendingAmount = lastPaymentHistory.getPendingAmount();
            advanceAmount = lastPaymentHistory.getAdvanceAmount();
            grandTotal += pendingAmount;
            grandTotal -= advanceAmount;
        }

        llPayment.setVisibility(grandTotal > 0 ? View.VISIBLE : View.GONE);


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

        vtilPaymentAmount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {

                    final long amount = Long.parseLong(charSequence.toString());

                    long diff = grandTotal - amount;

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
            MainActivity.start(this);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static void start(Activity context) {
        context.startActivityForResult(new Intent(context, PaymentTimeActivity.class), RQ_CODE_PAYMENT_TIME);
    }

    @OnClick(R.id.bPay)
    public void onPayButtonClicked() {

        if (vtilPaymentAmount.isMatch()) {

            final long amount = Long.parseLong(vtilPaymentAmount.getString());
            if (amount > 0) {
                new MaterialDialog.Builder(this)
                        .title(R.string.Payment)
                        .content(R.string.Do_you_really_want_to_proceed_with_the_payment)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {


                                    long diff = grandTotal - amount;
                                    final PaymentHistory paymentHistory = new PaymentHistory(null, amount,
                                            diff < 0 ? Math.abs(diff) : 0,
                                            diff > 0 ? diff : 0,
                                            DateUtils.formatWithddMMyyyy(new Date()),
                                            null
                                    );

                                    foodHistoriesTable.update(FoodHistories.COLUMN_IS_PAID, "0", FoodHistories.COLUMN_IS_PAID, "1");
                                    paymentHistoriesTable.add(paymentHistory);
                                    Toast.makeText(PaymentTimeActivity.this, "Payment finished!", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();

                                } else {
                                    Toast.makeText(PaymentTimeActivity.this, R.string.Payment_cancelled, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .build()
                        .show();

            } else {
                Toast.makeText(this, R.string.Amount_cant_be_zero, Toast.LENGTH_SHORT).show();
            }
        }

    }
}

package com.theah64.hosteler.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.theah64.hosteler.R;
import com.theah64.hosteler.adapters.PaymentHistoriesAdapter;
import com.theah64.hosteler.database.tables.FoodHistories;
import com.theah64.hosteler.database.tables.PaymentHistories;
import com.theah64.hosteler.models.PaymentHistory;

import java.util.List;

import butterknife.BindView;

public class PaymentHistoriesActivity extends BaseAppCompatActivity implements PaymentHistoriesAdapter.Callback {

    public static final int RQ_CODE = 1;
    @BindView(R.id.rvPaymentHistories)
    RecyclerView rvPaymentHistories;
    private List<PaymentHistory> paymentHistories;
    private PaymentHistoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_histories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentHistories = PaymentHistories.getInstance(this).getAll();
        if (paymentHistories.isEmpty()) {
            Toast.makeText(this, R.string.No_history_found, Toast.LENGTH_SHORT).show();
            finish();
        }
        rvPaymentHistories.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentHistoriesAdapter(this, this, paymentHistories);
        rvPaymentHistories.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDeletePaymentHistory(final int position) {

        new MaterialDialog.Builder(this)
                .title(R.string.Delete)
                .content(R.string.Do_you_reall_want_to_delete_this_payment)
                .positiveText(R.string.YES)
                .negativeText(R.string.CANCEL)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {

                            final PaymentHistory paymentHistory = paymentHistories.get(position);

                            //Removing connection
                            FoodHistories.getInstance(PaymentHistoriesActivity.this).update(FoodHistories.COLUMN_PAYMENT_HISTORY_ID, paymentHistory.getId(), FoodHistories.COLUMN_PAYMENT_HISTORY_ID, null);
                            PaymentHistories.getInstance(PaymentHistoriesActivity.this).delete(PaymentHistories.COLUMN_ID, paymentHistory.getId());

                            paymentHistories.remove(position);
                            adapter.notifyItemRemoved(position);
                            setResult(RESULT_OK);

                            if (paymentHistories.isEmpty()) {
                                finish();
                            }
                        }
                    }
                })
                .build()
                .show();


    }
}

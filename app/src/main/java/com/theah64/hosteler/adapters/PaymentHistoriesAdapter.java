package com.theah64.hosteler.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.theah64.hosteler.R;
import com.theah64.hosteler.models.PaymentHistory;
import com.theah64.hosteler.utils.DateUtils;
import com.theah64.hosteler.utils.RupeeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by theapache64 on 20/11/17.
 */

public class PaymentHistoriesAdapter extends RecyclerView.Adapter<PaymentHistoriesAdapter.ViewHolder> {

    private final Callback callback;
    private final List<PaymentHistory> paymentHistories;
    private final LayoutInflater inflater;

    public PaymentHistoriesAdapter(final Context context, Callback callback, List<PaymentHistory> paymentHistories) {
        this.callback = callback;
        this.inflater = LayoutInflater.from(context);
        this.paymentHistories = paymentHistories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View row = inflater.inflate(R.layout.payment_history_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PaymentHistory paymentHistory = paymentHistories.get(position);
        holder.tvPaymentDate.setText(DateUtils.getReadableDateFormat(DateUtils.parseWithddMMyyyy(paymentHistory.getDate())) + "(" + paymentHistory.getDate() + ")");
        RupeeUtils.setRupeeText(holder.itvAmount, paymentHistory.getAmountPaid());
    }

    @Override
    public int getItemCount() {
        return paymentHistories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //Generated with ButterLayout (http://github.com/theapache64/butterLayout): Mon Nov 20 13:00:55 UTC 2017
        @BindView(R.id.itvAmount)
        IconTextView itvAmount;

        @BindView(R.id.tvPaymentDate)
        TextView tvPaymentDate;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.itvDeletePayment)
        public void onDeletePaymentButtonClicked() {
            callback.onDeletePaymentHistory(getLayoutPosition());
        }
    }


    public interface Callback {
        void onDeletePaymentHistory(int position);
    }
}

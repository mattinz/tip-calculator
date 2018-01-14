package mattinz.tiphelper;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class TipCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TipCalculatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        setTipButtonListeners();
        //setTextWatchers();

        viewModel = ViewModelProviders.of(this).get(TipCalculatorViewModel.class);
        setViewModelObservers(viewModel);
    }

    private void calculateTotals(float tipAmount) {
        EditText billAmountEditText = findViewById(R.id.input_bill_amount);
        String input = billAmountEditText.getText().toString();
        if(!TextUtils.isEmpty(input)) {
            viewModel.setBillAmount(Float.parseFloat(billAmountEditText.getText().toString()));
            viewModel.setTipAmount(tipAmount);
            viewModel.calculateTotals();
        } else {
            showNoInputError();
        }
    }

    private void changeTipInputMethod(boolean isCustomTipVisible) {
        if(isCustomTipVisible) {
            findViewById(R.id.custom_tip_container).setVisibility(View.VISIBLE);
            findViewById(R.id.preset_tip_container).setVisibility(View.GONE);

            EditText customTipInput = findViewById(R.id.input_custom_tip_amount);
            customTipInput.requestFocus();
        } else {
            findViewById(R.id.custom_tip_container).setVisibility(View.GONE);
            findViewById(R.id.preset_tip_container).setVisibility(View.VISIBLE);
        }
    }

    private void setCurrencyTextView(TextView textView, float amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        textView.setText(format.format(amount));
    }

    private void setTextWatchers() {
        EditText billAmountInput = findViewById(R.id.input_bill_amount);
        billAmountInput.addTextChangedListener(new AppendTextWatcher(billAmountInput,
                NumberFormat.getCurrencyInstance().getCurrency().getSymbol(),
                AppendTextWatcher.BEFORE));

        EditText customTipAmountInput = findViewById(R.id.input_custom_tip_amount);
        customTipAmountInput.addTextChangedListener(new AppendTextWatcher(customTipAmountInput,
                getString(R.string.custom_tip_amount_append), AppendTextWatcher.AFTER));
    }

    private void setTipButtonListeners() {
        Button button = findViewById(R.id.button_tip_eighteen_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_ten_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_fifteen_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_twenty_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_custom);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_custom_cancel);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_custom_accept);
        button.setOnClickListener(this);
    }

    private void setViewModelObservers(TipCalculatorViewModel viewModel) {
        viewModel.getIsCustomTipEntryShown().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                changeTipInputMethod(aBoolean);
            }
        });
        viewModel.getBillTotal().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                setCurrencyTextView((TextView) findViewById(R.id.output_bill_total), aFloat);
            }
        });
        viewModel.getTipTotal().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                setCurrencyTextView((TextView) findViewById(R.id.output_tip_total), aFloat);
            }
        });
    }

    private void showNoInputError() {
        Toast.makeText(this, R.string.error_empty_input, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_tip_ten_percent:
                calculateTotals(10.0f);
                break;
            case R.id.button_tip_fifteen_percent:
                calculateTotals(15.0f);
                break;
            case R.id.button_tip_eighteen_percent:
                calculateTotals(18.0f);
                break;
            case R.id.button_tip_twenty_percent:
                calculateTotals(20.0f);
                break;
            case R.id.button_tip_custom:
            case R.id.button_tip_custom_cancel:
                viewModel.toggleIsCustomTipEntryShown();
                break;
            case R.id.button_tip_custom_accept:
                EditText customTipInput = findViewById(R.id.input_custom_tip_amount);
                String input = customTipInput.getText().toString();
                if(!TextUtils.isEmpty(input)) {
                    calculateTotals(Float.parseFloat(input));
                }
                break;
            default:
                Log.d(TipCalculatorActivity.class.getName(), "Could not locate button.");
        }
    }
}

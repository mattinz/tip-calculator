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

    private void setCurrencyTextView(TextView textView, float amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        textView.setText(format.format(amount));
    }

    private void setTipButtonListeners() {
        Button button = findViewById(R.id.button_tip_five_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_ten_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_fifteen_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_twenty_percent);
        button.setOnClickListener(this);

        button = findViewById(R.id.button_tip_custom);
        button.setOnClickListener(this);
    }

    private void setViewModelObservers(TipCalculatorViewModel viewModel) {
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
            case R.id.button_tip_five_percent:
                calculateTotals(5.0f);
                break;
            case R.id.button_tip_ten_percent:
                calculateTotals(10.0f);
                break;
            case R.id.button_tip_fifteen_percent:
                calculateTotals(15.0f);
                break;
            case R.id.button_tip_twenty_percent:
                calculateTotals(20.0f);
                break;
            case R.id.button_tip_custom:
                //TODO: Implement a custom tip field.
                break;
            default:
                Log.d(TipCalculatorActivity.class.getName(), "Could not locate button.");
        }
    }
}

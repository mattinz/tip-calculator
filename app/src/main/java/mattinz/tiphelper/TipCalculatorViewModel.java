package mattinz.tiphelper;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Mattin on 1/13/2018.
 */

public class TipCalculatorViewModel extends ViewModel {
    private float billAmount;
    private float tipAmount;

    private MutableLiveData<Boolean> isCustomTipEntryShown;
    private MutableLiveData<Float> tipTotal;
    private MutableLiveData<Float> billTotal;

    public TipCalculatorViewModel() {
        billAmount = 0.0f;
        tipAmount = 0.0f;

        isCustomTipEntryShown = new MutableLiveData<>();
        tipTotal = new MutableLiveData<>();
        billTotal = new MutableLiveData<>();

        isCustomTipEntryShown.setValue(false);
    }

    public void calculateTotals() {
        TipCalculator tipCalculator = new TipCalculator(billAmount, tipAmount);
        tipTotal.setValue(tipCalculator.getTipTotal());
        billTotal.setValue(tipCalculator.getBillTotal());
    }

    public LiveData<Boolean> getIsCustomTipEntryShown() {
        return isCustomTipEntryShown;
    }

    public LiveData<Float> getTipTotal() {
        return tipTotal;
    }

    public LiveData<Float> getBillTotal() {
        return billTotal;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    public void setTipAmount(float tipAmount) {
        this.tipAmount = tipAmount;
    }

    public void toggleIsCustomTipEntryShown() {
        isCustomTipEntryShown.setValue(!isCustomTipEntryShown.getValue());
    }
}

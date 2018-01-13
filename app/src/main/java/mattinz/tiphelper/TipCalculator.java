package mattinz.tiphelper;

/**
 * Created by Mattin on 1/13/2018.
 */

public class TipCalculator {

    private final float billTotal;
    private final float tipTotal;

    public TipCalculator(float billAmount, float tipAmount) {
        tipTotal = calculateTip(billAmount, tipAmount);
        billTotal = calculateBillTotal(billAmount, tipTotal);
    }

    public float getBillTotal() {
        return billTotal;
    }

    public float getTipTotal() {
        return tipTotal;
    }

    private float calculateTip(float billAmount, float tipAmount) {
        return billAmount * (tipAmount / 100.0f);
    }

    private float calculateBillTotal(float billAmount, float tipTotal) {
        return billAmount + tipTotal;
    }
}

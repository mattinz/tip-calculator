package mattinz.tiphelper;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Mattin on 1/14/2018.
 */

public class AppendTextWatcher implements TextWatcher {
    public static final int BEFORE = 0;
    public static final int AFTER = 1;

    private final EditText target;
    private final String content;
    private final int mode;

    public AppendTextWatcher(EditText target, String contentToAppend, int mode) {
        this.target = target;
        this.content = contentToAppend;
        this.mode = mode;
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if(!TextUtils.isEmpty(editable.toString())) {
            target.removeTextChangedListener(this);
            String cleanedInput = editable.toString().replace(content, "");
            String result;
            if (mode == BEFORE) {
                result = content + cleanedInput;
            } else {
                result = cleanedInput + content;
            }
            target.setText(result);
            target.setSelection(result.length());
            target.addTextChangedListener(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
}

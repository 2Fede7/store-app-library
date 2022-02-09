package it.gruppopam.app_common.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.EditTextPreference;

public class NonEmptyEditTextPreference extends EditTextPreference {

    public NonEmptyEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NonEmptyEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonEmptyEditTextPreference(Context context) {
        super(context);
    }

}

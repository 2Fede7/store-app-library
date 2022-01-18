package it.gruppopam.app_common.view;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.widget.IconTextView;

public class TaggedIconTextView extends IconTextView {
    String plainText;

    public TaggedIconTextView(Context context) {
        super(context);
    }

    public TaggedIconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaggedIconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        this.plainText = isEmpty(text.toString()) ? EMPTY : text.toString();
        super.setText(Iconify.compute(getContext(), text, this), type);
    }
}

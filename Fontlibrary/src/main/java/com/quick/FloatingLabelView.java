package com.quick;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import chnk.view.R;

public class FloatingLabelView extends LinearLayout implements OnFocusChangeListener {
    final String TAG = getClass().getSimpleName();
    private EditText input;
    private TextView display;
    private LinearLayout.LayoutParams inpuTextParams;
    private LinearLayout.LayoutParams displayTextParams;
    private Animation bottomUp, bottomDown;
    private OnFloatingLableFocusChangeListener focusChangeListener;

//	public FloatingLabelView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}

    public FloatingLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createLayout(attrs);
    }

    public FloatingLabelView(Context context) {
        super(context);
        createLayout(null);
    }

    private void createLayout(AttributeSet attrs) {
        Context context = getContext();
        input = new EditText(context);
        display = new TextView(context);
        input.setOnFocusChangeListener(this);
        bottomUp = AnimationUtils.loadAnimation(context, R.anim.txt_bottom_up);
        bottomDown = AnimationUtils.loadAnimation(context,
                R.anim.txt_bottom_down);
        // Create Default Layout
        inpuTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        displayTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        input.setLayoutParams(inpuTextParams);
        display.setLayoutParams(displayTextParams);
        setOrientation(LinearLayout.VERTICAL);
        createDefaultLayout();

        if (attrs != null) {
            createCustomLayout(attrs);
        }

        addView(display);
        addView(input);
        display.setVisibility(View.INVISIBLE);

        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (input.getText().toString().length() > 0
                        && display.getVisibility() == INVISIBLE) {
                    showHint();
                } else if (input.getText().toString().length() == 0
                        && display.getVisibility() == VISIBLE) {
                    hideHint();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void createDefaultLayout() {
        input.setGravity(Gravity.LEFT | Gravity.TOP);
        display.setGravity(Gravity.LEFT);

        display.setTextColor(Color.BLACK);
        input.setTextColor(Color.BLACK);

        Context context = getContext();
//        input.setTextAppearance(context, android.R.attr.textAppearanceMedium);
//        display.setTextAppearance(context, android.R.attr.textAppearanceSmall);

        display.setPadding(5, 2, 5, 2);
    }

    private void createCustomLayout(AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.FloatingLabel, 0, 0);
        // For Floating Hint

        String floatHintText = a
                .getString(R.styleable.FloatingLabel_floatHintText);
        ColorStateList floatHintTextColorFocused = a
                .getColorStateList(R.styleable.FloatingLabel_floatHintTextColorFocused);
        ColorStateList floatHintTextColorUnFocused = a
                .getColorStateList(R.styleable.FloatingLabel_floatHintTextColorUnFocused);
        int floatHintTextSize = a.getInt(
                R.styleable.FloatingLabel_floatHintTextSize, 15);
        String floatHintTextTypefaceName = a
                .getString(R.styleable.FloatingLabel_floatHintTextTypeface);
        int floatHintTextStyle = a.getInt(
                R.styleable.FloatingLabel_floatHintTextStyle, Typeface.NORMAL);
        int floatHintTextGravity = a.getInt(
                R.styleable.FloatingLabel_floatHintTextGravity, Gravity.LEFT);
        Drawable floatHintTextBackground = a
                .getDrawable(R.styleable.FloatingLabel_floatHintTextBackground);

        // For Actual Text
        String text = a.getString(R.styleable.FloatingLabel_text);
        ColorStateList textColor = a
                .getColorStateList(R.styleable.FloatingLabel_textColor);
        int textSize = a.getInt(R.styleable.FloatingLabel_textSize, 15);
        String textTypefaceName = a.getString(R.styleable.FloatingLabel_textTypeface);
        int textStyle = a.getInt(R.styleable.FloatingLabel_textStyle,Typeface.NORMAL);
        int textGravity = a.getInt(R.styleable.FloatingLabel_textGravity,Gravity.LEFT);

        Drawable textBackground = a
                .getDrawable(R.styleable.FloatingLabel_textBackground);
        boolean isPassword = a.getBoolean(R.styleable.FloatingLabel_isPassword,
                false);

        int inputType = EditorInfo.TYPE_NULL;

        inputType = a.getInt(R.styleable.FloatingLabel_floatInputType, EditorInfo.TYPE_NULL);
        int imeOptions = a.getInt(R.styleable.FloatingLabel_floatImeOptions, EditorInfo.IME_ACTION_DONE);
        int imeActionId = a.getInt(R.styleable.FloatingLabel_floatImeActionId, -1);
        String imeActionLabel = a.getString(R.styleable.FloatingLabel_floatImeActionLabel);
        boolean singleLine = a.getBoolean(R.styleable.FloatingLabel_floatSingleLine, false);
        a.recycle();

        setFloatHintText(floatHintText);
        setFloatHintTextColor(getColorStateList(floatHintTextColorFocused,
                floatHintTextColorUnFocused));
        setFloatHintTextSize(floatHintTextSize);
        setFloatHintTypeFace(floatHintTextTypefaceName, floatHintTextStyle);
        setFloatHintGravity(floatHintTextGravity);
        setFloatHintTextBackGround(floatHintTextBackground);

        setTextHint(floatHintText);
        setTextColor(textColor);
        setTextSize(textSize);
        setTextTypeFace(textTypefaceName, textStyle);
        setTextGravity(textGravity);
        setText(text);
        setTextBackGround(textBackground);
        setPassword(isPassword);
        input.setSingleLine(singleLine);
        input.setImeOptions(imeOptions);
        if((imeActionId>-1)&& !(TextUtils.isEmpty(imeActionLabel)))
        {
            input.setImeActionLabel(imeActionLabel, imeActionId);
        }

        if (inputType != EditorInfo.TYPE_NULL) {
            input.setInputType(inputType);
        }

    }

    private ColorStateList getColorStateList(ColorStateList focused,
                                             ColorStateList unfocused) {
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_selected }, // selected
                new int[] {} // default
        };

        int[] colors = new int[] {
                (focused != null) ? focused.getDefaultColor() : Color.BLACK,
                (unfocused != null) ? unfocused.getDefaultColor() : Color.GRAY };

        return new ColorStateList(states, colors);
    }

    /** FOR LABEL **/
    private void setFloatHintGravity(int floatHintTextGravity) {
        display.setGravity(floatHintTextGravity);
    }

    private void setFloatHintTypeFace(String floatHintTextTypefaceName,
                                      int floatHintTextStyle) {
        try {
            Typeface face = QuickFontManager.getTypeface(getContext().getApplicationContext(),floatHintTextTypefaceName).first;
            display.setTypeface(face);
        } catch (Exception e) {
            display.setTypeface(null, floatHintTextStyle);
        }
    }

    private void setFloatHintTextSize(int floatHintTextSize) {
        display.setTextSize(TypedValue.COMPLEX_UNIT_SP, floatHintTextSize);
    }

    private void setFloatHintTextColor(ColorStateList floatHintTextColor) {
        if (floatHintTextColor != null) {
            display.setTextColor(floatHintTextColor);
        }
    }

    public void setFloatHintText(String string) {
        if (string != null) {
            display.setText(string);
        }
    }

    private void setFloatHintTextBackGround(Drawable textBackground) {
        input.setBackgroundDrawable(textBackground);
    }

    /** FOR TEXT **/

    private void setPassword(boolean isPassword) {
        // TODO Auto-generated method stub
        if (isPassword) {
            input.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }
    }

    private void setTextBackGround(Drawable textBackground) {
        input.setBackgroundDrawable(textBackground);
    }

    private void setTextGravity(int textGravity) {
        input.setGravity(textGravity);
    }

    private void setTextTypeFace(String textTypefaceName, int textStyle) {
        try {
//			Typeface face = Typeface.createFromAsset(getContext().getAssets(),
//					textTypefaceName);
            Typeface face=QuickFontManager.getTypeface(getContext().getApplicationContext(),textTypefaceName).first;
            input.setTypeface(face);
        } catch (Exception e) {
            input.setTypeface(null, textStyle);
        }
    }

    private void setTextSize(int textSize) {
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    private void setTextColor(ColorStateList textColor) {
        if (textColor != null) {
            input.setTextColor(textColor);
        }
    }

    public void setText(String string) {
        if (string != null) {
            input.setText(string);
        }
    }

    public void setTextHint(String hintText) {
        if (hintText != null) {
            input.setHint(hintText);
        }
    }

    private void showHint() {
        if (display.getVisibility() != View.VISIBLE) {
            display.setVisibility(View.VISIBLE);
            display.startAnimation(bottomUp);
        }
    }

    private void hideHint() {
        if (display.getVisibility() != View.INVISIBLE) {
            display.setVisibility(View.INVISIBLE);
            display.startAnimation(bottomDown);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        display.setSelected(hasFocus);
        if (focusChangeListener != null) {
            focusChangeListener.onFocusChange(this, hasFocus);
        }
    }

    public String getText() {
        return input.getText().toString();
    }

    public EditText getinput() {
        return input;
    }

    public void setError(CharSequence text) {
        input.setError(text);
    }
    /**
     * See {@link android.widget.TextView#setError(CharSequence, android.graphics.drawable.Drawable)}.
     */
    public void setError(CharSequence text, Drawable icon) {
        input.setError(text, icon);
    }
    /**
     * See {@link android.widget.TextView#setError(CharSequence)}.
     *
     * @param resourceId
     */
    public void setErrorResource(int resourceId) {
        input.setError(getContext().getString(resourceId));
    }
    /**
     * See {@link android.widget.TextView#setError(CharSequence, Drawable)}.
     *
     * @param resourceId
     */
    public void setErrorResource(int resourceId, Drawable icon) {
        input.setError(getContext().getString(resourceId), icon);
    }
    /**
     */
    public void setImeActionLabel(CharSequence label, int actionId) {
        input.setImeActionLabel(label, actionId);
    }
    /**
     */
    public void setEllipsize(TextUtils.TruncateAt ellipsize) {
        input.setEllipsize(ellipsize);
    }
    /**
     */
    public void setSelection(int index) {
        input.setSelection(index);
    }
    public void setSelection(int start, int stop) {
        input.setSelection(start, stop);
    }
    /**
     * Sets the {@link android.widget.TextView.OnEditorActionListener}.
     *
     * @param listener
     */
    public void setOnEditorActionListener(TextView.OnEditorActionListener listener) {
        input.setOnEditorActionListener(listener);
    }
    /**
     * Requests focus on the input.
     */
    public void requestFieldFocus() {
        input.requestFocus();
    }
    public void setTextColor(int color) {
        input.setTextColor(color);
    }

    /**
     */
    public void selectAll() {
        input.selectAll();
    }
    /**
     */
    public void extendSelection(int index) {
        input.extendSelection(index);
    }





}

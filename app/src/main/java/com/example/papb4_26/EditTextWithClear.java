package com.example.papb4_26;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends androidx.appcompat.widget.AppCompatEditText {

    Drawable clearButtonImage;
    boolean isRightToLeft;
    private void init(){
        isRightToLeft = getResources().getBoolean(R.bool.is_right_to_left);
        clearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.baseline_clear_opaque, null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isButtonClicked = false;
                if (getCompoundDrawablesRelative()[2] != null) {
                    float clearButtonStartPosition;
                    if (!isRightToLeft){
                        clearButtonStartPosition =
                                (getWidth() - getPaddingEnd() -
                                        clearButtonImage.getIntrinsicWidth()
                                );
                        if (motionEvent.getX() > clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    }
                    else {
                        clearButtonStartPosition = getPaddingEnd()+clearButtonImage.getIntrinsicWidth();
                        if (motionEvent.getX() < clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    }
//                    if (motionEvent.getX() > clearButtonStartPosition) {
//                        isButtonClicked = true;
//                    }

                    if (isButtonClicked) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.baseline_close_24, null);
                            showClearButton();
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.baseline_clear_opaque, null);
                            showClearButton();
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }
                    else {return false;}
                }
                    return false;
                }
            }
        );
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void  showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,clearButtonImage,null);
    }

    private void hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
    }

}

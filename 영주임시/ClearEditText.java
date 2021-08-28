package com.example.recrecipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

//이하 https://gun0912.tistory.com/56 참고함.


public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {

    private Drawable clearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;

    public ClearEditText(final Context context){
        super(context);
        init();
    }

    public ClearEditText(final Context context, final AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public ClearEditText(final Context context, final AttributeSet attrs, final int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void setOnFocusChangeListener (OnFocusChangeListener onFocusChangeListner){
        this.onFocusChangeListener = onFocusChangeListner;
    }

    private void init(){

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.cancle);
        bitmap = bitmap.createScaledBitmap(bitmap,150,150,true);

        Drawable tempDrawable = new BitmapDrawable(bitmap);
//        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.cancle);
        clearDrawable = DrawableCompat.wrap(tempDrawable);
        DrawableCompat.setTintList(clearDrawable,getHintTextColors());
        clearDrawable.setBounds(0,0,clearDrawable.getIntrinsicWidth(),clearDrawable.getIntrinsicHeight());

        setClearIconVisible(false);

        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);

    }


    @Override
    public void onFocusChange(final View view, final boolean hasFocus){
        if(hasFocus){
            setClearIconVisible(getText().length()>0);
        } else{
            setClearIconVisible(false);
        }
        if(onFocusChangeListener != null){
            onFocusChangeListener.onFocusChange(view,hasFocus);
        }
    }

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent){
        final int x = (int) motionEvent.getX();
        if (clearDrawable.isVisible() && x > getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth()){
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                setError(null);
                setText(null);
            }
            return true;
        }
        if(onTouchListener != null){
            return onTouchListener.onTouch(view,motionEvent);
        }
        return false;
    }


    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener){
        this.onTouchListener = onTouchListener;
    }

    @Override
    public final void onTextChanged(final CharSequence s, final int start, final int before, final int count){
        if(isFocused()){
            setClearIconVisible(s.length()>0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){

    }

    @Override
    public void afterTextChanged(Editable s){

    }


    private void setClearIconVisible(boolean visible){
        clearDrawable.setVisible(visible,false);
        setCompoundDrawables(null,null,visible ? clearDrawable : null, null);
    }
}

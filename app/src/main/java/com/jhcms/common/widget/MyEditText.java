package com.jhcms.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import com.jhcms.common.utils.ToastUtil;

/**
 * Created by wangyujie
 * on 2017/9/2.
 * TODO 拦截输入优惠买单不享受优惠金额
 */

public class MyEditText extends EditText {
    /*默认为true*/
    private boolean isEmpty = true;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new InputConnecttion(super.onCreateInputConnection(outAttrs), false);

    }

    /**
     * 消费金额是否为空
     *
     * @param isEmpty
     */
    public void setMoneyIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    class InputConnecttion extends InputConnectionWrapper implements
            InputConnection {

        public InputConnecttion(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        /**
         * 对输入的内容进行拦截
         *
         * @param text
         * @param newCursorPosition
         * @return
         */
        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            // 当消费金额为空的时候进行拦截
            if (isEmpty) {
                ToastUtil.show("请先填写消费金额");
                return false;
            }
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean setSelection(int start, int end) {

            return super.setSelection(start, end);
        }

    }
}

package com.pavel.yandexpavel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Павел on 19.04.2016.
 */
public class RecycleListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mlistenerl;
    GestureDetector gestureDetector;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public RecycleListener(Context context, OnItemClickListener onItemClickListener) {
        mlistenerl = onItemClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mlistenerl != null && gestureDetector.onTouchEvent(e)) {
            mlistenerl.onItemClick(childView, rv.getChildPosition(childView));
            return true;
        } else
            return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }


}

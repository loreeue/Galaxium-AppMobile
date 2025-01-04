package com.example.practica3.androidimpl;

import android.view.View.OnTouchListener;
import com.example.practica3.Input;
import java.util.List;

public interface TouchHandler extends OnTouchListener {
    boolean isTouchDown(int pointer);

    int getTouchX(int pointer);

    int getTouchY(int pointer);

    List<Input.TouchEvent> getTouchEvents();
}
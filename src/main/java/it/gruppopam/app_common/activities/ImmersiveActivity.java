package it.gruppopam.app_common.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ImmersiveActivity extends AppCompatActivity {
    private void disableStandBy() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        enableImmersiveMode();
        disableStandBy();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            enableImmersiveMode();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private void enableImmersiveMode() {
        // Enables sticky immersive mode, hiding nav bar (back, home, and apps buttons)
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}

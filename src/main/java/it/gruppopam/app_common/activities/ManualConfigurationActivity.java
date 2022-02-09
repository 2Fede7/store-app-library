package it.gruppopam.app_common.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import it.gruppopam.app_common.R;
import it.gruppopam.app_common.events.ManualConfigurationCompletedEvent;
import it.gruppopam.app_common.fragment.StorePreferenceFragment;

public abstract class ManualConfigurationActivity extends AppCompatActivity {

    @Inject
    StorePreferenceFragment storePreferenceFragment;
    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_store_preference);
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.store_preference_fragment, storePreferenceFragment)
                .commit();
        eventBus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onManualConfigurationCompleted(ManualConfigurationCompletedEvent event) {
        redirectToHome();
    }

    public abstract void redirectToHome();
}

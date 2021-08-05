package it.gruppopam.app_common.utils;

import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.log.CustomLogger;

public class JobManagerLogger implements CustomLogger {

    private boolean shouldLog;

    public JobManagerLogger(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }

    @Override
    public boolean isDebugEnabled() {
        return shouldLog;
    }

    @Override
    public void d(String text, Object... args) {
        if (shouldLog) {
            Log.d(JobManager.class.getCanonicalName(), String.format(text, args));
        }
    }

    @Override
    public void e(Throwable t, String text, Object... args) {
        if (shouldLog) {
            Log.e(JobManager.class.getCanonicalName(), String.format(text, args), t);
        }
    }

    @Override
    public void e(String text, Object... args) {
        if (shouldLog) {
            Log.e(JobManager.class.getCanonicalName(), String.format(text, args));
        }
    }

    @Override
    public void v(String text, Object... args) {
        if (shouldLog) {
            Log.v(JobManager.class.getCanonicalName(), String.format(text, args));
        }
    }
}

package it.gruppopam.app_common.sync;

import android.app.Application;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import it.gruppopam.app_common.utils.ExceptionLogger;
import lombok.Getter;
import lombok.Setter;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class BaseJob extends Job {

    private static final int JOB_DELAY_IN_SECONDS = 15;
    protected static final int VERY_HIGH_PRIORITY = 4;
    protected static final int HIGH_PRIORITY = 3;
    protected static final int MEDIUM_PRIORITY = 2;
    protected static final int LOW_PRIORITY = 1;

    @Getter
    @Setter
    protected boolean enablePaging;

    protected BaseJob(Params params) {
        super(params);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount, int maxRunCount) {
        ExceptionLogger.logError(getClass().getName(), "Sync " + getSingleInstanceId() + " failed", throwable);
        RetryConstraint retryConstraint = new RetryConstraint(true);
        retryConstraint.setNewDelayInMs(SECONDS.toMillis(JOB_DELAY_IN_SECONDS));
        return retryConstraint;
    }

    public abstract void inject(Application application);
}

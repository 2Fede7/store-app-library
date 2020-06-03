package it.gruppopam.app_common.sync.push;

import com.birbit.android.jobqueue.Params;

import it.gruppopam.app_common.sync.BaseJob;

public abstract class PushJob extends BaseJob {

    protected PushJob(Params params) {
        super(params);
    }

    @Override
    public void onRun() throws Throwable {
        doRun();
    }

    public abstract void doRun() throws Throwable;
}

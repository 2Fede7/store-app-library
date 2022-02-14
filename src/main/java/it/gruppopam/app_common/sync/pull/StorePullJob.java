package it.gruppopam.app_common.sync.pull;

import com.birbit.android.jobqueue.Params;

public abstract class StorePullJob extends PullJob {

    protected Long storeId;

    public StorePullJob(boolean enablePaging, Params params, Long storeId) {
        super(enablePaging, params);
        this.storeId = storeId;
    }
}

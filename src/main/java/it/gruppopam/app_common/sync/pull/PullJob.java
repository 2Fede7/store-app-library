package it.gruppopam.app_common.sync.pull;

import com.birbit.android.jobqueue.Params;

import it.gruppopam.app_common.sync.BaseJob;
import retrofit2.Response;

public abstract class PullJob extends BaseJob {

    protected Long storeId;

    //private SharedPreferenceUtil sharedPreferenceUtil;

    public PullJob(boolean enablePaging, Params params) {
        super(params);
        setEnablePaging(enablePaging);
    }

    public PullJob(boolean enablePaging, Params params, Long storeId) {
        super(params);
        setEnablePaging(enablePaging);
        this.storeId = storeId;
    }

    public void initialize(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public void onRun() throws Throwable {
        Response response;
        preSync();
        do {
            response = sync();
        } while (enablePaging && response.code() == 200);
        postSync();
    }

    protected void preSync() {
    }

    protected abstract Response sync() throws Throwable;

    protected void postSync() throws Throwable {

    }
}

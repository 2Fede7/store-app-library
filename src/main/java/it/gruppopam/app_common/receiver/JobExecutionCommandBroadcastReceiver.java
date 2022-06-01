package it.gruppopam.app_common.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

import it.gruppopam.app_common.utils.ExceptionLogger;
import it.gruppopam.app_common.alarms.common.JobsHandlers;

public class JobExecutionCommandBroadcastReceiver extends BaseBroadcastReceiver {

    private final JobsHandlers jobsHandlers;

    @Inject
    public JobExecutionCommandBroadcastReceiver(JobsHandlers jobsHandlers) {
        this.jobsHandlers = jobsHandlers;
    }

    @Override
    public void onReceive(Context context, Intent intentReceived) {
        Log.d(this.getClass().toString(), formatLogIntentData(intentReceived));
        String jobName = intentReceived.getStringExtra("job_name");
        try {
            jobsHandlers.execute(jobName);
        } catch (Exception e) {
            ExceptionLogger.logError("CustomJobRun", jobName, e);
        }
    }

}


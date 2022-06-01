package it.gruppopam.app_common.alarms.common;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.Map;

public class JobsHandlers {
    private final Map<String, JobHandler> handlers;

    public JobsHandlers() {
        this.handlers = new HashMap<>();
    }

    public JobsHandlers register(String key, JobHandler jobHandler) {
        if (isEmpty(key) || jobHandler == null) {
            throw new RuntimeException("Invalid handler: {key: " + key + "; handler: " + jobHandler);
        }
        handlers.put(key, jobHandler);
        return this;
    }

    public void execute(String jobKey) {
        if (isJobKeyInvalid(jobKey)) {
            throw new RuntimeException("Invalid job: " + jobKey);
        }
        handlers.get(jobKey).run();
    }

    private boolean isJobKeyInvalid(String jobKey) {
        return isEmpty(jobKey) || !handlers.containsKey(jobKey);
    }

}

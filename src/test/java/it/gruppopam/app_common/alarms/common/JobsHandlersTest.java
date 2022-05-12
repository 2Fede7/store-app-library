package it.gruppopam.app_common.alarms.common;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JobsHandlersTest {
    @Test
    public void registerHandlerThrowsExceptionIfKeyIsInvalid() {
        assertThrows(RuntimeException.class, () -> new JobsHandlers().register(null, () -> {
        }));
        assertThrows(RuntimeException.class, () -> new JobsHandlers().register("", () -> {
        }));
    }

    @Test
    public void registerHandlerThrowsExceptionIfHandlerIsInvalid() {
        assertThrows(RuntimeException.class, () -> new JobsHandlers().register("A_JOB", null));
    }

    @Test
    public void registerAndExecuteValidHandler() {

        String validJobKey = "A_JOB";

        final boolean[] executed = {false};
        new JobsHandlers().register(validJobKey, () -> {
            executed[0] = true;
        }).execute(validJobKey);

        assertTrue(executed[0]);
    }

    @Test(expected = RuntimeException.class)
    public void tryToRunNotRegisteredJobThrowsException() {
        new JobsHandlers().execute("NOT_EXISTING_JOB");
    }
}

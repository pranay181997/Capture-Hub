package org.example.model;

import org.example.constants.CameraSystemConstants;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class CaptureRequest {
    private final int urgencyLevel;
    private final Consumer<CapturedImage> successCallback;
    private final Consumer<String> failureCallback;
    private final CountDownLatch latch;

    public CaptureRequest(int urgencyLevel, Consumer<CapturedImage> successCallback, Consumer<String> failureCallback) {
        if (urgencyLevel < 0) {
            throw new IllegalArgumentException(CameraSystemConstants.ERROR_INVALID_URGENCY);
        }
        this.urgencyLevel = urgencyLevel;
        this.successCallback = successCallback;
        this.failureCallback = failureCallback;
        this.latch = new CountDownLatch(1);
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public Consumer<CapturedImage> getSuccessCallback() {
        return image -> {
            successCallback.accept(image);
            latch.countDown();
        };
    }

    public Consumer<String> getFailureCallback() {
        return errorMessage -> {
            failureCallback.accept(errorMessage);
            latch.countDown();
        };
    }

    public void waitForCompletion() throws InterruptedException {
        latch.await();
    }
}
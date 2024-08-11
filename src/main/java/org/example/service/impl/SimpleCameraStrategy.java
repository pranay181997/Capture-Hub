package org.example.service.impl;

import org.example.model.CaptureRequest;
import org.example.model.CapturedImage;
import org.example.service.CameraStrategy;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class SimpleCameraStrategy implements CameraStrategy {
    @Override
    public void captureImageAsync(CaptureRequest request) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Simulate image capture delay
                CapturedImage image = new CapturedImage("ImageData");
                if (new Random().nextBoolean()) {
                    request.getSuccessCallback().accept(image);
                } else {
                    request.getFailureCallback().accept("Failed to capture image.");
                }
            } catch (InterruptedException e) {
                request.getFailureCallback().accept("Failed to capture image.");
            }
        });
    }
}
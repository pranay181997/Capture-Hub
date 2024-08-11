package org.example.service.impl;

import org.example.model.CaptureRequest;
import org.example.service.CameraStrategy;
import org.example.service.CaptureCommand;

public class CaptureImageCommand implements CaptureCommand {
    private final CameraStrategy cameraStrategy;
    private final CaptureRequest request;

    public CaptureImageCommand(CameraStrategy cameraStrategy, CaptureRequest request) {
        this.cameraStrategy = cameraStrategy;
        this.request = request;
    }

    public CaptureRequest getRequest() {
        return request;
    }

    @Override
    public void execute() {
        cameraStrategy.captureImageAsync(request);
    }
}
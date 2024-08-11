package org.example.service;

import org.example.model.CaptureRequest;

public interface CameraStrategy {
    void captureImageAsync(CaptureRequest request);
}
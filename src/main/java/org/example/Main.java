package org.example;

import org.example.model.CaptureRequest;
import org.example.service.impl.CameraSystem;
import org.example.service.impl.SimpleCameraStrategy;


public class Main {
    public static void main(String[] args) {
        CameraSystem cameraSystem = new CameraSystem(new SimpleCameraStrategy());

        // Client1 creates multiple capture requests with different urgency levels
        CaptureRequest request1 = new CaptureRequest(1,
                image -> System.out.println("Client1 - Success: " + image.getData()),
                error -> System.err.println("Client1 - Failure: " + error));

        CaptureRequest request2 = new CaptureRequest(10,
                image -> System.out.println("Client1 - Success: " + image.getData()),
                error -> System.err.println("Client1 - Failure: " + error));

        CaptureRequest request3 = new CaptureRequest(5,
                image -> System.out.println("Client1 - Success: " + image.getData()),
                error -> System.err.println("Client1 - Failure: " + error));

        // Submit requests from Client1
        cameraSystem.submitRequest("Client1", request1);
        cameraSystem.submitRequest("Client1", request2);
        cameraSystem.submitRequest("Client1", request3);

        // Client2 creates multiple capture requests
        CaptureRequest request4 = new CaptureRequest(3,
                image -> System.out.println("Client2 - Success: " + image.getData()),
                error -> System.err.println("Client2 - Failure: " + error));

        CaptureRequest request5 = new CaptureRequest(7,
                image -> System.out.println("Client2 - Success: " + image.getData()),
                error -> System.err.println("Client2 - Failure: " + error));

        // Submit requests from Client2
        cameraSystem.submitRequest("Client2", request4);
        cameraSystem.submitRequest("Client2", request5);

        try {
            request1.waitForCompletion();
            request2.waitForCompletion();
            request3.waitForCompletion();
            request4.waitForCompletion();
            request5.waitForCompletion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Shutdown camera system
        cameraSystem.shutdown();
    }
}

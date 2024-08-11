package org.example.service.impl;

import org.example.model.CaptureRequest;
import org.example.service.CameraStrategy;
import org.example.service.CaptureCommand;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraSystem {
    private final CameraStrategy cameraStrategy;
    private final ConcurrentHashMap<String, PriorityQueue<CaptureCommand>> clientQueues;
    private final ExecutorService executorService;

    public CameraSystem(CameraStrategy cameraStrategy) {
        this.cameraStrategy = cameraStrategy;
        this.clientQueues =  new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(4);
        startProcessingCommands();
    }

    private void startProcessingCommands() {
        executorService.submit(() -> {
            while (true) {
                try {
                    for (Map.Entry<String, PriorityQueue<CaptureCommand>> entry : clientQueues.entrySet()) {
                        CaptureCommand command = entry.getValue().poll();
                        if (command != null) {
                            command.execute();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void submitRequest(String clientId, CaptureRequest request) {
        clientQueues.computeIfAbsent(clientId, k -> new PriorityQueue<>((cmd1, cmd2) -> {
            CaptureRequest req1 = ((CaptureImageCommand) cmd1).getRequest();
            CaptureRequest req2 = ((CaptureImageCommand) cmd2).getRequest();
            return Integer.compare(req2.getUrgencyLevel(), req1.getUrgencyLevel());
        }));
        CaptureCommand command = new CaptureImageCommand(cameraStrategy, request);
        clientQueues.get(clientId).add(command);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}


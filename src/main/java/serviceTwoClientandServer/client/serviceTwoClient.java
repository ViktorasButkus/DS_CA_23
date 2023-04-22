package serviceTwoClientandServer.client;

import com.proto.serviceTwo.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class serviceTwoClient {

    private static void doActivateAlerts(ManagedChannel channel) {
        System.out.println("Enter doActivateAlerts");
        AlertSystemServiceGrpc.AlertSystemServiceBlockingStub stub = AlertSystemServiceGrpc.newBlockingStub(channel);
        ActivateAlertsResponse response = stub.activateAlerts(ActivateAlertsRequest.newBuilder().setActivate(true).build());

        System.out.println("Alert message: " +response.getMessage());
    }

    private static void doStreamAlerts(ManagedChannel channel){
        System.out.println("Enter doStreamAlerts");

        AlertSystemServiceGrpc.AlertSystemServiceBlockingStub stub = AlertSystemServiceGrpc.newBlockingStub(channel);

        stub.streamAlerts(StreamAlertsRequest.newBuilder().setSeconds(6).build()).forEachRemaining(response -> {

            System.out.println(response.getAlertMessage());
        });


    }


    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Need one argument to work");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        switch (args[0]) {
            case "activateAlerts": doActivateAlerts(channel); break;
            case "streamAlerts": doStreamAlerts(channel);break;
            default:
                System.out.println("keyword invalid- " + args[0]);
        }


        System.out.println("Shutting down");
        channel.shutdown();
    }
}

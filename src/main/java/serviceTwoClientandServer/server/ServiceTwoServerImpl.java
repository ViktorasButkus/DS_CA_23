package serviceTwoClientandServer.server;

import com.proto.serviceTwo.ActivateAlertsRequest;
import com.proto.serviceTwo.ActivateAlertsResponse;
import com.proto.serviceTwo.StreamAlertsRequest;
import com.proto.serviceTwo.StreamAlertsResponse;
import com.proto.serviceTwo.AlertSystemServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Random;

public class ServiceTwoServerImpl extends AlertSystemServiceGrpc.AlertSystemServiceImplBase {

    //BOTH RPC METHODS FOR SERVICE TWO ARE DEFINED HERE

    //UNARY - Alert is activated or deactivated
    @Override
    public void activateAlerts(ActivateAlertsRequest request, StreamObserver<ActivateAlertsResponse> responseObserver) {

        String message;
        //If the checkbox is ticked it is true and alerts have been activated
        //If checkbox is left empty, it is false and alerts have been deactivated
        if (request.getActivate()) {
            message = "Alerts have been activated";
        } else {
            message = "Alerts have been deactivated";
        }

        //No error handling required here as both scenarios (ticked and left empty) are covered
        //Setting up the response to send to the client
        responseObserver.onNext(ActivateAlertsResponse.newBuilder().setMessage(message).build());
        responseObserver.onCompleted();
    }

    //SERVER STREAM - take in a location and send response that the location has an alert
    @Override
    public void streamAlerts(StreamAlertsRequest request, StreamObserver<StreamAlertsResponse> responseObserver) {

        String location = request.getLocation();
        // Start streaming alerts
        for (int i = 1; i <= 5; i++) {
            // Generate a random alert message
            String alertMessage = generateRandomAlertMessage(location);
            System.out.println(alertMessage);
            // Create the response message and send it to the client
            StreamAlertsResponse response = StreamAlertsResponse.newBuilder().setAlertMessage(alertMessage).build();
            responseObserver.onNext(response);

            // Sleep for some time before sending the next message
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Signal the end of the stream
        responseObserver.onCompleted();
    }

    // Helper method to generate a random alert message based on the location
    //Using a random number generator to pick a number and based on that number, pick a message to send
    private String generateRandomAlertMessage(String location) {
        Random rand = new Random();
        int num = rand.nextInt(3);

        if (num == 0) {
            return "Alert: Movement detected at " + location;
        } else if (num == 1) {
            return "Alert: No movement detected at " + location;
        } else {
            return "Alert: Broken sensor at  " + location;
        }

    }
    }

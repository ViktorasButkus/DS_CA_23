package serviceTwoClientandServer.server;

import com.proto.serviceTwo.ActivateAlertsRequest;
import com.proto.serviceTwo.ActivateAlertsResponse;
import com.proto.serviceTwo.StreamAlertsRequest;
import com.proto.serviceTwo.StreamAlertsResponse;
import com.proto.serviceTwo.AlertSystemServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ServiceTwoServerImpl extends AlertSystemServiceGrpc.AlertSystemServiceImplBase {

    //Unary - Alert is activated or deactivated
    @Override
    public void activateAlerts(ActivateAlertsRequest request, StreamObserver<ActivateAlertsResponse> responseObserver) {

        String message;
        //If the checkbox is ticked it is true and alerts have been activated
        //If checkbox is left unticked, it is fast and alerts have been deactivated
         if (request.getActivate()) {
                message = "Alerts have been activated";
         } else {
             message = "Alerts have been deactivated";
         }

        //Setting up the response to send to the client
        responseObserver.onNext(ActivateAlertsResponse.newBuilder().setMessage(message).build());
        responseObserver.onCompleted();
    }

    //server streaming
    @Override
    public void streamAlerts(StreamAlertsRequest request, StreamObserver<StreamAlertsResponse> responseObserver){
        StreamAlertsResponse response = StreamAlertsResponse.newBuilder().setAlertMessage("Alert happened").build();

        for (int i = 0; i< 10; ++i) {
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }
}

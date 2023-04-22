package serviceTwoClientandServer.server;

import com.proto.serviceTwo.ActivateAlertsRequest;
import com.proto.serviceTwo.ActivateAlertsResponse;
import com.proto.serviceTwo.StreamAlertsRequest;
import com.proto.serviceTwo.StreamAlertsResponse;
import com.proto.serviceTwo.AlertSystemServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ServiceTwoServerImpl extends AlertSystemServiceGrpc.AlertSystemServiceImplBase {

    //Unary
    @Override
    public void activateAlerts(ActivateAlertsRequest request, StreamObserver<ActivateAlertsResponse> responseObserver) {

        //Setting up the response to send to the client
        responseObserver.onNext(ActivateAlertsResponse.newBuilder().setMessage("Alert message incoming: " + request.getActivate()).build());
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

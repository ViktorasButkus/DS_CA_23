package serviceThreeClientAndServer.server;

import com.proto.serviceThree.*;
import io.grpc.stub.StreamObserver;

public class serviceThreeServerImpl extends SensorSystemServiceGrpc.SensorSystemServiceImplBase {

    @Override
    public void activateSensors(ActivateSensorsRequest request, StreamObserver<ActivateSensorsResponse> responseObserver){

        responseObserver.onNext(ActivateSensorsResponse.newBuilder().setMessage("Sensor status " + request.getActivate()).build());
        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<DetectMotionRequest> detectMotion (StreamObserver<DetectMotionResponse> responseObserver){
        StringBuilder sb = new StringBuilder();

        return new StreamObserver<DetectMotionRequest>() {
            @Override
            public void onNext(DetectMotionRequest request) {
                sb.append("Sensor providing information: ");
                sb.append(request.getSensorId() + " " + request.getMotionDetected());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(DetectMotionResponse.newBuilder().setAlertMessage(sb.toString()).build());
                responseObserver.onCompleted();
            }
        };


    }
}

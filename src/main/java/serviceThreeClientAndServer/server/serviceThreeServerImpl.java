package serviceThreeClientAndServer.server;

import com.proto.serviceThree.*;
import com.proto.serviceTwo.ActivateAlertsResponse;
import io.grpc.stub.StreamObserver;

public class serviceThreeServerImpl extends SensorSystemServiceGrpc.SensorSystemServiceImplBase {

    //Unary - Sensors are activated or deactivated
    @Override
    public void activateSensors(ActivateSensorsRequest request, StreamObserver<ActivateSensorsResponse> responseObserver){

        String message;
        //If the checkbox is ticked it is true and sensors have been activated
        //If checkbox is left unticked, it is fast and sensors have been deactivated
        if (request.getActivate()) {
            message = "Sensors have been activated";
        } else {
            message = "Sensors have been deactivated";
        }

        //Setting up the response to send to the client
        responseObserver.onNext(ActivateSensorsResponse.newBuilder().setMessage(message).build());
        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<DetectMotionRequest> detectMotion (StreamObserver<DetectMotionResponse> responseObserver){
        StringBuilder sb = new StringBuilder();

        return new StreamObserver<>() {
            @Override
            public void onNext(DetectMotionRequest request) {

                boolean sen1 = request.getSensor1Activated();
                boolean sen2 = request.getSensor2Activated();
                boolean sen3 = request.getSensor3Activated();

                System.out.println("sen values set");
                String message;

                if (sen1 || sen2 || sen3) {
                    message = "Sensor has been activated";
                } else {
                    message = "All sensors are deactivated";
                }

                sb.append(message);
                DetectMotionResponse response = DetectMotionResponse.newBuilder()
                        .setAlertMessage(sb.toString())
                        .build();

                responseObserver.onNext(response);

                sb.setLength(0); // clear StringBuilder


            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("entering oncompleted");
                responseObserver.onCompleted();
            }
        };


    }
}

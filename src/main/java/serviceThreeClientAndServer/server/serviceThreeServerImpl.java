package serviceThreeClientAndServer.server;

import com.proto.serviceThree.*;
import com.proto.serviceTwo.ActivateAlertsResponse;
import io.grpc.stub.StreamObserver;

public class serviceThreeServerImpl extends SensorSystemServiceGrpc.SensorSystemServiceImplBase {

    //UNARY - Sensors are activated or deactivated
    @Override
    public void activateSensors(ActivateSensorsRequest request, StreamObserver<ActivateSensorsResponse> responseObserver){

        String message;
        //If the checkbox is ticked it is true and sensors have been activated
        //If checkbox is left empty, it is false and sensors have been deactivated
        if (request.getActivate()) {
            message = "Sensors have been activated";
        } else {
            message = "Sensors have been deactivated";
        }

        //No error handling required here as both scenarios (ticked and left empty) are covered
        //Setting up the response to send to the client
        responseObserver.onNext(ActivateSensorsResponse.newBuilder().setMessage(message).build());
        responseObserver.onCompleted();

    }

    //CLIENT STREAM - takes in if sensors have been activated/deactivated and returns the corresponding message
    @Override
    public StreamObserver<DetectMotionRequest> detectMotion (StreamObserver<DetectMotionResponse> responseObserver){
        StringBuilder sb = new StringBuilder();

        return new StreamObserver<>() {
            @Override
            public void onNext(DetectMotionRequest request) {


                boolean sen1 = request.getSensor1Activated();
                boolean sen2 = request.getSensor2Activated();
                boolean sen3 = request.getSensor3Activated();

                String message;

                //checks to see if any sensor has been activated and returns the corresponding message
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

                sb.setLength(0);


            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };


    }
}

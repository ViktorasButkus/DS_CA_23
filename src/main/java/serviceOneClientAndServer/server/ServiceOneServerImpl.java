package serviceOneClientAndServer.server;

import com.proto.serviceOne.*;
import io.grpc.stub.StreamObserver;

public class ServiceOneServerImpl extends serviceOneGrpc.serviceOneImplBase{


    //Unary - get a request, return a response
    @Override
    public void takePicture(TakePictureRequest request, StreamObserver<TakePictureResponse> responseObserver) {

        //Setting up the response to send to the client
        responseObserver.onNext(TakePictureResponse.newBuilder().setResult("Image received from camera " + request.getCamera()).build());
        responseObserver.onCompleted();
    }


    //Bidirectional - return a response each time we receive a request
    @Override
    public StreamObserver<MotionEvent> streamMotionEvents(StreamObserver<Alert> responseObserver) {

        return new StreamObserver<MotionEvent>() {
            @Override
            public void onNext(MotionEvent request) {
                responseObserver.onNext(Alert.newBuilder().setMessage("Motion detected: " + request.getMotionDetected()).build());
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

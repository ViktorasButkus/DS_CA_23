package serviceOneClientAndServer.server;

import com.proto.serviceOne.*;
import io.grpc.stub.StreamObserver;

public class ServiceOneServerImpl extends serviceOneGrpc.serviceOneImplBase{


    //Unary
    @Override
    public void takePicture(TakePictureRequest request, StreamObserver<TakePictureResponse> responseObserver) {

        //Setting up the response to send to the client
        responseObserver.onNext(TakePictureResponse.newBuilder().setResult("Image received from camera " + request.getCamera()).build());
        responseObserver.onCompleted();
    }


    //Bidirectional
    @Override
    public StreamObserver<MotionEvent> streamMotionEvents(StreamObserver<Alert> responseObserver) {
        //return super.streamMotionEvents(responseObserver);

        return new StreamObserver<MotionEvent>() {
            @Override
            public void onNext(MotionEvent value) {
                responseObserver.onNext(Alert.newBuilder().build());

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

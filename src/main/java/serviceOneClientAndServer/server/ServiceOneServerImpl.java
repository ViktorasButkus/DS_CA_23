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


    //Bidirectional - creating a zoom slider to zoom in and out on a camera
    @Override
    public StreamObserver<MotionEvent> streamMotionEvents(StreamObserver<Alert> responseObserver) {

        return new StreamObserver<MotionEvent>() {
            @Override
            public void onNext(MotionEvent request) {

                String message;
                if (request.getZoom() < 5) {
                    message = "Zoomed out image";

                } else if (request.getZoom() > 5) {
                    message = "Zoomed in image ";
                } else {
                    message = "No change to image";
                }
                responseObserver.onNext(Alert.newBuilder().setCameraFrame(message).build());
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

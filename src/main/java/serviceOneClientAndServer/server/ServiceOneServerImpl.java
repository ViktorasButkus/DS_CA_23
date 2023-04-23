package serviceOneClientAndServer.server;

import com.proto.serviceOne.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class ServiceOneServerImpl extends serviceOneGrpc.serviceOneImplBase{

    //BOTH RPC METHODS FOR SERVICE ONE ARE DEFINED HERE

    //UNARY - Taking an entry of location and returning the response of image was taken of that location
    @Override
    public void takePicture(TakePictureRequest request, StreamObserver<TakePictureResponse> responseObserver) {

        //Error handling for if an unknown location is entered
        if (!request.getCamera().equalsIgnoreCase("Door")
                && !request.getCamera().equalsIgnoreCase("Window")
                && !request.getCamera().equalsIgnoreCase("Garden")) {

            responseObserver.onError(Status.INVALID_ARGUMENT
                            .withDescription("Unknown Location: " + request.getCamera())
                            .augmentDescription("Known locations are: Door, Window, Garden")
                            .asRuntimeException());

            }

        //Setting up the response to send to the client
        responseObserver.onNext(TakePictureResponse.newBuilder().setResult("Image received from camera: " + request.getCamera()).build());
        responseObserver.onCompleted();
    }


    //BIDIRECTIONAL - taking the input from a zoom slider to zoom in and out on a camera
    @Override
    public StreamObserver<MotionEvent> streamMotionEvents(StreamObserver<Alert> responseObserver) {

        return new StreamObserver<MotionEvent>() {
            @Override
            public void onNext(MotionEvent request) {
                //Taking the value set on the slider, it will output the corresponding message
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

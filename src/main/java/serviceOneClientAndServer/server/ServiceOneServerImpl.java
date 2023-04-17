package serviceOneClientAndServer.server;

import com.proto.serviceOne.serviceOneGrpc;
import com.proto.serviceOne.TakePictureRequest;
import com.proto.serviceOne.TakePictureResponse;
import io.grpc.stub.StreamObserver;

public class ServiceOneServerImpl extends serviceOneGrpc.serviceOneImplBase{

    @Override
    public void takePicture(TakePictureRequest request, StreamObserver<TakePictureResponse> responseObserver) {

        //Setting up the response to send to the client
        responseObserver.onNext(TakePictureResponse.newBuilder().setResult("Image received from camera " + request.getCamera()).build());
        responseObserver.onCompleted();
    }
}

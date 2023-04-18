package serviceThreeClientAndServer.server;

import com.proto.serviceThree.SensorSystemServiceGrpc;
import com.proto.serviceThree.ActivateSensorsRequest;
import com.proto.serviceThree.ActivateSensorsResponse;
import io.grpc.stub.StreamObserver;

public class serviceThreeServerImpl extends SensorSystemServiceGrpc.SensorSystemServiceImplBase {

    @Override
    public void activateSensors(ActivateSensorsRequest request, StreamObserver<ActivateSensorsResponse> responseObserver){

    }
}

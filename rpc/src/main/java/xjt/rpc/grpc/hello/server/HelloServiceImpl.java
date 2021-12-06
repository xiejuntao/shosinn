package xjt.rpc.grpc.hello.server;

import io.grpc.stub.StreamObserver;
import xjt.rpc.grpc.hello.HelloReply;
import xjt.rpc.grpc.hello.HelloRequest;
import xjt.rpc.grpc.hello.HelloServiceGrpc;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase{
    @Override
    public void say(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("初相见： " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}

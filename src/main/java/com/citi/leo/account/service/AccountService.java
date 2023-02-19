package com.citi.leo.account.service;

import com.citi.leo.account.proto.AccountProto;
import com.citi.leo.account.proto.AccountServiceGrpc;
import io.grpc.stub.StreamObserver;

public class AccountService extends AccountServiceGrpc.AccountServiceImplBase {

    // client side streaming
    @Override
    public StreamObserver<AccountProto.RegistrationRequest> registerAccount(StreamObserver<AccountProto.RegistrationResponse> responseObserver) {
        /**
         * Async communication is based on callback of observers
         */
        return new StreamObserver<AccountProto.RegistrationRequest>() {
            int i = 0;
            @Override
            public void onNext(AccountProto.RegistrationRequest registrationRequest) {
                System.out.println(registrationRequest.getPhoneNumber() + " was registered!");
                i = i +1;
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(AccountProto.RegistrationResponse.newBuilder().
                        setResult("You successfully registered "+ i +" accounts!").build());
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * Bidirectional-streaming RPC
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<AccountProto.RegistrationRequest> registerAndNotify(StreamObserver<AccountProto.RegistrationResponse> responseObserver) {
        return new StreamObserver<AccountProto.RegistrationRequest>() {
            int i = 0;
            @Override
            public void onNext(AccountProto.RegistrationRequest registrationRequest) {
                String phoneNumber = registrationRequest.getPhoneNumber();
                System.out.println(phoneNumber + " was registered!");
                responseObserver.onNext(AccountProto.RegistrationResponse.newBuilder()
                        .setResult("account " + phoneNumber+ " was registered, it was onboarded to system A").build());
                responseObserver.onNext(AccountProto.RegistrationResponse.newBuilder()
                        .setResult("account " + phoneNumber+ " was registered, it was onboarded to system B").build());
                responseObserver.onNext(AccountProto.RegistrationResponse.newBuilder()
                        .setResult("account " + phoneNumber+ " was registered, it was onboarded to system C").build());
                i = i +1;
                responseObserver.onNext(AccountProto.RegistrationResponse.newBuilder()
                        .setResult("---------------------------------------------------------------").build());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}

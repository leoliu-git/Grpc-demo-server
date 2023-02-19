package com.citi.leo;

import com.citi.leo.account.service.AccountService;
import com.citi.leo.shopping.service.ShoppingService;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    private static final int port = 9999;
    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server = ServerBuilder.forPort(port)
                .addService(new ShoppingService())
                .addService(new AccountService())
                .build().start();
        System.out.println(String.format("GRPC service started, port: %d", port));
        server.awaitTermination();
    }
}

package com.citi.leo.shopping.service;

import com.citi.leo.shopping.proto.ShoppingProto;
import com.citi.leo.shopping.proto.ShoppingServiceGrpc;
import com.google.protobuf.ProtocolStringList;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class ShoppingService extends ShoppingServiceGrpc.ShoppingServiceImplBase {

    // Simple RPC
    @Override
    public void search(ShoppingProto.ShoppingRequest request, StreamObserver<ShoppingProto.ShoppingResponse> responseObserver) {
        String commodityType = request.getCommodityType();
        ShoppingProto.ShoppingResponse commodityList = null;
        try {
            if ("computer".equals(commodityType)){
                commodityList = generateComputerList();
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(commodityList);
        }
        responseObserver.onCompleted();
    }

    // Server-streaming RPC
    @Override
    public void list(ShoppingProto.ShoppingRequests request, StreamObserver<ShoppingProto.ShoppingResponse> responseObserver) {
        ProtocolStringList commodityTypeList = request.getCommodityTypeList();
        for (String commodityType : commodityTypeList){
            ShoppingProto.ShoppingResponse response = null;
            if (commodityType.equals("computer")){
                response = generateComputerList();
            }else if(commodityType.equals("car")){
                response = generateCarList();
            }else if(commodityType.equals("phone")){
                response = generatePhoneList();
            }
            responseObserver.onNext(response);
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        responseObserver.onCompleted();
    }

    public ShoppingProto.ShoppingResponse generateComputerList(){
        ShoppingProto.ShoppingResponse.Builder commodityListBuilder = ShoppingProto.ShoppingResponse.newBuilder();
        ShoppingProto.Commodity commodity1 = ShoppingProto.Commodity.newBuilder()
                .setBrand("Apple").setPrice(9999.99).build();
        ShoppingProto.Commodity commodity2 = ShoppingProto.Commodity.newBuilder()
                .setBrand("Microsoft").setPrice(7999.99).build();
        ShoppingProto.Commodity commodity3 = ShoppingProto.Commodity.newBuilder()
                .setBrand("Dell").setPrice(5999.99).build();

        commodityListBuilder.addCommodity(commodity1).addCommodity(commodity2).addCommodity(commodity3);
        return commodityListBuilder.build();
    }

    public ShoppingProto.ShoppingResponse generatePhoneList(){
        ShoppingProto.ShoppingResponse.Builder commodityListBuilder = ShoppingProto.ShoppingResponse.newBuilder();
        ShoppingProto.Commodity commodity1 = ShoppingProto.Commodity.newBuilder()
                .setBrand("Apple").setPrice(6999.99).build();
        ShoppingProto.Commodity commodity2 = ShoppingProto.Commodity.newBuilder()
                .setBrand("Huawei").setPrice(5999.99).build();
        ShoppingProto.Commodity commodity3 = ShoppingProto.Commodity.newBuilder()
                .setBrand("Xiaomi").setPrice(2999.99).build();

        commodityListBuilder.addCommodity(commodity1).addCommodity(commodity2).addCommodity(commodity3);
        return commodityListBuilder.build();
    }

    public ShoppingProto.ShoppingResponse generateCarList(){
        ShoppingProto.ShoppingResponse.Builder commodityListBuilder = ShoppingProto.ShoppingResponse.newBuilder();
        ShoppingProto.Commodity commodity1 = ShoppingProto.Commodity.newBuilder()
                .setBrand("BMW").setPrice(400000).build();
        ShoppingProto.Commodity commodity2 = ShoppingProto.Commodity.newBuilder()
                .setBrand("BENZ").setPrice(500000).build();
        ShoppingProto.Commodity commodity3 = ShoppingProto.Commodity.newBuilder()
                .setBrand("BYD").setPrice(200000).build();

        commodityListBuilder.addCommodity(commodity1).addCommodity(commodity2).addCommodity(commodity3);
        return commodityListBuilder.build();
    }
}

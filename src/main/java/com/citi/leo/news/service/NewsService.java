package com.citi.leo.news.service;

import com.citi.leo.news.proto.NewsProto;
import com.citi.leo.news.proto.NewsServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.Date;

public class NewsService extends NewsServiceGrpc.NewsServiceImplBase {
    @Override
    public void list(NewsProto.NewsRequest request, StreamObserver<NewsProto.NewsResponse> responseObserver) {
        String date = request.getDate();
        NewsProto.NewsResponse newsList = null;
        try {
            NewsProto.NewsResponse.Builder newsBuilder = NewsProto.NewsResponse.newBuilder();
            for (int i = 0; i < 100; i++) {
                NewsProto.News news = NewsProto.News.newBuilder().setId(i)
                        .setContent(date+"current news contect" +i)
                        .setTitle("title" +i)
                        .setCreateTime(new Date().getTime())
                        .build();
                newsBuilder.addNews(news);
            }
            newsList = newsBuilder.build();
        }catch (Exception e){
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(newsList);
        }
        responseObserver.onCompleted();
    }
}

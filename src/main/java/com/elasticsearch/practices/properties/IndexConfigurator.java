package com.elasticsearch.practices.properties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static org.elasticsearch.client.RequestOptions.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class IndexConfigurator {
    private static final String KEYWORD = "keyword";
    private static final String TYPE = "type";
    private final RestHighLevelClient restHighLevelClient;
    private final ConfigProperties properties;


    @PostConstruct
    private void createIndexWithMapping() {
        final IndicesClient indicesClient = restHighLevelClient.indices();
        GetIndexRequest request = new GetIndexRequest(properties.getIndex().getName());
        try {
            final boolean exists = indicesClient.exists(request, DEFAULT);
            if (!exists) {
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(properties.getIndex().getName());
                createIndexRequest.settings(Settings.builder()
                        .put("index.number_of_shards", properties.getIndex().getShard())
                        .put("index.number_of_replicas", properties.getIndex().getShard())
                        .build());
                XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                        .startObject()
                            .startObject("properties")
                                .startObject("event_type")
                                    .field(TYPE, KEYWORD)
                                .endObject()
                                .startObject("user_id")
                                    .field(TYPE, KEYWORD)
                                .endObject()
                                .startObject("artist_id")
                                    .field(TYPE, KEYWORD)
                                .endObject()
                            .endObject()
                        .endObject();
                createIndexRequest.mapping(contentBuilder);

                indicesClient.create(createIndexRequest, DEFAULT);

                log.info("Index {} created", properties.getIndex().getName());
            }
        } catch (IOException e) {
            log.error(String.format("The exception was thrown in createIndexWithMapping method.%s", e));
        }


    }
  /*      IndicesAdminClient adminClient=client.admin().indices();
        IndicesExistsRequest request=new IndicesExistsRequest(properties.getIndex().getName());
        IndicesExistsResponse response=adminClient.exists(request).actionGet();
        if (!response.isExists()) {
            adminClient.prepareCreate(properties.getIndex().getName())
                    .setSettings();
            log.info("Index {} created", properties.getIndex().getName());

        }
       CreateIndexRequest request = new CreateIndexRequest(properties.getIndex().getName());
         try {

             XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                     .startObject()
                     .startObject("properties")
                     .startObject("event_type")
                     .field(TYPE, KEYWORD)
                     .endObject()
                     .startObject("user_id")
                     .field(TYPE, KEYWORD)
                     .endObject()
                     .startObject("artist_id")
                     .field(TYPE, KEYWORD)
                     .endObject()
                     .endObject()
                     .endObject();
          *//*  request.settings(Settings.builder()
                    .put("index.number_of_shards", properties.getIndex().getShard())
                    .put("index.number_of_replicas", properties.getIndex().getReplica())
                    .build());



            request.mapping(contentBuilder);

*//*
        } catch (IOException e) {
            log.error(String.format("The exception was thrown in createIndexWithMapping method.%s", e));
        }
    }*/


}




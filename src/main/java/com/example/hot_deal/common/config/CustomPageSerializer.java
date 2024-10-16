package com.example.hot_deal.common.config;

import com.example.hot_deal.common.domain.PageResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class CustomPageSerializer <T> extends JsonSerializer<Page<T>> {

    @Override
    public void serialize(Page<T> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        PageResponse<T> response = PageResponse.from(page);
        gen.writeObject(response);
    }
}

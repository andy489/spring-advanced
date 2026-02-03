package bg.softuni.bookserver.config;

import bg.softuni.bookserver.model.entity.BookEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class CustomJacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(BookEntity.class, new BookEntitySerializer());
        mapper.registerModule(module);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }

    public static class BookEntitySerializer extends JsonSerializer<BookEntity> {
        @Override
        public void serialize(BookEntity bookEntity, JsonGenerator gen, SerializerProvider provider)
                throws IOException {

            gen.writeStartObject();
            gen.writeNumberField("id", bookEntity.getId());
            gen.writeStringField("title", bookEntity.getTitle());
            gen.writeStringField("isbn", bookEntity.getIsbn());

            if (bookEntity.getAuthor() != null) {
                gen.writeObjectFieldStart("author");
                gen.writeStringField("name", bookEntity.getAuthor().getName());
                gen.writeEndObject();
            } else {
                gen.writeNullField("author");
            }

            gen.writeEndObject();
        }
    }
}
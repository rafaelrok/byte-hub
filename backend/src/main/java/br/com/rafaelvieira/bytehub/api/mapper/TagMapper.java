package br.com.rafaelvieira.bytehub.api.mapper;

import br.com.rafaelvieira.bytehub.api.model.tag.TagListResponse;
import br.com.rafaelvieira.bytehub.domain.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagMapper {
    public TagListResponse toCollectionResponse(List<Tag> tags) {
        var tagNames = tags.stream().map(Tag::getName).toList();
        return TagListResponse.builder()
                .tags(tagNames)
                .build();
    }
}

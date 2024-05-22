package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.domain.model.Tag;
import br.com.rafaelvieira.bytehub.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;


    @Transactional(readOnly = true)
    public List<Tag> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Tag save(Tag tag) {
        var existingTag = repository.findByName(tag.getName());
        return existingTag.map(repository::save).orElseGet(() -> repository.save(tag));
    }

    public List<Tag> saveAll(List<String> tags) {
        return tags.stream()
                .map(t -> {
                    var tag = toEntity(t);
                    return save(tag);
                })
                .toList();
    }

    private Tag toEntity(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }
}

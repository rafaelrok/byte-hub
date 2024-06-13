package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.mapper.TagMapper;
import br.com.rafaelvieira.bytehub.api.model.tag.TagListResponse;
import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    @CheckSecurity.Public.canRead
    public TagListResponse list() {
        return tagMapper.toCollectionResponse(tagService.listAll());
    }
}

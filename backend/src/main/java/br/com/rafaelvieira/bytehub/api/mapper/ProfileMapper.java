package br.com.rafaelvieira.bytehub.api.mapper;

import br.com.rafaelvieira.bytehub.api.model.profile.ProfileResponse;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final ModelMapper modelMapper;

    public ProfileResponse toResponse(Profile profile) {
        return modelMapper.map(profile, ProfileResponse.class);
    }

    public ProfileResponse toResponse(Profile current, Profile profile) {
        var response = toResponse(profile);

        var isFollowing = current.getProfiles().contains(profile);
        response.setFollowing(isFollowing);

        return response;
    }
}

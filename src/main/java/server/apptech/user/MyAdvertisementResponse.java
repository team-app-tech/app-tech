package server.apptech.user;

import lombok.Builder;
import lombok.Getter;
import server.apptech.advertisement.dto.AdResponse;

import java.util.List;

@Getter
@Builder
public class MyAdvertisementResponse {

    private List<AdResponse> content;
    public static MyAdvertisementResponse of(List<AdResponse> adResponses){
        return MyAdvertisementResponse.builder()
                .content(adResponses)
                .build();
    }
}

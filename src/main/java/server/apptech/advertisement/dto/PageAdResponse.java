package server.apptech.advertisement.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PageAdResponse {

    private List<AdResponse> content;
    private int totalPage;
    private int currentPage;
    private long totalElements;

    public static PageAdResponse of(Page<AdResponse> adResponses){
        return PageAdResponse.builder()
                .content(adResponses.getContent())
                .totalPage(adResponses.getTotalPages())
                .currentPage(adResponses.getPageable().getPageNumber())
                .totalElements(adResponses.getTotalElements())
                .build();
    }
}

package server.apptech.prize.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.repository.AdvertisementRepository;
import server.apptech.comment.domain.Comment;
import server.apptech.comment.domain.repository.CommentRepository;
import server.apptech.prize.domain.repository.PrizeRepository;
import server.apptech.prize.domain.Prize;
import server.apptech.user.UserRepository;
import server.apptech.user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrizeService {

    private final PrizeRepository prizeRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    public void givePrizeToWinner(Long advertisementId) {

        Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
        // 상금
        Long totalPrice = advertisement.getTotalPrice();
        // 인원수
        Integer prizeWinnerCnt = advertisement.getPrizeWinnerCnt();

        // 인원수 만큼의 상위댓글 가져옴 좋아요순 정렬
        List<Comment> prizeComments = commentRepository.findTopPrizeCommentsSortedByLikes(PageRequest.of(0, prizeWinnerCnt), advertisementId);

        int totalLikeCnt = getTotalLikeCnt(prizeComments);

        for(int i = 0; i < prizeComments.size() ; i++) {
            Comment comment = prizeComments.get(i);
            User user = comment.getUser();
            int likeCnt = comment.getLikeCnt();
            double likeRatio = (double) likeCnt / totalLikeCnt;
            int prizePoint = (int)(totalPrice * likeRatio);
            if(prizePoint > 0){
                user.addPoint(prizePoint);
                Prize prize = Prize.builder()
                        .advertisement(advertisement)
                        .user(user)
                        .comment(comment)
                        .price(prizePoint)
                        .ranking(i+1)
                        .build();
                prizeRepository.save(prize);
                userRepository.save(user);
            }
        }
    }

    private int getTotalLikeCnt(List<Comment> prizeComments) {
        return prizeComments.stream().mapToInt(c -> c.getLikeCnt()).sum();
    }
}

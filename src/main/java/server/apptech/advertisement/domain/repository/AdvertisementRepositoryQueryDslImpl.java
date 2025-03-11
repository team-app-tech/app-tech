package server.apptech.advertisement.domain.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.advertisement.domain.type.EventStatus;
import server.apptech.advertisement.domain.type.SortOption;
import java.time.LocalDateTime;
import java.util.List;

import static server.apptech.advertisement.domain.QAdvertisement.advertisement;
import static server.apptech.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryQueryDslImpl implements AdvertisementRepositoryQueryDsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Advertisement> findAdvertisements(PageRequest pageable, EventStatus eventStatus, SortOption sortOption, LocalDateTime now, String keyword) {


        BooleanExpression eventStatusPredicate = getEventStatusPredicate(eventStatus, now);

        List<Advertisement> content = queryFactory.selectFrom(advertisement)
                .leftJoin(advertisement.user, user).fetchJoin()
                .where(
                        eventStatusPredicate,
                        keyword != null ? advertisement.title.containsIgnoreCase(keyword) : null
                )
                .orderBy(getOrderSpecifier(sortOption))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(advertisement.count())
                .from(advertisement)
                .where(
                        eventStatusPredicate,
                        keyword != null ? advertisement.title.containsIgnoreCase(keyword) : null
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    private static BooleanExpression getEventStatusPredicate(EventStatus eventStatus, LocalDateTime now) {
        BooleanExpression eventStatusPredicate = switch (eventStatus) {
            case UPCOMING -> advertisement.startDate.gt(now);
            case ONGOING -> advertisement.startDate.loe(now).and(advertisement.endDate.goe(now));
            case FINISHED -> advertisement.endDate.lt(now);
        };
        return eventStatusPredicate;
    }

    private static OrderSpecifier<?> getOrderSpecifier(SortOption sortOption) {
        OrderSpecifier<?> orderSpecifier = switch (sortOption) {
            case PRIZE_DESCENDING -> new OrderSpecifier<>(Order.DESC, advertisement.totalPrice);
            case START_ASCENDING -> new OrderSpecifier<>(Order.ASC, advertisement.createdAt);
            case END_ASCENDING -> new OrderSpecifier<>(Order.ASC, advertisement.endDate);
            case VIEWS_DESCENDING -> new OrderSpecifier<>(Order.DESC, advertisement.viewCnt);
            case COMMENTS_DESCENDING -> new OrderSpecifier<>(Order.DESC, advertisement.commentCnt);
            case LIKES_DESCENDING -> new OrderSpecifier<>(Order.DESC, advertisement.likeCnt);
        };
        return orderSpecifier;
    }
}

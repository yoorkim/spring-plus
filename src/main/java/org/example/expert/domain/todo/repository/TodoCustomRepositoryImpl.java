package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.comment.entity.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {

        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(String title, LocalDate createdFrom, LocalDate createdTo, String nickname, Pageable pageable) {

        JPQLQuery<TodoSearchResponse> query = queryFactory
                .select(Projections.constructor(TodoSearchResponse.class,
                        todo.title,
                        manager.countDistinct(),
                        comment.countDistinct()))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContains(title),
                        createdBetween(createdFrom, createdTo),
                        nicknameContains(nickname)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<TodoSearchResponse> results = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? todo.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression createdBetween(LocalDate start, LocalDate end) {
        if (start == null && end == null) return null;
        if (start != null && end != null) {
            return todo.createdAt.between(start.atStartOfDay(), end.atTime(LocalTime.MAX));
        }
        if (start != null) {
            return todo.createdAt.goe(start.atStartOfDay());
        }
        return todo.createdAt.loe(end.atTime(LocalTime.MAX));
    }

    private BooleanExpression nicknameContains(String nickname) {
        return StringUtils.hasText(nickname) ? manager.user.nickname.containsIgnoreCase(nickname) : null;
    }

}

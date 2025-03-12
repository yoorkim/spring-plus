package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoCustomRepository {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT t FROM Todo t WHERE " +
            "(:weather IS NULL OR t.weather = :weather) AND " +
            "(:start IS NULL OR t.modifiedAt >= :start) AND " +
            "(:end IS NULL OR t.modifiedAt <= :end)")
    Page<Todo> findAllByFilters(@Param("weather") String weather,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                Pageable pageable);

}

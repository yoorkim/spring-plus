package org.example.expert.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class UserBatchInsertTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final int TOTAL_USERS = 1_000_000;
    private static final int BATCH_SIZE = 1000;

    private final Random random = new Random();

    @Test
    public void insertMillionUsers() {
        long start = System.currentTimeMillis();

        String sql = "INSERT INTO users (email, password, nickname, user_role, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String password = "$2a$10$mRkXSzEoIYO6n5a8N4Dpe.nI0Tzq9SxvVmWHnvdypz0gPY/1stbii";

        for (int batch = 0; batch < TOTAL_USERS / BATCH_SIZE; batch++) {
            final int currentBatch = batch;

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int userId = currentBatch * BATCH_SIZE + i;
                    String email = "user" + userId + "@test.com";
                    String nickname = randomNickname();
                    String role = "ROLE_USER";
                    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

                    ps.setString(1, email);
                    ps.setString(2, password);
                    ps.setString(3, nickname);
                    ps.setString(4, role);
                    ps.setTimestamp(5, now);
                    ps.setTimestamp(6, now);
                }

                @Override
                public int getBatchSize() {
                    return BATCH_SIZE;
                }
            });

            if ((batch + 1) % 10 == 0) {
                System.out.println("Inserted " + ((batch + 1) * BATCH_SIZE) + " users...");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Completed inserting 1,000,000 users in " + (end - start) / 1000.0 + " seconds.");
    }

    private String randomNickname() {
        int length = 6;
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

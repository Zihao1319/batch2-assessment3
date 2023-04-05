package ibf2022.paf.assessment.server.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3

@Repository
public class UserRepository {

    public final static String FIND_BY_USERNAME_SQL = "select * from users where username = ?";
    public final static String INSERT_USER_SQL = "insert into users (user_id, username, name) values (?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findUserByUsername(String username) {

        try {
            User user = jdbcTemplate.queryForObject(FIND_BY_USERNAME_SQL, BeanPropertyRowMapper.newInstance(User.class),
                    username);
            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    public String insertUser(User user) {

        String userId = UUID.randomUUID().toString().substring(0, 8);

        jdbcTemplate.execute(INSERT_USER_SQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ps.setString(1, userId);
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getName());
                Boolean result = ps.execute();
                return result;
            }
        });

        return userId;
    }
}

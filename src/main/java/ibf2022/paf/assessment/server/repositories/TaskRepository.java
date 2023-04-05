package ibf2022.paf.assessment.server.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6
@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_TASKS_SQL = "insert into task (user_id, description, priority, due_date) values (?, ?, ?, ?)";

    public int insertTask(List<Task> taskList, String userId) {
        List<Object[]> params = taskList.stream()
                .map(task -> new Object[] { userId, task.getDescription(), task.getPriority(), task.getDueDate() })
                .collect(Collectors.toList());

        int added[] = jdbcTemplate.batchUpdate(INSERT_TASKS_SQL, params);
        return added.length;
    }
}

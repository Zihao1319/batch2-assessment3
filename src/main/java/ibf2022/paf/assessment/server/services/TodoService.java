package ibf2022.paf.assessment.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7
@Service
public class TodoService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public int upsertTask(String userName, List<Task> taskList) {

        String userId = null;

        Optional<User> currUser = userRepo.findUserByUsername(userName);

        if (currUser.isEmpty()) {
            System.out.printf(">>>>>>>>>is empty!!");
            User newUser = new User();
            newUser.setUsername(userName);
            userId = userRepo.insertUser(newUser);
            System.out.printf(">>>>>>>>>userid: %s\n", userId);

        } else {
            userId = currUser.get().getUserId();
        }

        int isUpdated = taskRepo.insertTask(taskList, userId);

        if (isUpdated > 0) {
            return isUpdated;
        } else {
            return 0;
        }
    }
}

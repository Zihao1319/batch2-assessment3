package ibf2022.paf.assessment.server.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;
import jakarta.servlet.http.HttpServletResponse;

// TODO: Task 4, Task 8
@Controller
@RequestMapping("/")
public class TasksController {

    @Autowired
    private TodoService todoSvc;

    @PostMapping("/task")
    public ModelAndView postTask(@RequestBody MultiValueMap<String, String> data, HttpServletResponse response)
            throws ParseException {

        List<Task> taskLists = new LinkedList<Task>();

        int columns = data.size() / 3;

        int currIndex = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String userName = data.getFirst("username");

        while (currIndex < columns) {
            Task task = new Task();
            task.setDescription(data.getFirst("description-%d".formatted(currIndex)));
            // System.out.printf(">>>>>>>> description: %s\n",
            // data.getFirst("description-%d".formatted(currIndex)));

            Date dueDate = dateFormat.parse(data.getFirst("dueDate-%d".formatted(currIndex)));
            task.setDueDate(dueDate);
            // System.out.printf(">>>>>>>> date: %s\n",
            // data.getFirst("dueDate-%d".formatted(currIndex)));

            Integer priority = Integer.parseInt(data.getFirst("priority-%d".formatted(currIndex)));
            task.setPriority(priority);
            // System.out.printf(">>>>>>>> priority: %s\n",
            // data.getFirst("priority-%d".formatted(currIndex)));

            taskLists.add(task);
            currIndex += 1;
        }

        Integer iTaskCount = todoSvc.upsertTask(userName, taskLists);

        if (iTaskCount > 0) {
            ModelAndView modelAndView = new ModelAndView("result");
            modelAndView.addObject("taskCount", iTaskCount.toString());
            modelAndView.addObject("username", userName);
            response.setStatus(200);
            return modelAndView;

        } else {

            ModelAndView modelAndView = new ModelAndView("error");
            response.setStatus(500);
            return modelAndView;

        }

    }

}

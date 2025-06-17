package com.example.mockPortoData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @GetMapping("/projects")
    public ProjectInterface projects() throws InterruptedException {
        Thread.sleep(2000); // simulate delay

        List<ProjectInterfaceItem> respArray = new ArrayList<>();
        respArray.add(new ProjectInterfaceItem(13, "apel", "desc"));
        respArray.add(new ProjectInterfaceItem(14, "jeru", "desc"));

        // Convert List to Array
        ProjectInterfaceItem[] itemsArray = respArray.toArray(new ProjectInterfaceItem[0]);

        // Wrap in response object
        return new ProjectInterface(itemsArray);
    }
}

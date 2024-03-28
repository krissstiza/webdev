package com.example.WebLab1.Controller;

import com.example.WebLab1.Model.Project;
import com.example.WebLab1.Service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project newProject) {
        return new ResponseEntity<Project>(projectService.createProject(newProject), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.readProject(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project newProject) {
        return ResponseEntity.ok(projectService.updateProject(id, newProject));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getFilter(
        @RequestParam("start_date") LocalDate startLocalDate,
        @RequestParam("end_date") LocalDate endLocalDate
    ) {
        Date startDate = Date.from(startLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        return ResponseEntity.ok(projectService.readFilter(startDate, endDate));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
    //    jdbcTemplate.queryForRowSet("SELECT * FROM project LEFT OUTER JOIN task ON project.id = task.projectId;");
        return ResponseEntity.ok(projectService.readAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

package com.ana.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ana.crudspring.model.Course;
import com.ana.crudspring.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public @ResponseBody List<Course> list(){
    // public List<Course> list(){
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id){
        return courseRepository.findById(id)
        .map(recordFound -> ResponseEntity.ok().body(recordFound))
        .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course){
        return courseRepository.save(course);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id,
                                             @RequestBody Course course) {
    return courseRepository.findById(id)
    .map(
        recordFound -> {
            recordFound.setName((course.getName()));
            recordFound.setCategory(course.getCategory());
            Course updated = courseRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
        }
        )
        .orElse(ResponseEntity.notFound().build());
   }


   @DeleteMapping("/{id}")
   public ResponseEntity<?>  delete(@PathVariable Long id){
    courseRepository.deleteById(id);
       return courseRepository.findById(id)
               .map(
                       recordFound -> {
                           courseRepository.deleteById((id));
                           return ResponseEntity.noContent().build();
                       }
               )
               .orElse(ResponseEntity.notFound().build());
   }
}   
    




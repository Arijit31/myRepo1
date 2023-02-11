package com.Rest_Example.CourseList.controller;
import com.Rest_Example.CourseList.customException.ControllerException;
import com.Rest_Example.CourseList.customException.ServiceException;
import com.Rest_Example.CourseList.entity.Courses;
import com.Rest_Example.CourseList.service.Course_Services;
import com.Rest_Example.CourseList.utility.GlobalUse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;




@RestController
//For taking request from the client side.
public class Course_Controller {

    @Autowired
    private Course_Services cs;


    private final Logger controlLog = GlobalUse.getLogger(Course_Controller.class);




    @GetMapping("/allCourses")
    //getCourse method collect all the courses from database
    public ResponseEntity<?> getCourse(){
        String method = "getCourse()";
        try{
            controlLog.info("Controller Called {}",method);
            return new ResponseEntity<>(this.cs.getCourse(),HttpStatus.FOUND);
        }catch(ServiceException e){
          ControllerException ce = new ControllerException(e.getError_code(),e.getError_des());
          return new ResponseEntity<>(ce,HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            controlLog.error("Controller Called {}",method);
            return new ResponseEntity<>("Server Problem",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @PostMapping("/add")
    //addCourses method for adding new course
    public ResponseEntity<?> addCourse(@RequestBody Courses newOne){
        String method = "addCourse";
        try{

            controlLog.info("Control Called {} with {}",method,newOne);
            return new ResponseEntity<>(this.cs.addCourse(newOne),HttpStatus.ACCEPTED);
        }catch(ServiceException e){
            ControllerException ce = new ControllerException(e.getError_code(),e.getError_des());
            return new ResponseEntity<>(ce,HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e){
            controlLog.error("Control Called {} with {}",method,newOne);
            return new ResponseEntity<>("Server Problem",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @GetMapping("/allCourses/{id}")
    //singleCourse for giving  a specific course by id
    public ResponseEntity<?> singleCourse(@PathVariable long id){
          String method = "singleCourse";
        try{
            controlLog.info("Controller {} Called with {}",method,id);
            return new ResponseEntity<>(this.cs.singleCourse(id),HttpStatus.FOUND);
        }catch(ServiceException e){
            ControllerException ce = new ControllerException(e.getError_code(),e.getError_des());
            return new ResponseEntity<>(ce,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            controlLog.error("Controller called {} with {}",method,e.getMessage());
            return new ResponseEntity<>("Can't process this request",HttpStatus.BAD_REQUEST);
        }
    }





    @DeleteMapping("/deleteOne/{id}")
    //Delete a specific course by its id
    public ResponseEntity<?> deleteCourse(@PathVariable long id){
        String method = "deleteOne";

        try{
            this.cs.deleteCourse(id);
            controlLog.info("Controller {} Called with {}",method,id);
            return new ResponseEntity<>( "Delete successfully",HttpStatus.ACCEPTED);

        }catch(ServiceException e){
            ControllerException ce = new ControllerException(e.getError_code(),e.getError_des());
            return new ResponseEntity<>(ce,HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>("Can't process your request",HttpStatus.BAD_REQUEST);
        }

    }
}

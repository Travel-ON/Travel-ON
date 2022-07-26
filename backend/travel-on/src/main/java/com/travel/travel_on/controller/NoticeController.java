package com.travel.travel_on.controller;

import com.travel.travel_on.dto.FAQ;
import com.travel.travel_on.dto.Notice;
import com.travel.travel_on.model.service.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/notice")
public class NoticeController {


    @Autowired
    private NoticeServiceImpl nsvc;

    @PostMapping
    public ResponseEntity<?> selectList(){
        try{
            List<Notice> result = nsvc.select();
            return new ResponseEntity<List<Notice>>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(Notice notice){
        try{
            int result = nsvc.write(notice);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<?> select(@PathVariable Integer noticeId){
        try{
            Notice result = nsvc.selectOne(noticeId);
            return new ResponseEntity<Notice>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> modify(Notice notice){
        try{
            int result = nsvc.update(notice);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> delete(@PathVariable Integer noticeId){
        try{
            int result = nsvc.delete(noticeId);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @PostMapping("/faq")
    public ResponseEntity<?> selectFAQList(){
        try{
            List<FAQ> result = nsvc.selectFAQ();
            return new ResponseEntity<List<FAQ>>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

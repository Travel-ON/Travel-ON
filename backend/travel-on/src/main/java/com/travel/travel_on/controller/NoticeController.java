package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.FAQ;
import com.travel.travel_on.entity.Notice;
import com.travel.travel_on.model.service.NoticeService;
import com.travel.travel_on.model.service.UserService;

import io.swagger.annotations.ApiOperation;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;

import java.util.Date;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/notice")
@Slf4j
public class NoticeController {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private NoticeService nsvc;

    @Autowired
    private UserService usvc;

    @ApiOperation(value = "글리스트 조회: 공지사항 글 조회 및 페이징", response = Board.class )
    @GetMapping("/page") //페이징 디폴트 10개씩
    public ResponseEntity<?> selectPage(@PageableDefault(sort = "noticeId")Pageable pageable){
        Board result = new Board();
        result.P = nsvc.findPage(pageable);
        result.previous = pageable.previousOrFirst().getPageNumber();
        result.next = pageable.next().getPageNumber();

        return new ResponseEntity<Board>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "글쓰기: 공지사항을 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@ApiIgnore Authentication authentication, @RequestBody Notice notice){
        try{
            log.info("공지사항 글쓰기");
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = usvc.select(userId);
            if(userDto.isAdminFlag()) {
                Date time = new Date();
                String nowTime = format.format(time);
                notice.setHits(0);
                notice.setNoticeDate(nowTime);
                notice.setFixationFlag(false);
                boolean result = nsvc.write(notice);
                if (result) {
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 글 조회", response = Notice.class)
    @GetMapping("/detail/{noticeId}")
    public ResponseEntity<?> select(@PathVariable Integer noticeId){
        try{
            Notice result = nsvc.selectOne(noticeId);

            if(result == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                result.setHits(result.getHits()+1);
                nsvc.update(result);
                return new ResponseEntity<Notice>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: 공지사항 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@ApiIgnore Authentication authentication, @RequestBody Notice notice){
        try{
            log.info("공지사항 수정");
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = usvc.select(userId);
            if(userDto.isAdminFlag()) {
                boolean result = nsvc.update(notice);
                if(result){
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: 글 삭제")
    @DeleteMapping("delete/{noticeId}")
    public ResponseEntity<?> delete(@ApiIgnore Authentication authentication, @PathVariable Integer noticeId){
        try{
            log.info("공지사항 삭제");
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = usvc.select(userId);
            if(userDto.isAdminFlag()) {
                boolean result = nsvc.delete(noticeId);
                if(result){
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }else{
                    return  new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "FAQ 리스트 조회: FAQ 글 조회 및 페이징, 검색", response = FAQBoard.class)
    @PostMapping("/faq")
    public ResponseEntity<?> searchFAQ(String keyword, @PageableDefault(sort = "faqId")Pageable pageable){
        FAQBoard result = new FAQBoard();
        result.PF = nsvc.search(keyword, pageable);
        result.previous = pageable.previousOrFirst().getPageNumber();
        result.next = pageable.next().getPageNumber();

        return new ResponseEntity<FAQBoard>(result, HttpStatus.OK);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    static class Board{
        Page<Notice> P;
        int previous;
        int next;
    }

    static class FAQBoard{
        Page<FAQ> PF;
        int previous;
        int next;
    }
}



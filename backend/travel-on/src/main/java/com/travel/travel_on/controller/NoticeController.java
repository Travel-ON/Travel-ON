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

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/notice")
@Slf4j
public class NoticeController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "글리스트 조회: 공지사항 글 조회 및 페이징", response = Board.class )
    @GetMapping("/page") //페이징 디폴트 10개씩
    public ResponseEntity<?> selectPage(@PageableDefault(sort = "noticeId")Pageable pageable){
        Board result = new Board();
        result.P = noticeService.findPage(pageable);
        result.previous = pageable.previousOrFirst().getPageNumber();
        result.next = pageable.next().getPageNumber();

        return new ResponseEntity<Board>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "글쓰기: 공지사항을 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@ApiIgnore Authentication authentication, @RequestBody Notice notice){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto.isAdminFlag()) {
                Date time = new Date();
                String nowTime = simpleDateFormat.format(time);
                notice.setHits(0);
                notice.setNoticeDate(nowTime);
                notice.setFixationFlag(false);
                boolean result = noticeService.write(notice);
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
            Notice result = noticeService.selectOne(noticeId);

            if(result == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                result.setHits(result.getHits()+1);
                noticeService.update(result);
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
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto.isAdminFlag()) {
                boolean result = noticeService.update(notice);
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
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);
            if(userDto.isAdminFlag()) {
                boolean result = noticeService.delete(noticeId);
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

    @ApiOperation(value = "FAQ 리스트 조회: FAQ 글 조회 및 페이징", response = FAQBoard.class)
    @GetMapping("/faq")
    public ResponseEntity<?> searchFAQ(@PageableDefault(sort = "faqId")Pageable pageable){
        FAQBoard result = new FAQBoard();
        result.PF = noticeService.faqPage(pageable);
        result.previous = pageable.previousOrFirst().getPageNumber();
        result.next = pageable.next().getPageNumber();

        return new ResponseEntity<FAQBoard>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "FAQ 리스트 조회: FAQ 글 조회 및 페이징, 검색", response = FAQBoard.class)
    @PostMapping("/faq/search")
    public ResponseEntity<?> searchFAQ(@RequestParam("key") String keyword, @PageableDefault(sort = "faqId")Pageable pageable){
        FAQBoard result = new FAQBoard();
        result.PF = noticeService.search(keyword, pageable);
        result.previous = pageable.previousOrFirst().getPageNumber();
        result.next = pageable.next().getPageNumber();

        return new ResponseEntity<FAQBoard>(result, HttpStatus.OK);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    static class Board{
        Page<Notice> P;
        int previous;
        int next;

        public Board() {}

        public Board(Page<Notice> P, int previous, int next){
            P = this.P;
            previous = this.previous;
            next = this.next;
        }
    }

    @Getter
    @Setter
    static class FAQBoard{
        Page<FAQ> PF;
        int previous;
        int next;

        public  FAQBoard(){}

        public FAQBoard(Page<FAQ> PF, int previous, int next){
            PF = this.PF;
            previous = this.previous;
            next = this.next;
        }
    }
}




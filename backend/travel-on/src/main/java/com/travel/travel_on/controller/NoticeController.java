package com.travel.travel_on.controller;

import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.FAQ;
import com.travel.travel_on.entity.Notice;
import com.travel.travel_on.model.service.NoticeServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeServiceImpl nsvc;

    @ApiOperation(value = "글리스트 조회: 공지사항 글 조회 및 페이징", response = Board.class )
    @GetMapping("/page") //페이징 디폴트 10개씩
    public ResponseEntity<?> selectPage(@PageableDefault(sort = "noticeId")Pageable pageable){
        Board result = new Board();
        result.P = nsvc.findPage(pageable); // 페이징
        result.previous = pageable.previousOrFirst().getPageNumber(); // 이전버튼용
        result.next = pageable.next().getPageNumber(); // 다음버튼용

        return new ResponseEntity<Board>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "글쓰기: 공지사항을 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> regist(Notice notice){
        try{
            boolean result = nsvc.write(notice);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
                return new ResponseEntity<Notice>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: 공지사항 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(Notice notice){
        try{
            boolean result = nsvc.update(notice);
            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: 글 삭제")
    @DeleteMapping("delete/{noticeId}")
    public ResponseEntity<?> delete(@PathVariable Integer noticeId){
        try{
            boolean result = nsvc.delete(noticeId);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return  new ResponseEntity<>(HttpStatus.CONFLICT);
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
        result.previous = pageable.previousOrFirst().getPageNumber(); // 이전 버튼용
        result.next = pageable.next().getPageNumber(); // 다음 버튼용

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

        public Board() {

        }

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

        public  FAQBoard(){

        }

        public FAQBoard(Page<FAQ> PF, int previous, int next){
            PF = this.PF;
            previous = this.previous;
            next = this.next;
        }
    }
}



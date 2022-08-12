package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.QNADto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.QNA;
import com.travel.travel_on.model.service.AlarmService;
import com.travel.travel_on.model.service.QNAService;
import com.travel.travel_on.model.service.UserService;

import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/qna")
@Slf4j
public class QNAController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private UserService userService;

    @Autowired
    private QNAService qnaService;

    @Autowired
    private AlarmService alarmService;

    @ApiOperation(value = "QNA 리스트 조회: QNA 글 조회(검색가능)", response = List.class)
    @GetMapping("/")
    public ResponseEntity<?> searchQNA(@ApiIgnore Authentication authentication, @RequestParam(value = "keyword", required = false)String keyword){
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<QNA> list;
            if(keyword == null || keyword.isEmpty()){
                list = qnaService.selectAll(userDto.toEntity(), "null");
            }else{
                list = qnaService.selectAll(userDto.toEntity(), keyword);
            }
            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글쓰기: QNA를 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Date time = new Date();
            String nowTime = simpleDateFormat.format(time);
            QNADto qnaDto = new QNADto(0,userId,userDto.getNickname(),param.get("title"),param.get("content"),nowTime,false,null,null);

            boolean result = qnaService.write(userDto, qnaDto);

            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 QNA 조회", response = QNADto.class)
    @GetMapping("/detail/{qnaId}")
    public ResponseEntity<?> select(@ApiIgnore Authentication authentication, @PathVariable Integer qnaId){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            QNADto result = qnaService.selectOne(qnaId);

            if(!userDto.isAdminFlag() && !userId.equals(result.getRealId())){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            if(result == null){
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<QNADto>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 회원 답변완료글 조회", response = List.class)
    @GetMapping("/answer/complete")
    public ResponseEntity<?> completeSelect(@ApiIgnore Authentication authentication){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto.isAdminFlag()){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            
            List<QNA> list = qnaService.AnswerAll(userDto.toEntity());

            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: QNA 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();

            QNADto qnaDto = qnaService.selectOne(Integer.parseInt(param.get("qnaId")));

            if(!userId.equals(qnaDto.getRealId())){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if(qnaDto.isAnswerFlag()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            qnaDto.setTitle(param.get("title"));
            qnaDto.setContent(param.get("content"));
            boolean result = qnaService.update(qnaDto);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: QNA 삭제")
    @DeleteMapping("delete/{qnaId}")
    public ResponseEntity<?> delete(@ApiIgnore Authentication authentication, @PathVariable Integer qnaId){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();

            QNADto qnaDto = qnaService.selectOne(qnaId);

            if(!userId.equals(qnaDto.getRealId())){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            boolean result = qnaService.delete(qnaId);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 관리자모드 QNA리스트 조회", response = List.class)
    @GetMapping("/admin")
    public ResponseEntity<?> adminSelect(@ApiIgnore Authentication authentication, @RequestParam(value = "keyword", required = false)String keyword){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(!userDto.isAdminFlag()){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            List<QNA> list;
            if(keyword == null || keyword.isEmpty()){
                list = qnaService.adminSelectAll("null");
            }else{
                list = qnaService.adminSelectAll(keyword);
            }
            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 관리자모드 답변대기글 조회", response = List.class)
    @GetMapping("/admin/answer")
    public ResponseEntity<?> adminSelect(@ApiIgnore Authentication authentication){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(!userDto.isAdminFlag()){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            List<QNA> list = qnaService.noneAnswerAll();

            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: QNA 관리자 답변 등록")
    @Transactional
    @PutMapping("/admin/regist")
    public ResponseEntity<?> adminRegist(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(!userDto.isAdminFlag()){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            QNADto qnaDto = qnaService.selectOne(Integer.parseInt(param.get("qnaId")));

            if(qnaDto.isAnswerFlag()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Date time = new Date();
            String nowTime = simpleDateFormat.format(time);
            qnaDto.setAnswerFlag(true);
            qnaDto.setAnswer(param.get("answer"));
            qnaDto.setAnswerDate(nowTime);

            boolean result = qnaService.update(qnaDto);
            alarmService.insert(userDto.toEntity(),"Q&A : 답변이 등록되었습니다!");
            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: QNA 관리자 답변 수정")
    @PutMapping("/admin/modify")
    public ResponseEntity<?> adminModify(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(!userDto.isAdminFlag()){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            QNADto qnaDto = qnaService.selectOne(Integer.parseInt(param.get("qnaId")));
            if(!qnaDto.isAnswerFlag()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            qnaDto.setAnswer(param.get("answer"));

            boolean result = qnaService.update(qnaDto);
            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: QNA 관리자 답변 삭제")
    @PutMapping("/admin/delete/{qnaId}")
    public ResponseEntity<?> adminDelete(@ApiIgnore Authentication authentication, @PathVariable Integer qnaId){
        try{
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(!userDto.isAdminFlag()){
                return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            QNADto qnaDto = qnaService.selectOne(qnaId);
            if(!qnaDto.isAnswerFlag()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            qnaDto.setAnswerDate(null);
            qnaDto.setAnswer(null);
            qnaDto.setAnswerFlag(false);

            boolean result = qnaService.update(qnaDto);
            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

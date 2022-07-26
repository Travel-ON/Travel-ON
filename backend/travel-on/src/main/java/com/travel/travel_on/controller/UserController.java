package com.travel.travel_on.controller;

import com.travel.travel_on.dto.User;
import com.travel.travel_on.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin ( origins={"*"}, maxAge = 6000 )
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService usvc;

    @ApiOperation(value="회원가입: 사용자 정보를 삽입한다", response = Integer.class )
    @PostMapping("/regist")
    public ResponseEntity<?> regist(User user){
        try{
            int result = usvc.insert(user);
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value="로그인: 사용자 정보를 조회한다", response = Integer.class )
    @PostMapping("/login")
    public ResponseEntity<?> login(String id, String password){
        try{
            User user= usvc.select(id);
            int result=0;
            if(user!=null && user.getPassword().equals(password)){
                return new ResponseEntity<Integer>(result, HttpStatus.OK);
            }else{
                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value="아이디 중복검사: 회원 정보에 중복되는 {id}가 있는지 조회한다", response = Integer.class )
    @PostMapping("/idcheck")
    public ResponseEntity<?> idcheck(String id){
        try{
            User user= usvc.select(id);
            int result=0;
            if(user!=null){
                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
            }else{ // 아이디 사용 가능: 0 반환
                return new ResponseEntity<Integer>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value="닉네임 중복검사: 회원 정보에 중복되는 {nickname}이 있는지 조회한다", response = Integer.class )
    @PostMapping("/nickcheck")
    public ResponseEntity<?> nickcheck(String nickname){
        try{
            User user= usvc.selectByNickname(nickname);
            int result=0;
            if(user!=null){
                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
            }else{ // 닉네임 사용 가능: 0 반환
                return new ResponseEntity<Integer>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value="회원정보 조회: 사용자 정보를 조회한다", response = User.class )
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable String id){
        try{
            User user= usvc.select(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value="회원정보 수정: 사용자 정보를 수정한다", response = Integer.class )
    @PutMapping("/modify")
    public ResponseEntity<?> modify(User user){
        try{
            int result = usvc.update(user);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value="회원 탈퇴: 사용자 정보를 삭제한다", response = Integer.class )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            int result = usvc.delete(id);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }
    /********/
//    @ApiOperation(value="비밀번호 찾기: ", response = Integer.class )
//    @PostMapping("/email")
//    public ResponseEntity<?> resetPassword(String nickname){
//        try{
//            User user= usvc.selectByNickname(nickname);
//            int result=0;
//            if(user!=null){
//                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
//            }else{ // 닉네임 사용 가능: 0 반환
//                return new ResponseEntity<Integer>(result, HttpStatus.OK);
//            }
//        }catch (Exception e){
//            return exceptionHandling(e);
//        }
//    }

    @ApiOperation(value="칭호 변경: 사용자 칭호를 수정한다", response = Integer.class )
    @PutMapping("/title")
    public ResponseEntity<?> modifyTitle(String id, String title){
        try{
            User user = usvc.select(id);
            user.setUserTitle(title);
            int result = usvc.update(user);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    /********/
//    @ApiOperation(value="칭호 조회: 사용자가 얻은 칭호리스트를 조회한다", response = Integer.class )
//    @PostMapping("/title")
//    public ResponseEntity<?> selectTitle(User user){
//        try{
//            int result = usvc.update(user);
//            return new ResponseEntity<Integer>(result, HttpStatus.OK);
//        }catch (Exception e){
//            return exceptionHandling(e);
//        }
//    }

    /********/
//    @ApiOperation(value="여행횟수 조회: 사용자의 지역별 여행횟수를 조회한다", response = User.class )
//    @GetMapping("/trophy/{id}")
//    public ResponseEntity<?> trophy(@PathVariable String id){
//        try{
//            User user= usvc.select(id);
//            return new ResponseEntity<User>(user, HttpStatus.OK);
//        }catch (Exception e){
//            return exceptionHandling(e);
//        }
//    }

    /********/
    //위치 인증
    //@ApiOperation

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

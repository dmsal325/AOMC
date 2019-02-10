package com.aomc.coop.controller;

import com.aomc.coop.model.User;
import com.aomc.coop.model.UserWithToken;
import com.aomc.coop.response.Status_common;
import com.aomc.coop.service.MemberService;
import com.aomc.coop.utils.CodeJsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//


@RestController
@RequestMapping("/members")
public class MemberController {

    CodeJsonParser codeJsonParser = CodeJsonParser.getInstance();

    @Autowired
    private MemberService memberService;

    // 회원 가입
    @PostMapping
    @CrossOrigin
    public ResponseEntity register(@RequestBody User user) { // header, body(json), HTTP.status //
        try {
            return new ResponseEntity(memberService.register(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(codeJsonParser.codeJsonParser(Status_common.INTERNAL_SERVER_ERROR.getStatus()), HttpStatus.OK);
        }
    }

    // 회원 가입 시 이메일 인증
    @GetMapping(path="/{authUrl}")
    @CrossOrigin
    public ResponseEntity emailAuth(@PathVariable(value = "authUrl") String authUrl) { // header, body(json), HTTP.status //
        try {
            return new ResponseEntity(memberService.emailAuth(authUrl), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(codeJsonParser.codeJsonParser(Status_common.INTERNAL_SERVER_ERROR.getStatus()), HttpStatus.OK);
        }
    }

    // 회원 탈퇴
    @PutMapping(path="/{idx}")
    @CrossOrigin
    public ResponseEntity withdrawal(@RequestBody UserWithToken userWithToken, @PathVariable(value = "idx") int idx) { // header, body(json), HTTP.status //
        try {
            return new ResponseEntity(memberService.withdrawal(userWithToken, idx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(codeJsonParser.codeJsonParser(Status_common.INTERNAL_SERVER_ERROR.getStatus()), HttpStatus.OK);
        }
    }

    // 비밀번호 변경
//    @PutMapping(path="/pwd/{idx}")
//    @CrossOrigin
//    public ResponseEntity changePwd(@RequestBody User user, @PathVariable(value = "idx") int idx) { // header, body(json), HTTP.status //
//        try {
//            return new ResponseEntity(memberService.changePwd(user, idx), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(codeJsonParser.codeJsonParser(Status_common.INTERNAL_SERVER_ERROR.getStatus()), HttpStatus.OK);
//        }
//    }


    // 비밀번호 분실 후 변경



}

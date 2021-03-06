package com.aomc.coop.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import com.aomc.coop.model.Message;
import com.aomc.coop.response.Status_common;
import com.aomc.coop.utils.CodeJsonParser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aomc.coop.service.StorageService;
import com.aomc.coop.storage.StorageFileNotFoundException;

@CrossOrigin
@Controller
@RequestMapping("/api/files")
public class FileServerController {

    private final StorageService storageService;

    CodeJsonParser codeJsonParser = CodeJsonParser.getInstance();

    @Autowired
    public FileServerController(StorageService storageService) {
        this.storageService = storageService;
    }
// ***** Interceptor를 쓰면서 REST API가 깨졌다(url에 upload download를 명시해버림). 오히려 REST API와 맞는 방법인지, 아니라면 더 나은 방법은 있을지 고민해볼 것

    @PostMapping(path = "/upload/{channel_idx}")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file, @RequestParam("message") String stringMessage, @PathVariable final int channel_idx) throws IOException {
        Message message  = new ObjectMapper().readValue(stringMessage, Message.class);

        try {
            return new ResponseEntity(storageService.upload(file, message, channel_idx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(codeJsonParser.codeJsonParser(Status_common.INTERNAL_SERVER_ERROR.getStatus()), HttpStatus.OK);
        }
    }

// ***** 아래 두 Controller들도 ResponseEntity, codeJsonParser 양식에 맞게 바꾸기
    @GetMapping(path = "/download/{channel_idx}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable String filename, @PathVariable final int channel_idx) {

        Resource file = storageService.download(filename, channel_idx);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    // profile picture를 업로드 하는 @PostMapping
    @PostMapping(path = "/upload/profile/{user_idx}")
    public ResponseEntity uploadProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable final int user_idx) throws IOException {

        try {
            return new ResponseEntity(storageService.uploadProfilePicture(file, user_idx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(codeJsonParser.codeJsonParser(Status_common.INTERNAL_SERVER_ERROR.getStatus()), HttpStatus.OK);
        }
    }

    // profile picture를 다운로드 하는 @GetMapping
    @GetMapping(path = "/download/profile/{filename:.+}")
    public ResponseEntity downloadProfilePicture(@PathVariable String filename) throws IOException {
        Resource file = storageService.downloadProfilePicture(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }



//
//    // GET / looks up the current list of uploaded files from
//    // the StorageService and loads it into a Thymeleaf template.
//    // It calculates a link to the actual resource using MvcUriComponentsBuilder
//
//// ***** 여기서 File Server의 URL을 생성한다.
//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//// ***** filename : 한글 파일명은 제대로 읽혀지지 않으므로, UTF-8 처리가 필요하다.
//        model.addAttribute("files", storageService.getAllFilesPaths().map(
//                path -> MvcUriComponentsBuilder.fromMethodName
//                        (FileUploadController.class,
//                                "download",
//                                path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//
//// * model.addAttribute(String name, Object value)
//// name 이름으로 value 객체를 추가한다
//
//// * Stream.map
//// 맵(map)은 스트림 내 요소들을 하나씩 특정 값으로 변환해줍니다.
//// 스트림에 들어가 있는 값이 input 이 되어서 특정 로직을 거친 후 output 이 되어
//// (리턴되는) 새로운 스트림에 담기게 됩니다.
//// 이러한 작업을 맵핑(mapping)이라고 합니다.
//
//// * MvcUriComponentsBuilder.fromMethodName
//// Create a UriComponentsBuilder from the mapping of a controller method and an array of method argument values.
////  Returns:
//// a UriComponentsBuilder instance, never null
//
//        return "uploadForm";
//    }


}
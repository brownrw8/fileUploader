package com.brownrw8.fileUploader.controller;

import com.brownrw8.fileUploader.dto.FileEntityDto;
import com.brownrw8.fileUploader.service.FileService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;

/*
 *
 *  REST service for files - allows to upload and get list of files.
 *
 */
@RestController
@JsonSerialize
@RequestMapping("file")
public class FileController {

    Logger LOGGER = Logger.getLogger(FileController.class);

    @Autowired
    private MultipartResolver multipartResolver;

    @Autowired
    private FileService fileService;

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity handleFileNotFound(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * get list of file entity
     *
     *
     * @return - ResponseEntity<> a list of file entities
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity listFiles(){
        return new ResponseEntity<>(fileService.getFiles(), HttpStatus.OK);
    }

    /**
     *
     * saves a new file entity
     *
     * @param fileEntityDto - the file entity to be saved
     * @return - the file entity that was saved successfully
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity saveFile(@RequestBody @Valid FileEntityDto fileEntityDto) {
        return new ResponseEntity<>(fileService.saveFile(fileEntityDto), HttpStatus.OK);
    }

    /**
     *
     * downloads a file
     *
     * @param filename - the filename of the file to be served
     * @return - downloads the file
     */
    @RequestMapping(value = "/download/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity downloadFile(@PathVariable String filename) throws FileNotFoundException {
        FileEntityDto fileEntityDto = fileService.getFileByName(filename);
        if(fileEntityDto == null){
            throw new FileNotFoundException();
        }
        Resource file = fileService.getFileAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; " +
                        "filename=\""+fileEntityDto.getOriginalName()+"\"")
                .body(file);
    }

    /**
     *
     * uploads a file
     *
     * @param file - the file to be uploaded
     * @return - uploads the file
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("file");
        return new ResponseEntity<>(fileService.storeFile(file), HttpStatus.OK);
    }


}

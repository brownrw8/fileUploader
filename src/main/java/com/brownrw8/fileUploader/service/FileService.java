package com.brownrw8.fileUploader.service;


import com.brownrw8.fileUploader.dto.FileEntityDto;
import com.brownrw8.fileUploader.dto.FileUploadDto;
import com.brownrw8.fileUploader.entity.FileEntity;
import com.brownrw8.fileUploader.repository.FileRepository;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private static final Logger LOGGER = Logger.getLogger(FileService.class);

    @Value("${fileUploader.localUploadDirectory}")
    private String localUploadDirectory;

    @Autowired
    FileRepository fileRepository;

    public FileEntityDto saveFile(FileEntityDto fileEntityDto){
        FileEntity fileEntity = new FileEntity(fileEntityDto);
        FileEntity savedFileEntity = fileRepository.save(fileEntity);
        FileEntityDto savedFileEntityDto = null;
        if(savedFileEntity!=null){
            savedFileEntityDto = new FileEntityDto(savedFileEntity);
        }
        return savedFileEntityDto;
    }

    public List<FileEntityDto> getFiles(){
        List<FileEntity> fileEntities = fileRepository.findAll();
        List<FileEntityDto> fileEntityDtos = new ArrayList<>();
        fileEntities.forEach(fileEntity -> {
            fileEntityDtos.add(new FileEntityDto(fileEntity));
        });
        return fileEntityDtos;
    }

    public FileEntityDto getFileByName(String name){
        FileEntity fileEntity = fileRepository.findByName(name);
        FileEntityDto fileEntityDto = null;
        if(fileEntity!=null){
            fileEntityDto = new FileEntityDto(fileEntity);
        }
        return fileEntityDto;
    }

    public Resource getFileAsResource(String fileName){
        File file  = new File(localUploadDirectory + File.separator + fileName);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            LOGGER.error("Could not read file " + fileName);
        }
        return resource;
    }

    public FileUploadDto storeFile(MultipartFile uploadFile){
        File safeDestination = null;
        File localUploadLocation = null;
        FileUploadDto fileUploadDto = new FileUploadDto(
            uploadFile.getOriginalFilename(),
            uploadFile.getContentType(),
            uploadFile.getSize()
        );
        fileUploadDto.setValid(true);
        try{
            localUploadLocation = new File(localUploadDirectory);
        } catch (NullPointerException e){
            LOGGER.error("Could not secure the tmp file location " + localUploadDirectory);
            fileUploadDto.setValid(false);
        }
        if(fileUploadDto.getValid()){
            try {
                LOGGER.debug("Attempting copy job for " + fileUploadDto.toString() + " to local upload " +
                        "directory " + localUploadDirectory);
                safeDestination = File.createTempFile("AFU.", "", localUploadLocation);
            } catch (IOException e) {
                LOGGER.error("Could not secure tmp file for " + fileUploadDto.toString());
                fileUploadDto.setValid(false);
            }
        }
        if(fileUploadDto.getValid()){
            if (safeDestination != null) {
                fileUploadDto.setSafeFileName(safeDestination.getName());
            }else{
                LOGGER.error("Could not secure safe file name for " + fileUploadDto.toString());
                fileUploadDto.setValid(false);
            }
        }
        if(fileUploadDto.getValid()){
            try {
                uploadFile.transferTo(safeDestination);
            } catch (IOException e) {
                LOGGER.error("Could not copy to safe location for " + fileUploadDto.toString());
                fileUploadDto.setValid(false);
            }
        }
        return fileUploadDto;
    }

}

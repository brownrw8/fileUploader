package com.brownrw8.fileUploader.dto;


import com.brownrw8.fileUploader.entity.FileEntity;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FileEntityDto implements Serializable{

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String mimeType;
    @NotNull
    private String title;
    private String description;
    @NotNull
    private String originalName;
    private DateTime uploaded;

    protected FileEntityDto() {}

    public FileEntityDto(String name, String mimeType, String title, String description, String originalName) {
        this.name = name;
        this.mimeType = mimeType;
        this.title = title;
        this.description = description;
        this.originalName = originalName;
    }

    public FileEntityDto(FileEntity fileEntity){
        this.id = fileEntity.getId();
        this.name = fileEntity.getName();
        this.mimeType = fileEntity.getMimeType();
        this.title = fileEntity.getTitle();
        this.description = fileEntity.getDescription();
        this.originalName = fileEntity.getOriginalName();
        this.uploaded = fileEntity.getUploaded();
    }

    @Override
    public String toString() {
        return String.format(
                "FileEntityDto[id=%d, name='%s', description='%s', originalName='%s']",
                id, name, description, originalName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FileEntityDto)) {
            return false;
        }

        return (name != null
                ? name.equals(((FileEntityDto) obj).getName())
                : ((FileEntityDto) obj).getName() == null);
    }

    @Override
    public int hashCode() {
        int hash = name != null ? name.hashCode() : 0;
        hash = 31 * hash;
        return hash;
    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginalName() {
        return originalName;
    }

    public DateTime getUploaded(){
        return uploaded;
    }
}

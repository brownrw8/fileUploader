package com.brownrw8.fileUploader.entity;


import com.brownrw8.fileUploader.dto.FileEntityDto;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FileEntity implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "mimeType", length = 255)
    private String mimeType;
    @Column(name = "title", length = 255)
    private String title;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "originalName", length = 255)
    private String originalName;
    @Column(name = "uploaded")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime uploaded;

    protected FileEntity() {}

    public FileEntity(String name, String mimeType, String title, String description, String originalName) {
        this.name = name;
        this.mimeType = mimeType;
        this.title = title;
        this.description = description;
        this.originalName = originalName;
        this.uploaded = new DateTime();
    }

    public FileEntity(FileEntityDto fileEntityDto){
        this.name = fileEntityDto.getName();
        this.mimeType = fileEntityDto.getMimeType();
        this.title = fileEntityDto.getTitle();
        this.description = fileEntityDto.getDescription();
        this.originalName = fileEntityDto.getOriginalName();
        this.uploaded = new DateTime();
    }

    @Override
    public String toString() {
        return String.format(
                "FileEntity[id=%d, name='%s', description='%s', originalName='%s', uploaded='%s']",
                id, name, description, originalName,
                new DateTime( uploaded ).toString("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FileEntity)) {
            return false;
        }

        return id != null
                ? id.equals(((FileEntity) obj).getId())
                : ((FileEntity) obj).getId() != null;
    }

    @Override
    public int hashCode() {
        int hash = id != null ? id.hashCode() : 0;
        hash = 31 * hash;
        return hash;
    }

    public Long getId() {
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

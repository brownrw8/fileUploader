package com.brownrw8.fileUploader.dto;


import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import java.io.Serializable;

public class FileUploadDto implements Serializable{

    private final String originalFileName;
    private final String mimeType;
    private final Long size;
    private String safeFileName;
    private Boolean isValid = false;

    public FileUploadDto(String originalFileName, String mimeType, Long size){
        this.originalFileName = originalFileName;
        this.mimeType = mimeType;
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FileUploadDto)) {
            return false;
        }

        return (safeFileName != null
                ? safeFileName.equals(((FileUploadDto) obj).getSafeFileName())
                : ((FileUploadDto) obj).getSafeFileName() == null);
    }

    @Override
    public int hashCode() {
        int hash = safeFileName != null ? safeFileName.hashCode() : 0;
        hash = 31 * hash;
        return hash;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getSafeFileName() {
        return safeFileName;
    }

    public void setSafeFileName(String safeFileName){
        this.safeFileName = safeFileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getSize() {
        return size;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}

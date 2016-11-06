package com.brownrw8.fileUploader.repository;


import com.brownrw8.fileUploader.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findByName(String name);
}

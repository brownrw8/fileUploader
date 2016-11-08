package com.brownrw8.fileUploader;

import com.brownrw8.fileUploader.dto.FileEntityDto;
import com.brownrw8.fileUploader.service.FileService;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.*;
import org.springframework.test.context.junit4.*;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploaderIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FileService fileService;

    @Value("${fileUploader.localUploadDirectory}")
    private String localUploadDirectory;

    @Test
    public void showApplication() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).contains("ng-app");
        assertThat(body).contains("</html>");
    }

    @Test
    public void addAndListFileEntities() {
        FileEntityDto firstTestEntity = new FileEntityDto("AFU.11111","test/test","Test 1","test","test1.png");
        FileEntityDto secondTestEntity = new FileEntityDto("AFU.22222","test/test","Test 2","test","test2.png");
        FileEntityDto thirdTestEntity = new FileEntityDto("AFU.33333","test/test","Test 3","test","test3.png");
        fileService.saveFile(firstTestEntity);
        fileService.saveFile(secondTestEntity);
        fileService.saveFile(thirdTestEntity);

        String body = this.restTemplate.getForObject("/file/list", String.class);
        assertThat(body).contains("Test 1");
        assertThat(body).contains("test3.png");
    }

    @Test
    public void ensureLocalUploadDirectoryExists(){
        File candidate = new File(localUploadDirectory);
        assertThat(candidate.exists());
        assertThat(candidate.isDirectory());
    }

}
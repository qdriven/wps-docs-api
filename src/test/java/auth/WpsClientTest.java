package auth;

import io.qmeta.wps.auth.WpsAuthService;
import io.qmeta.wps.yunfile.WpsYunFileService;
import io.qmeta.wps.yunfile.exception.YunException;
import io.qmeta.wps.yunfile.response.CreateCommitFileResponse;
import io.qmeta.wps.yunfile.response.RemainingSpaceResponse;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
class WpsClientTest {

    @Autowired
    WpsAuthService service;

    @Autowired
    WpsYunFileService wpsYunFileService;


    @Test
    @Ignore
    void testGetCode() throws IOException {
        String requestUrl = service.getRequestUrl();
        System.out.println(requestUrl);
    }

    @Test
    void testGetAccessToken() {
        String accessToken = service.getAccessToken();
        System.out.println(accessToken);
    }

    @Test
    public void testGetRemainSpace() {
        RemainingSpaceResponse rem = wpsYunFileService.getRemainingSpace();
        System.out.println(rem);
    }

    @Test
    public void testUploadFile() throws JSONException, YunException {
        String filePath = "/Users/Patrick/workspace/wip/qmeta-supplement/TestDocs.docx";
        CreateCommitFileResponse response = wpsYunFileService.uploadFile("0", new File(filePath),
                null);
        System.out.println(response);
        System.out.println("openfileid is " + response.getData().getOpen_fileid());
        System.out.println("openfilename is " + response.getData().getFile_name());
    }

    @Test
    public void testGetSharedLinkUrl() {
        String openFileId = "op3gUeQAnqIVKr4RbLy6GxQA";
        String sharedLinkUrl = wpsYunFileService.getWriteSharedLinkUrl(openFileId);
        System.out.println(sharedLinkUrl);
    }

    @Test
    public void getGetDownloadUrl() {
        String openFileId = "op3gUeQAnqIVKr4RbLy6GxQA";
        String sharedLinkUrl = wpsYunFileService.getDownloadUrl(openFileId);
        System.out.println(sharedLinkUrl);
    }

}
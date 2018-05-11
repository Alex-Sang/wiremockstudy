import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FatalStartupException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WiremockStudy {

    private static final int MOCK_PORT = 8181;
    private WireMockServer myMockServer;

    public static void main(String[] args) {
        WiremockStudy wiremockStudy = new WiremockStudy();
        wiremockStudy.startServer();
    }

    private void startServer() {
        myMockServer = new WireMockServer(
                wireMockConfig()
                        .extensions(new ActionsTransformer())
                        .port(MOCK_PORT)
                        .usingFilesUnderDirectory("./target/classes"));

        System.out.println("STARTING MY MOCK SERVER......");

        try {
            myMockServer.start();
        } catch (FatalStartupException e) {
            throw new FatalStartupException(new Throwable("An instance of MY MOCK server is already running on PORT " + MOCK_PORT + " cannot start another on the same port"));
        }

        if (!myMockServer.isRunning()) {
            System.out.println("The MY MOCK SERVER IS NOT RUNNING!!!");
        } else {
            System.out.println("STARTED MY MOCK SERVER ON PORT = " + MOCK_PORT);
            System.out.println("The MY MOCK SERVER CAN BE ACCESSED FROM http://localhost:" + MOCK_PORT);
            System.out.println("=============================================================================");
        }
    }

    public void stopServer() {
        myMockServer.stop();
    }
}

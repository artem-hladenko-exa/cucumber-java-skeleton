package io.cucumber.skeleton;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

public class BeforeHook {

    /*  1	Passed
    2	Blocked
    3	Untested (not allowed when adding a result)
    4	Retest
    5	Failed*/

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Before(value = "@failed")
    public void beforeFailHook(final Scenario scenario) throws IOException {
        System.out.println("RUN_ID: " + System.getProperty("RUN_ID"));
        String run = System.getProperty("RUN_ID");
        String tc = scenario.getSourceTagNames().toArray()[1].toString().replace("@C", "");

        sendResultToTR(run, tc, "5");
    }

    @Before(value = "@retest")
    public void beforeRetestHook(final Scenario scenario) throws IOException {
        System.out.println("RUN_ID: " + System.getProperty("RUN_ID"));
        String run = System.getProperty("RUN_ID");
        String tc = scenario.getSourceTagNames().toArray()[1].toString().replace("@C", "");

        sendResultToTR(run, tc, "4");
    }

    @Before(value = "@passed")
    public void beforePassHook(final Scenario scenario) throws IOException {
        System.out.println("RUN_ID: " + System.getProperty("RUN_ID"));
        String run = System.getProperty("RUN_ID");
        String tc = scenario.getSourceTagNames().toArray()[1].toString().replace("@C", "");

        sendResultToTR(run, tc, "1");
    }

    private void sendResultToTR(final String run, final String tc, final String status) throws IOException {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        String credential = Credentials.basic("menserds@gmail.com", "EJkWNojLHdGnQpXvTbBn-BOJTrxm3s6ZedQ2L9XcM");

        String json = "{\n" +
                "\t\"status_id\": " + status + ",\n" +
                "\t\"comment\": \"This test was run on CI\"\n" +
                "}";

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .method("POST", body)
                .header("Content-type", "application/json")
                .header("Authorization", credential)
                .url("http://10.70.0.54:8484/index.php?/api/v2/add_result_for_case/" + run +"/"+ tc)
                .build();

        client.newCall(request).execute();
    }

}

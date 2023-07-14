package ru.stqa.pft.mantis.appmanager;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.jayway.restassured.RestAssured;
import ru.stqa.pft.mantis.model.IssueBugify;

import java.util.Set;

import static java.lang.String.format;

public class RestHelper {

    private ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<IssueBugify> getIssues() {
        String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json?limit=200").asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<IssueBugify>>() {}.getType());
    }

    public String getSpecificIssueState(int issueId) {
        String json = RestAssured.get(format("https://bugify.stqa.ru/api/issues/%s.json", issueId)).asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonArray issueArray = parsed.getAsJsonObject().getAsJsonArray("issues");
        return issueArray.get(0).getAsJsonObject().get("state_name").getAsString();
    }

    public int createIssue(IssueBugify newIssue) {
        String json = RestAssured.given()
                .parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post("https://bugify.stqa.ru/api/issues.json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}
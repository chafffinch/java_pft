package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.stqa.pft.rest.tests.Issue;

import java.io.IOException;
import java.util.Set;

import static com.google.gson.JsonParser.parseString;

public class JsonHelper {
    private ApplicationManager app;

    public JsonHelper(ApplicationManager app) {
        this.app = app;
    }
    public Set<Issue> getIssue() throws IOException {
        String json = getExecutor().execute(Request.Get(app.getProperty("rest.issue")+ "?limit=40"))
                .returnContent().asString();
        JsonElement parsed = parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }
    public Executor getExecutor() {
        return Executor.newInstance().auth(app.getProperty("rest.authKey"), "");
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post(app.getProperty("rest.issue"))
                        .bodyForm(new BasicNameValuePair("description", newIssue.getDescription()),
                                new BasicNameValuePair("subject", newIssue.getSubject())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public String getIssueStatus(int issueId) throws IOException {
        String json = getExecutor()
                .execute(Request.Get(String.format(app.getProperty("rest.getIssueService") + "%s.json", issueId)))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        JsonElement first = issues.getAsJsonArray().get(0);
        return first.getAsJsonObject().get("state_name").getAsString();
    }
}

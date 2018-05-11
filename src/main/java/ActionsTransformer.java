
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class ActionsTransformer extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource, Parameters parameters) {

        int actionCount = 0;
        String actionsSummary = "";
        String actionsDetails = "";

        if (request.getUrl().equals("/actions")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode requestBody = mapper.readTree(request.getBodyAsString());
                System.out.println(requestBody);

                JsonNode actions = requestBody.path("Actions");
                actionCount = actions.size();
                System.out.println("Total Actions is: " + actionCount);
                actionsSummary = "RESPONSE: Total Actions: " + actionCount + " is Received!";
                String temp = "";
                for (int i = 1; i <= actionCount; i++){
                    temp = "Action " + i + " is: " + actions.get(i-1).get("Action").textValue();
                    System.out.println(temp);
                    actionsDetails = actionsDetails + "\n" + temp;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (actionCount > 2){
            return  new ResponseDefinitionBuilder()
                    .withBody(actionsSummary + "\n" + "Too many actions! ")
                    .withStatus(200)
                    .withHeader("Content-Type","text/xml; charset=utf-8")
                    .build();
        }else{
            return  new ResponseDefinitionBuilder()
                    .withBody(actionsSummary + "\n" + actionsDetails)
                    .withStatus(200)
                    .withHeader("Content-Type","text/xml; charset=utf-8")
                    .build();
        }


    }
    @Override
    public String getName() {
        return "ActionsTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}

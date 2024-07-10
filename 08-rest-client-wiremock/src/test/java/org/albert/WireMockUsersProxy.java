package org.albert;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.Collections;
import java.util.Map;

public class WireMockUsersProxy implements QuarkusTestResourceLifecycleManager
{
    WireMockServer wireMockServer;

    @Override
    public Map<String, String> start()
    {
        wireMockServer = new WireMockServer(8189);
        wireMockServer.start();
        WireMock.configureFor(8189);

        String jsonBody = """
                {
                  "id": 100,
                  "name": "Franz Bonaparta",
                  "username": "Emil",
                  "address": {
                    "street": "Gehenna",
                    "geo": {
                      "lat": "-37.3159",
                      "lng": "81.1496"
                    }
                  }
                }                                              
                """;

        stubFor(
                get(urlEqualTo("/users/100")).willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(jsonBody)
                )
        );

        // This line is used to make an actual request for the real Server
        // instead of using the WireMock Server. You could also be
        // more specific and use `get(urlEqualTo("/users/1"))`
        stubFor(get(urlMatching(".*"))
                .atPriority(10)
                .willReturn(aResponse().proxiedFrom("https://jsonplaceholder.typicode.com"))
        );

        return Collections.singletonMap(
                "org.albert.proxy.UsersProxy/mp-rest/url",
                wireMockServer.baseUrl()
        );
    }

    @Override
    public void stop()
    {
        if (wireMockServer != null)
        {
            wireMockServer.stop();
        }
    }

}

package org.albert;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

// mvnw.cmd quarkus:dev -Dquarkus.args=I_am_self_aware
public class QuarkusController implements QuarkusApplication
{
    @Override
    public int run(String... args) throws Exception
    {
        System.out.println(args[0]);
        Quarkus.waitForExit();
        return 0;
    }
}

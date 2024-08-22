package org.albert;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class App
{
    public static void main(String[] args)
    {
        Quarkus.run(QuarkusController.class, args);
    }
}

// Another way of doing the same thing above.
//@QuarkusMain
//public class App implements QuarkusApplication
//{
//    // mvnw.cmd quarkus:dev -Dquarkus.args=I_am_self_aware
//    @Override
//    public int run(String... args) throws Exception
//    {
//        System.out.println(args[0]);
//        Quarkus.waitForExit();
//        return 0;
//    }
//}
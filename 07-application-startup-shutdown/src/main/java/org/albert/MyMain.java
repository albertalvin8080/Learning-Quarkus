package org.albert;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class MyMain
{
    public static void main(String[] args)
    {
        System.out.println("Inside main()");
        Quarkus.run(BusinessLogic.class, args);
    }

    public static class BusinessLogic implements QuarkusApplication
    {
        @Override
        public int run(String... args) throws Exception
        {
            System.out.println("Inside business logic...");
            Quarkus.waitForExit();
            return 0;
        }
    }
}

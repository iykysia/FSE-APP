package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.DeploymentOptions;


public class App
{

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        MainVerticle mainVert = new MainVerticle();
        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        vertx.deployVerticle(mainVert, options);
        // vertx.undeploy(mainVert.deploymentID(), res -> {
        //     if (res.succeeded()) {
        //       System.out.println("Undeployed ok");
        //     } else {
        //       System.out.println("Undeploy failed!");
        //     }
        //   });
          long timerID = vertx.setPeriodic(1000, id -> {
            System.out.println("And every second this is printed");
          });
        //   vertx.cancelTimer(timerID);

    }
}
package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.Promise;
import io.vertx.core.DeploymentOptions;


public class App
{

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        // Verticle vertx = new MainVerticle();
        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        vertx.deployVerticle(new MainVerticle(), options);
    }
}
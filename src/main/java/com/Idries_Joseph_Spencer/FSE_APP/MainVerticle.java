package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Route;
import io.vertx.core.http.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.client.*;
// import io.vertx.ext.web.FileUpload;

//https://vertx.io/docs/vertx-web/java/
public class MainVerticle extends AbstractVerticle {
  String user = "";



  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();


    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());



    router.post("/login").handler(ctx -> {
      String routeURI = "/login/fail";
      if(ctx.request().getParam("username") != null && ctx.request().getParam("password") != null)
      {
        System.out.println("username was set");
        if(ctx.request().getParam("password").equals("password")){
          System.out.println("equaled username");
          user = ctx.request().getParam("username");
          routeURI = "/homepage";
      }
      }
      ctx.reroute(routeURI);
    });
    
    Route routeLogin = router.route("/login");
    routeLogin.handler(routingContext -> {
      System.out.println("Get to login");
      HttpServerResponse response = routingContext.response();
      response.sendFile("src\\resources\\html\\test.html", 0);
    });


    Route routeLogin_fail = router.route("/login/fail");
    routeLogin_fail.handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "text/plain")
          .end("login fail");
    });
    Route routeBase = router.route("/");
    routeBase.handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.sendFile("src\\resources\\html\\test.html", 0);
    });
    Route routehomepage = router.route("/homepage");
    routehomepage.handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      Session session = routingContext.session();
      response.putHeader("content-type", "text/plain")
          .end("login success, Welcome " 
          + user);
    });

    router.errorHandler(500, rc -> {
      System.err.println("Handling failure");
      Throwable failure = rc.failure();
      if (failure != null) {
        failure.printStackTrace();
      }
    });
    server.requestHandler(router).listen(8888);

  }
}

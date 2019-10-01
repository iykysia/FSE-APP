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
// import io.vertx.core.http.HttpServerResponse;

//https://vertx.io/docs/vertx-web/java/
public class MainVerticle extends AbstractVerticle {
  String user = "";



  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    // server.requestHandler(req -> {
    //   req.response().sendFile("src\\resources\\html\\test.html", 0);
    //     // .putHeader("content-type", "text/plain")
    //     // .end("Hello from Vert.x!");
    // }).listen(8888, http -> {
    //   if (http.succeeded()) {
    //     startPromise.complete();
    //     System.out.println("HTTP server started on port 8888");
    //   } else {
    //     startPromise.fail(http.cause());
    //   }
    // });


    // ***this is from the router
    // HttpServerResponse response = routingContext.response();

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    // router.route().handler(routingContext -> {
    //   HttpServerResponse response = routingContext.response();
    //   response.sendFile("src\\resources\\html\\test.html", 0);
    // });
    // Route route = router.route(HttpMethod.POST, "/login");
    // route.handler(routingContext -> {

      router.route(HttpMethod.POST, "/login").handler(routingContext -> {
      System.out.println("Post to login");
      HttpServerResponse response = routingContext.response();
      // response.putHeader("content-type", "application/json");
      // JsonObject postJson = routingContext.getBodyAsJson();
      // System.out.println(postJson != null);

      // System.out.println(routingContext.getBodyAsString());
      // response.putHeader("content-type", "application/json");
      // String username = postJson.getString("username");
      // System.out.println("Post Username: " + username);

      // String password = postJson.getString("password");
      // System.out.println("Post Password: " + password);


      String username = "username";
      String password = "password";
      username = routingContext.request().getParam("username");
      password = routingContext.request().getParam("password");
      // HttpServerResponse response = routingContext.response();
      // response.putHeader("content-type", "application/json");
      // String username = postJson.getString("username");
      // System.out.println("Post Username: " + username);

      // String password = postJson.getString("password");
      // System.out.println("Post Password: " + password);


      if(username.equals("username") && password.equals("password"))
      {
        //60000 times minutes or 1000 times seconds work for timing out
        user = username;
        // Session session = routingContext.session().;
        // session.put("username", username);
        routingContext.reroute("/homepage");
      }
      else{
      routingContext.reroute("/login/fail");
      }
        // Write to the response and end it
      response.end("{\"status\": 200}");
    });
    Route routeHome = router.route("/");
    routeHome.handler(routingContext -> {
      System.out.println("Get to login");
      HttpServerResponse response = routingContext.response();
      response.sendFile("src\\resources\\html\\test.html", 0);
    });
    Route routeLogin = router.route("/login");
    routeLogin.handler(routingContext -> {
      System.out.println("Get to login");
      HttpServerResponse response = routingContext.response();
      response.sendFile("src\\resources\\html\\test.html", 0);
    });



   /* 
    Route route = router.route(HttpMethod.POST, "/login");
    route.handler(routingContext -> {
      System.out.println("Post to login");

      JsonObject postJson = routingContext.getBodyAsJson();

      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "application/json");
      String username = postJson.getString("username");
      String password = postJson.getString("password");
      if(username.equals("username") && password.equals("password"))
      {
        //60000 times minutes or 1000 times seconds work for timing out
        Session session = routingContext.session();
        session.put("username", username);
        routingContext.reroute("/homepage");
      }
      else{
      routingContext.reroute("/login/fail");
      }
        // Write to the response and end it
      response.end("{\"status\": 200}");

    //   if(username.equals("username") && password.equals("password"))
    //   {
    //     //60000 times minutes or 1000 times seconds work for timing out
    //     Session session = routingContext.session();
    //     session.put("username", username);
    //     routingContext.reroute("/homepage");
    //   }
    //   else{
    //   routingContext.reroute("/login/fail");
    //   }
    //   //got it here
    //   //do something ...
    //   // });
    // // String username = routingContext.request().getParam("username");
    // // String password = routingContext.request().getParam("password");

    // // if(username.equals("username") && password.equals("password"))
    // // {
    // //   //60000 times minutes or 1000 times seconds work for timing out
    // //   Session session = routingContext.session();
    // //   session.put("username", username);
    // //   routingContext.reroute("/homepage");
    // // }
    // else{
    // routingContext.reroute("/login/fail");
    // }


    });

    */
    Route routeLogin_fail = router.route("/login/fail");
    routeLogin_fail.handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "text/plain")
          .end("login fail");
    });

    Route routehomepage = router.route("/homepage");
    routehomepage.handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      Session session = routingContext.session();
      response.putHeader("content-type", "text/plain")
          .end("login success, Welcome " 
          + user);
          // + session.get("username"));
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

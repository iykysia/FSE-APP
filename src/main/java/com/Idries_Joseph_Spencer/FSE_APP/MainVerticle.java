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
import io.vertx.ext.web.FileUpload;
// import io.vertx.example.util.Runner;


// import io.vertx.core.http.HttpServerResponse;

//https://vertx.io/docs/vertx-web/java/
public class MainVerticle extends AbstractVerticle {
  String user = "";



  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    // WebClient client = WebClient.create(vertx);

    // server.requestHandler(
    //   r->{r.setExpectMultipart(true);
    // }
    // );
    // client.get(8080, "myserver.mycompany.com", "/login").addQueryParam("username", "username_value");

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
      else{
        
      }
      ctx.reroute(routeURI);
      // ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
      // note the form attribute matches the html form element name.
      // ctx.response().end("Hello " + ctx.request().getParam("username") + "!");
    });
    // router.setExpectMultipart(true);
    // router.post("/login").handler(Body)
    // .handler(routingContext -> {
    //   System.out.println("post login");
    //   routingContext.request().bodyHandler(body->{
    //     System.out.print("entered body");
    //   });
    //   routingContext.next();
    //   // MultiMap attributes = routingContext.request().formAttributes();
    //   // routingContext.response().putHeader("Content-Type", "text/plain");

    //   // routingContext.response().setChunked(true);

    //   // for (FileUpload f : routingContext.fileUploads()) {
    //   //   System.out.println("f");
    //   //   routingContext.response().write("Filename: " + f.fileName());
    //   //   routingContext.response().write("\n");
    //   //   routingContext.response().write("Size: " + f.size());
    //   // }

    //   // routingContext.response().end();
    // }).blockingHandler(requestHandler ->{
    //   System.out.println("enter blocking");
    //   System.out.println(requestHandler.request().getFormAttribute("username"));
    // }
    // );
/*
    router.route().handler(MultipartHandler.create());
    // req.setExpectMultipart(true);
// router.route().handler()
      router.post("/login").handler(routingContext -> {
      System.out.println("Post to login");
      // .request().setExpectMultipart(true);
      HttpServerRequest request = routingContext.request();


      System.out.println(request.getFormAttribute("username"));



      String username = "username";
      String password = "password";

        
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
      // response.end("{\"status\": 200}");
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

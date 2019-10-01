package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Route;
import io.vertx.core.http.HttpMethod;

//https://vertx.io/docs/vertx-web/java/
public class MainVerticle extends AbstractVerticle {

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
    HttpServerResponse response = routingContext.response();

    Router router = Router.router(vertx);

    Route route = router.route(HttpMethod.POST, "/login/:username/:password/");
    route.handler(routingContext -> {

    String username = routingContext.request().getParam("username");
    String password = routingContext.request().getParam("password");
    if(username.equals("username") && password.equals("password"))
    {
      Session session = routingContext.session();
      session.put("username", username);
      routingContext.reroute("/homepage");
    }
    else{
    routingContext.reroute("/login/fail")
    }
    });
  }
}

package com.Idries_Joseph_Spencer.FSE_APP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;



//https://vertx.io/docs/vertx-web/java/
public class MainVerticle extends AbstractVerticle {
  String user = "";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    SessionStore store = LocalSessionStore.create(vertx, "MainVerticle.sessionmap");
    SessionHandler sessionHandler = SessionHandler.create(store);

    router.route().handler(sessionHandler);
    router.route().handler(BodyHandler.create());

    router.post("/signup").handler(ctx -> {
        try{
          Connection signupInsert = MySQLLinker.Connect("fse_sprint1", "root", "fse");
          PreparedStatement signUpStatement = signupInsert.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
          signUpStatement.setString(1, ctx.request().getParam("username"));
          signUpStatement.setString(2, ctx.request().getParam("password"));
          signUpStatement.executeUpdate();
          signupInsert.close();
        }
        catch (Exception e){
          e.printStackTrace();
        }
      // ctx.response().end();
      ctx.request().params().clear();
      ctx.reroute(HttpMethod.GET, "/login");

      // ctx.response().end();

    });

    RouteMaster login = RouteMaster.buildPost(router, "/login").defineResponse(RouteMaster.ResponseType.reroute, "/login/fail");
    login.defineAction( (context) ->{
      if(context.request().getParam("username") != null && context.request().getParam("password") != null)
      {
        System.out.println("login attempt");
        Connection logiConnection = MySQLLinker.Connect("fse_sprint1", "root", "fse");
        if(!logiConnection.equals(null)) {
        try{
          System.out.println(""+context.request().getParam("username") + " & " + context.request().getParam("password"));
          PreparedStatement loginPreparedStatement = logiConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
          loginPreparedStatement.setString(1, context.request().getParam("username"));
          ResultSet rs = loginPreparedStatement.executeQuery();
          System.out.println(rs.toString());

          while (rs.next()) {
            System.out.println(rs.getString("username")+" & "+rs.getString("password"));
            if(context.request().getParam("password").equals(rs.getString("password"))){
              Session session = context.session();
              user = context.request().getParam("username");
              session.put("username", user);
              login.defineResponse(RouteMaster.ResponseType.reroute, "/homepage");
            }
            else{
              break;
            }
          }
          logiConnection.close();
        }
        catch (Exception e){
          e.printStackTrace();
          System.out.println("failed login");
        }
      }
      else {  
        System.out.println("LinkerFailed");
      }


      //   if(context.request().getParam("password").equals("password")){
      //     Session session = context.session();
      //     user = context.request().getParam("username");
      //     session.put("username", user);
      //     login.defineResponse(RouteMaster.ResponseType.reroute, "/homepage");
      // }
      }
    });
    login.execute();


    //RouteMaker Code Minimization
    RouteMaster.GetAndHTML(router,"/login","test.html");
    RouteMaster.GetAndTextResponse(router, "/login/fail", "login fail");
    RouteMaster.GetAndHTML(router,"/","signup.html");

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

/*
    router.post("/login").handler(ctx -> {
      String routeURI = "/login/fail";
      if(ctx.request().getParam("username") != null && ctx.request().getParam("password") != null)
      {
        if(ctx.request().getParam("password").equals("password")){
          Session session = ctx.session();
          user = ctx.request().getParam("username");
          session.put("username", user);
          routeURI = "/homepage";
      }
      }
      ctx.reroute(routeURI);
    });
    */

     /*
    //Old Code
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
    */

    /*
///
///This is code is the same as MySQLLinker
///
Connection conn = null;
try {
  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?useSSL=false&serverTimezone=UTC", "root","jinn");
  System.out.println(conn.getClientInfo("root"));
  System.out.println("sql succ");
  // Do something with the Connection
  System.out.println(conn.toString());
  // conn.
  Statement stmt=conn.createStatement(); 
// conn.setSchema("sakila");
  ResultSet rs = stmt.executeQuery("Select * from actor"); 
  // ResultSet rs = stmt.execute("use sakila;");
  // ResultSet rs = stmt.executeQuery("INSERT into user ("")");  
  System.out.println(rs.toString());
  conn.close();
  System.out.println(rs.toString());

 
} catch (SQLException ex) {
  System.out.println("SQLException: " + ex.getMessage());
  System.out.println("SQLState: " + ex.getSQLState());
  System.out.println("VendorError: " + ex.getErrorCode());
}
*/
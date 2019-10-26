package com.Idries_Joseph_Spencer.FSE_APP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
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

Connection connect = MySQLLinker.Connect("fse", "root", "fse");
if(!connect.equals(null)) {
  System.out.println(connect.toString());
  // connect.createStatement().execute("use fse");
  PreparedStatement stmt = connect.prepareStatement("SELECT * FROM users");
  ResultSet rs = stmt.executeQuery();
  System.out.println(rs.toString());
  try{
  // System.out.println(rs.getString("actor_id"));
  
  while (rs.next()) {
    // retrieve and print the values for the current row
    String i = rs.getString("username");
    // if(rs.getString("username").equals())
    System.out.println("ROW = " + i );
  }
  connect.close();
  // while (rs.next()) {
  //   // retrieve and print the values for the current row
  //   int i = rs.getInt("actor_id");

  //   System.out.println("ROW = " + i );
  // }

  }
  catch (Exception e){
    System.out.println("failed query");
  }
  // connect.setAutoCommit(false);
  // PreparedStatement prs = connect.prepareStatement();
}
else {  
  System.out.println("LinkerFailed");
}


    
    Router router = Router.router(vertx);
    SessionStore store = LocalSessionStore.create(vertx, "MainVerticle.sessionmap");
    SessionHandler sessionHandler = SessionHandler.create(store);

    router.route().handler(sessionHandler);
    router.route().handler(BodyHandler.create());


    router.post("/signup").handler(ctx -> {
      
    });
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
    RouteMaster login = RouteMaster.buildPost(router, "/login").defineResponse(RouteMaster.ResponseType.reroute, "/login/fail");
    login.defineAction( (context) ->{

      Connection connLogin = MySQLLinker.Connect("fse", "root", "fse");
      try{
      PreparedStatement loginstm = connLogin.prepareStatement("SELECT * FROM users");
      ResultSet rs = loginstm.executeQuery();
      }
      catch (Exception e){
        e.printStackTrace();
      }
      if(context.request().getParam("username") != null && context.request().getParam("password") != null)
      {
        if(context.request().getParam("password").equals("password")){
          Session session = context.session();
          user = context.request().getParam("username");
          session.put("username", user);
          login.defineResponse(RouteMaster.ResponseType.reroute, "/homepage");
      }
      }
    });
    login.execute();


    //RouteMaker Code Minimization
    RouteMaster.GetAndHTML(router,"/login","test.html");
    RouteMaster.GetAndTextResponse(router, "/login/fail", "login fail");
    RouteMaster.GetAndHTML(router,"/","test.html");


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

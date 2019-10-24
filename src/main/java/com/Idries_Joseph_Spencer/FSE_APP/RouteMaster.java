package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

//TODO
//converting this to a Builder pattern
public class RouteMaster
{
    private String route;
    private String endRoute;
    private Router router;
    private String resp;
    private RouteAction action;
    private boolean post;
    
    
    private RouteMaster (){

    }
    private static RouteMaster buildBase(RouteMaster build, Router router, String route){
        build.router = router;
        build.route = route;

        return build;
    }
    public static RouteMaster buildGet(Router router, String route){
        RouteMaster build = new RouteMaster();
        build.post = false;
        build.buildBase(router, route);

        return build;
    }
    public static RouteMaster buildPost(Router Router, String Route){
        RouteMaster build = new RouteMaster();
        build.post = true;
        
        return build;
    }

    
    public interface RouteAction{
        void routeAction();
    }


    //TODO
    //make a complex version to allow adding to the body
    public static void GetAndHTML(Router router, String route, String html){
        Route routeLogin = router.route(route);
        routeLogin.handler(routingContext -> {
        System.out.println("RouteMaker: Get Html_Resp: "+ route);
        HtmlResponse(routingContext, html);
        });
    }

    public static void GetAndTextResponse(Router router, String route, String text){
        Route routeLogin_fail = router.route(route);
        routeLogin_fail.handler(routingContext -> {
            System.out.println("RouteMaker: Get Text_Resp: "+ route);
            TextResponse(routingContext, text);
    });
    }

    public static void HtmlResponse(RoutingContext routingContext, String html)
    {
        HttpServerResponse response = routingContext.response();
        response.sendFile("src\\resources\\html\\"+html, 0);
    }
    public static void TextResponse(RoutingContext routingContext, String text)
    {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain")
            .end(text);    
    }

    public static void GetAndReroute(){

    }

}
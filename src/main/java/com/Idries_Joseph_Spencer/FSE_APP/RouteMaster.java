package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

//TODO
//converting this to a Builder pattern
public class RouteMaster
{
     public enum ResponseType{
        text,
        reroute,
        html,
    }
    public interface RouteActionLambda{
        void routeAction(RoutingContext context);
    }
    private String route;
    private Router router;
    private String resp;
    private RouteActionLambda action;
    private boolean post;
    // private RoutingContext context;
    private ResponseType respType;
    
    
    private RouteMaster (){

    }
   
    public static RouteMaster buildGet(Router router, String route){
        RouteMaster build = new RouteMaster();
        build.post = false;
        build.router = router;
        build.route = route;

        return build;
    }

    public static RouteMaster buildPost(Router router, String route){
        RouteMaster build = new RouteMaster();
        build.post = true;
        build.router = router;
                build.route = route;

        return build;
    }
    
    public RouteMaster defineResponse(ResponseType responseType, String resp){
        respType = responseType;
        this.resp = resp;
        return this;
    }
    
    public RouteMaster defineAction(RouteActionLambda action){
        this.action = action;
        return this;
    }

    private void ResponseSwitch(RoutingContext context, ResponseType type, String resp){
        switch(type){
            case reroute:
                RouteMaster.RerouteResponse( context, resp);
                break;
            case html:
                RouteMaster.HtmlResponse(context, resp);
            break;
            case text:
                RouteMaster.TextResponse( context, resp);
            break;
            
        }
    }
    public void execute(){
        if(post){
            post();
        }
        else{
            get();
        }
    }
    private void post(){
        router.post(route).handler(requestHandler -> {
            
            action.routeAction(requestHandler);
            ResponseSwitch(requestHandler,respType,resp);
        });

    }
    private void get(){
        router.post(route).handler(requestHandler -> {

            action.routeAction(requestHandler);
            ResponseSwitch(requestHandler, respType,resp);
        });
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
    public static void RerouteResponse(RoutingContext routingContext, String html)
    {
        routingContext.reroute(html);
    }
    public static void TextResponse(RoutingContext routingContext, String text)
    {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain")
            .end(text);    
    }
}
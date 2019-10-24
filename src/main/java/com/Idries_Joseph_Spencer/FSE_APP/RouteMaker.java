package com.Idries_Joseph_Spencer.FSE_APP;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class RouteMaker
{

    public void GetAndHTML(Router router, String html, String route){
        Route routeLogin = router.route(route);
        routeLogin.handler(routingContext -> {
        System.out.println("Get "+ route);
        HttpServerResponse response = routingContext.response();
        response.sendFile("src\\resources\\html\\"+html, 0);
    });
    }
    public void GetAndReroute(){

    }

}
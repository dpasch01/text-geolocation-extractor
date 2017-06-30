package com.location.extractor.service;

import com.location.extractor.clavin.CLAVIN;
import com.location.extractor.utils.Utils;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class Controller {

    static {
        staticFileLocation("/public");
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");

            response.type("application/json");
        });
    }

    public Controller() {

        get("/resolve", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                String text = request.queryParams("text");
                if (text == null) {
                    throw new Exception("Text parameter is not defined.");
                }
                return Service.resolve(request.queryParams("text"));
            }

        }, Utils.transform2json());

        post("/resolve", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                String text = request.params("text");
                if (text == null) {
                    throw new Exception("Text parameter is not defined.");
                }
                return Service.resolve(request.params("text"));
            }

        }, Utils.transform2json());

        get("/url", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                String source = request.queryParams("source");
                if (source == null) {
                    throw new Exception("URL parameter is not defined.");
                }
                return Service.resolveURL(request.queryParams("source"));
            }

        }, Utils.transform2json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(Utils.convert2json(new ResponseError(e)));
        });

    }

}

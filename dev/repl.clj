(ns repl
  (:require [core :refer [-main] :rename {-main build}]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as content-type]
            [ring.util.response :as response]))

(defonce server (atom nil))

(defn app [request]
  (or ((file/wrap-file identity "target/html") request)
      (response/not-found "Not Found")))

(def handler
  (-> app
      content-type/wrap-content-type))

(defn start []
  (when-not @server
    (reset! server (jetty/run-jetty handler {:port 8080 :join? false}))
    (println "Server started on http://localhost:8080")))

(defn stop []
  (when @server
    (.stop @server)
    (reset! server nil)
    (println "Server stopped")))

(defn restart []
  (stop)
  (start))

(defn serve []
  (start))

(comment
  (restart)
  (build))

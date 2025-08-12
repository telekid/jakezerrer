(ns core
  (:require [borkdude.html :refer [html]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn template [body]
  (html [:html [:head] [:body [:<> body]]]))

(defn home []
  (template
   (html
    [:h1 "home"])))

(defn about []
  (template
   (html
    [:h1 "about"])))

(defn this-life
  "blog post about this life"
  []
  (template
   (html
    [:h1 "This life"])))

(defn something-else
  "blog post about something else"
  []
  (template
   (html
    [:h1 "Something else"])))

(something-else)

(def pages
  {"/" home
   "/about2" about
   "/blog/this-life" this-life
   "/blog/something-else" something-else})

(defn clean []
  (let [target-dir (io/file "target")]
    (when (.exists target-dir)
      (doseq [file (file-seq target-dir)
              :when (.isFile file)]
        (io/delete-file file))
      (doseq [dir (reverse (filter #(.isDirectory %) (file-seq target-dir)))
              :when (not= dir target-dir)]
        (.delete dir)))))

(defn compile []
  (doseq [[path page-fn] pages]
    (let [target-path (if (= path "/")
                        "target/html/index.html"
                        (str "target/html" path "/index.html"))
          target-file (io/file target-path)]
      (io/make-parents target-file)
      (spit target-file (str (page-fn))))))

(defn -main []
  (clean)
  (compile))

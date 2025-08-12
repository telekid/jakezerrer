(ns highlight
  (:require [clojure.java.shell :as shell]
            [borkdude.html :refer [html]]))

(defn highlight [lang src]
  (let [result (shell/sh "pygmentize" "-l" lang "-f" "html" :in src)]
    (:out result)))

(defmacro highlight-clj [& body]
  `(highlight "clojure" ~(apply str (map pr-str body))))

(defn highlight-styles [style]
  (let [result (shell/sh "pygmentize" "-S" style "-f" "html" "-a" ".code")]
    (:out result)))

(defn code [v]
  (html
   [:div
    {:class "code"}
    [:$ v]]))

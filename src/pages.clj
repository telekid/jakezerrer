(ns pages
  (:require [borkdude.html :refer [html]]))

(def home-uri "/")
(def books-2025-uri "/books-2025")

(defn template [body]
  (html
   [:html
    {:style {:font-family "monospace"}}
    [:head
     [:meta {:charset "UTF-8"}]]
    [:body
     [:<> body]]]))

(defn home []
  (template
   (html
    [:<>
     [:p "Hello."]
     [:p "My name is Jake Zerrer. This is where I keep things online. Look around."]
     [:ul
      (map
       (fn [[uri name]]
         (html
          [:li [:a {:href (str uri)} name]]))
       [[books-2025-uri "2025 reading list"]])]])))

(defn page [body]
  (template
   (html
    [:<>
     [:p [:a {:href "/"} "< home"]]
     body])))

(defn books-2025
  []
  (page
   (html
    [:<>
     [:h1 "Incomplete 2025 reading list"]
     [:ul
      [:li "The Places in Between (Rory Stewart)"]
      [:li "Either/Or: A Fragment of Life (Søren Kierkegaard)"]
      [:li "The Philosophy of History (G. W. F. Hegel)"]
      [:li "This Life: Secular Faith and Spiritual Freedom (Martin Hägglund)"]
      [:li "Mating (Normal Rush)"]]])))

(defn pages []
  {home-uri home
   books-2025-uri books-2025})

(comment
  (require '[repl :refer [restart build]])
  (restart)
  (build))

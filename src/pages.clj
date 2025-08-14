(ns pages
  (:require [borkdude.html :refer [html]]
            [pages.missionary :refer [missionary]]
            [components :refer [template page]]))

(def home-uri "/")
(def books-2025-uri "/books-2025")
(def past-work-uri "/past-work")
(def missionary-uri "/code/missionary")

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
       [[books-2025-uri "Selected reading list, 2025"]
        [past-work-uri "Past work"]])]])))

(defn books-2025
  []
  (page
   (html
    [:<>
     [:h1 "Selected reading list, 2025"]
     [:ul
      [:li "The Second Sex (Simone de Beauvior)"]
      [:li "The Places in Between (Rory Stewart)"]
      [:li "Either/Or: A Fragment of Life (Søren Kierkegaard)"]
      [:li "The Philosophy of History (G. W. F. Hegel)"]
      [:li "This Life: Secular Faith and Spiritual Freedom (Martin Hägglund)"]
      [:li "The Power Broker (Robert Caro)"]
      [:li "Mating (Normal Rush)"]]])))

(defn past-work
  []
  (page
   (html
    [:<>
     [:h1 "Past work"]
     [:p "I have spent most of my professional life working as a software engineer:"]
     [:ul
      [:li "In 2024, I ran product engineering at Normal Computing"]
      [:li "In the summer of 2023, I traveled and prototyped a devex tool called refuge"]
      [:li "From 2018 to 2023, I worked as a software engineer at Flexport"]
      [:li "From 2014 to 2017, I worked as a software engineer at a small startup"]]
     [:p "I had a previous career as a theatrical sound designer in New York City."]])))

(defn e-404 []
  (page
   (html
    [:<>
     [:h1 "404 Oh, be some other path!"]
     [:p "What's in a path? That which we call a page"
      [:br]
      "by any other path would fail to load"]])))

(defn pages []
  {home-uri home
   books-2025-uri books-2025
   past-work-uri past-work
   missionary-uri missionary})

(comment
  (require '[repl :refer [restart build]])
  (restart)
  (build))

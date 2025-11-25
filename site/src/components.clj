(ns components
  (:require [borkdude.html :refer [html]]
            [highlight :refer [highlight-styles]]))

(defn template [body]
  (html
   [:html
    {:style {:font-family "monospace"}}
    [:head
     [:style
      [:$
       (str
        (highlight-styles :code "default")
        (highlight-styles :output "default")
        "li { line-height: 1.6; }"
        "p { line-height: 1.6; font-family: Palatino; }"
        ".page-body p { max-width: 44em; }"
        ".page-body blockquote { max-width: 44em; margin-left: 2em; }"
        "hr { border: none; height: 1px; background-color: #ccc; margin: 2em 0; }"
        ".highlight { padding: 1px; padding-left: 6px; }")]]
     [:meta {:charset "UTF-8"}]]
    [:body
     [:<> body]
     [:footer
      [:br]
      [:br]
      [:p "---"]
      [:span
       [:a {:href "https://github.com/telekid/jakezerrer"} "page src"]
       " | "
       [:a {:href "mailto:contact@jakezerrer.com?subject=Blog post"} "contact me"]]]]]))

(defn page [body]
  (template
   (html
    [:<>
     [:p [:a {:href "/"} "< home"]]
     [:div {:class "page-body"}
      body]])))

(defn blockquote [body [from to]]
  (html
   [:figure
    {:style "margin-left: 0; margin-bottom: 2em"}
    [:blockquote
     body]
    [:figcaption
     (html
      [:em
       (if (nil? to)
         (html [:<> (str "p. " from)])
         (html [:<> (str "pp. " from "-" to)]))])]]))

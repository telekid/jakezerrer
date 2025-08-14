(ns highlight
  (:require [clojure.java.shell :as shell]
            [borkdude.html :refer [html]]
            [zprint.core :as zp]
            [clojure.string :refer [join]]))

(defn highlight [lang src]
  (let [result (shell/sh "pygmentize" "-l" lang "-f" "html" :in src)]
    (:out result)))

(defn code [style v]
  (html
   (let [s style]
     [:div
      {:class s}
      v])))

(defmacro highlight-clj [& body]
  (let [out (atom [])
        _res ;; return value of final form; currently unused
        (let [prev-ns *ns*
              t #(swap! out conj %)]
          (ns example)
          (add-tap (bound-fn* t))
          (let [res
                (last (map eval body))]
            (remove-tap (bound-fn* t))
            (in-ns (ns-name prev-ns))
            res))
        code
        (join "\n\n" (map zp/zprint-str body))
        tap-out  (join "\n" (map zp/zprint-str @out {:style :backtranslate}))
        joined (apply str
                      (concat code))]
    `(html
      [:div
       {:style {:border-left "5px solid #ddd"}}
       [:div {:class "code"}
        [:$
         (highlight "clojure"
                    ~joined)]]
       [:p {:style {:padding-left "6px"}} "tap contents:"]
       [:div {:class "output"}
        [:$
         (highlight "clojure"
                    ~tap-out)]]])))

(defn highlight-styles [k style]
  (let [result (shell/sh "pygmentize" "-S" style "-f" "html" "-a" (str "." (name k)))]
    (:out result)))

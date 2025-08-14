(ns scratch
  (:require [missionary.core :as m]))

(def ex1
  (m/ap "Hello, world!"))

(println (m/? (m/reduce {} nil ex1)))

(def ex2
  (m/ap (println "now")
        (m/? (m/sleep 1000))
        (println "now")
        "done"))

(println
 (m/? (m/reduce {} nil ex2)))


(def ex
  (m/ap (println "V:" (m/?> (m/seed [1 2 3])))))

(println
 (m/? (m/reduce {} nil ex)))

(def ex3
  (m/ap (m/?> ex2)))

(println
 (m/? (m/reduce {} nil ex3)))

(def ex4
  (m/ap (let [v (m/?> ex2)]
          (println "V" v))))

(m/? (m/reduce {} nil ex4))

(def ex5
  (m/ap (let [v (m/?> (m/seed [1 2 3]))]
          (println "V" v))))

(m/? (m/reduce {} nil ex5))

;; Aside: What is a _continuation_?

;; Pulling back the curtain on m/reduce


;; Clojure reduce
(println "Result"
         (reduce (fn [acc curr]
                   (println curr)
                   (+ acc curr))
                 0
                 [1 2 3]))

;; Missionary reduce
(println "Result"
         (m/?
          (m/reduce (fn [acc curr] (println curr)
                      (+ acc curr))
                    0
                    (m/seed [1 2 3]))))

;; Haskell do macro

;; Back to ex5

(def ex6
  (m/ap
    ))

(ns core
  (:require [clojure.java.io :as io]
            [pages :refer [pages]]))

(defn clean []
  (let [target-dir (io/file "target")]
    (when (.exists target-dir)
      (doseq [file (file-seq target-dir)
              :when (.isFile file)]
        (io/delete-file file))
      (doseq [dir (reverse (filter #(.isDirectory %) (file-seq target-dir)))
              :when (not= dir target-dir)]
        (.delete dir)))))

(defn build []
  (doseq [[path page-fn] (pages)]
    (let [target-path (if (= path "/")
                        "target/html/index.html"
                        (str "target/html" path "/index.html"))
          target-file (io/file target-path)]
      (io/make-parents target-file)
      (spit target-file (str (page-fn))))))

(defn -main []
  (clean)
  (build))
